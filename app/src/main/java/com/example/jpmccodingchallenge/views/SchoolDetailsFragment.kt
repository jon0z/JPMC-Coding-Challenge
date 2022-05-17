package com.example.jpmccodingchallenge.views

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.databinding.SchoolDetailsFragmentBinding
import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import com.example.jpmccodingchallenge.viewmodels.SchoolDetailsViewModel

class SchoolDetailsFragment : Fragment() {
    private val viewModel: SchoolDetailsViewModel by viewModels()
    private lateinit var mSchool: School
    private lateinit var viewBinding: SchoolDetailsFragmentBinding

    companion object {
        fun newInstance(school: School): SchoolDetailsFragment {
            val schoolDetailsFrag = SchoolDetailsFragment()
            val args = Bundle()
            args.putSerializable("school", school)
            schoolDetailsFrag.arguments = args
            return schoolDetailsFrag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mSchool = requireArguments().getSerializable("school") as School
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.school_details_fragment, container, false)
        viewBinding = SchoolDetailsFragmentBinding.bind(view)
        viewBinding.loadingProgressContainer.loadingPlaceholderText.text = "Loading School Details..."
        setActionBarTitle()
        getSchoolSATRecords()
        return viewBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_email -> {
                emailSchoolIntent()
                true
            }
            R.id.action_call -> {
                callSchoolIntent()
                true
            }
            R.id.action_website -> {
                visitSchoolWebsiteIntent()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun setActionBarTitle(){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = mSchool.schoolName
    }
    private fun getSchoolSATRecords(){
        viewModel.getSATResults().observe(requireActivity()){ satResultsList ->
            if(satResultsList.isNullOrEmpty()){
                missingSchoolDataAlert(getString(R.string.missingSATResultsWarningMessage))
            } else {
                val schoolSATRecords = satResultsList.find { it.dbn == mSchool.dbn }
                if (schoolSATRecords != null){
                    hideLoadingIndicator()
                    setupUI(schoolSATRecords)
                } else {
                    missingSchoolDataAlert(getString(R.string.missingSchoolDataWarningMessage))
                }
            }
        }
    }

    private fun setupUI(satResults: SATResults) {
        setClickListeners()
        viewBinding.schoolOverview.text = mSchool.overview
        // grades and students
        viewBinding.gradesContainer.schoolGrades.text = mSchool.grades
        viewBinding.gradesContainer.schoolNumberStudents.text = mSchool.totalStudents
        // academic opportunities
        viewBinding.academicOpportunitiesContainer.opportunitiesPlaceholderText.text = getString(R.string.academic_opportunities_placeholder_text)
        viewBinding.academicOpportunitiesContainer.opportunityOne.text = mSchool.academicOpportunities1
        // sports
        if(!mSchool.sports.isNullOrEmpty()){
            viewBinding.sportsContainer.opportunitiesPlaceholderText.text = getString(R.string.schoolSportsPlaceholderText)
            viewBinding.sportsContainer.opportunityOne.text = mSchool.sports
            viewBinding.sportsContainer.root.visibility = View.VISIBLE
        }
        // SAT Scores
        viewBinding.satScoresContainer.satScoresPlaceholder.text = getString(R.string.satAvgScoresPlaceholderText)
        viewBinding.satScoresContainer.satMathScore.text = getString(R.string.mathPlaceholderText).plus(" ").plus(satResults.avgMathScore)
        viewBinding.satScoresContainer.satReadingScore.text = getString(R.string.readingPlaceholderText).plus(" ").plus(satResults.avgReadingScore)
        viewBinding.satScoresContainer.satWritingScore.text = getString(R.string.writingPlaceholderText).plus(" ").plus(satResults.avgWritingScore)
        // School contact info
        viewBinding.contactInfoContainer.contactInfoPlaceholderText.text = getString(R.string.contactInformationPlaceholderText)
        viewBinding.contactInfoContainer.emailPlaceholderText.text = getString(R.string.emailPlaceholderText)
        viewBinding.contactInfoContainer.emailValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewBinding.contactInfoContainer.emailValue.text = if(mSchool.email != null) mSchool.email else "N/A"
        viewBinding.contactInfoContainer.websitePlaceholderText.text = getString(R.string.website_placeholder_text)
        viewBinding.contactInfoContainer.websiteValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewBinding.contactInfoContainer.websiteValue.text = mSchool.website
        viewBinding.contactInfoContainer.phonePlaceholderText.text = getString(R.string.phone_placeholder_text)
        viewBinding.contactInfoContainer.phoneValue.text = mSchool.phone
        viewBinding.contactInfoContainer.addressPlaceholderText.text = getString(R.string.address_placeholder_text)
        val schoolAddress = mSchool.primaryAddressLine1.plus(" ")
            .plus(mSchool.city).plus(", ")
            .plus(mSchool.state).plus(" ")
            .plus(mSchool.zipCode)
        viewBinding.contactInfoContainer.addressValue.text = schoolAddress
    }

    // Dialog alert to let users know of any errors while getting the data or missing school data in database
    private fun missingSchoolDataAlert(message: String){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
        builder.setNegativeButton("Back") { dialog, _ ->
            dialog.dismiss()
            requireActivity().onBackPressed()
        }
        builder.create()
            .show()
    }

    private fun setClickListeners(){
        viewBinding.contactInfoContainer.websiteValue.setOnClickListener {
            visitSchoolWebsiteIntent()
        }
        viewBinding.contactInfoContainer.emailValue.setOnClickListener {
            emailSchoolIntent()
        }
        viewBinding.contactInfoContainer.phoneValue.setOnClickListener {
            callSchoolIntent()
        }
    }

    private fun visitSchoolWebsiteIntent(){
        if (!mSchool.website.startsWith("https")){
            val url = "https://".plus(mSchool.website)
            val webBrowserIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(webBrowserIntent)
        }
    }

    private fun emailSchoolIntent(){
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, mSchool.email)
            putExtra(Intent.EXTRA_SUBJECT, "subject")
        }
        startActivity(emailIntent)
    }

    private fun callSchoolIntent(){
        val phoneDialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${mSchool.phone}")
        }
        startActivity(phoneDialIntent)
    }

    private fun hideLoadingIndicator(){
        if(viewBinding.loadingProgressContainer.root.visibility == View.VISIBLE){
            viewBinding.loadingProgressContainer.root.visibility = View.GONE
            viewBinding.schoolInfoContainer.visibility = View.VISIBLE
        }
    }
}