package com.example.jpmccodingchallenge.views

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        mSchool = requireArguments().getSerializable("school") as School
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.school_details_fragment, container, false)
        viewBinding = SchoolDetailsFragmentBinding.bind(view)
        setActionBarTitle()
        getSchoolSATRecords()
        return viewBinding.root
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
                    setupUI(schoolSATRecords)
                } else {
                    missingSchoolDataAlert(getString(R.string.missingSchoolDataWarningMessage))
                }
            }
        }
    }

    // This method just sets the text values for the different textviews in the fragment layout
    private fun setupUI(satResults: SATResults) {
        setClickListeners()
//        viewBinding.schoolName.text = mSchool.schoolName
        viewBinding.schoolOverview.text = mSchool.overview
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
        viewBinding.contactInfoContainer.emailValue.text = mSchool.email
        viewBinding.contactInfoContainer.websitePlaceholderText.text = getString(R.string.website_placeholder_text)
        viewBinding.contactInfoContainer.websiteValue.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        viewBinding.contactInfoContainer.websiteValue.text = mSchool.website
        viewBinding.contactInfoContainer.phonePlaceholderText.text = getString(R.string.phone_placeholder_text)
        viewBinding.contactInfoContainer.phoneValue.text = mSchool.phone
        viewBinding.contactInfoContainer.addressPlaceholderText.text = "address:"
        val schoolAddress = mSchool.primaryAddressLine1.plus(" ").plus(mSchool.city).plus(", ").plus(mSchool.state).plus(" ").plus(mSchool.zipCode)
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
            if (!mSchool.website.startsWith("https")){
                val url = "https://".plus(mSchool.website)
                val webBrowserIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(webBrowserIntent)
            }
        }
        viewBinding.contactInfoContainer.emailValue.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, mSchool.email)
                putExtra(Intent.EXTRA_SUBJECT, "subject")
            }
            startActivity(emailIntent)
        }
        viewBinding.contactInfoContainer.phoneValue.setOnClickListener {
            val phoneDialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${mSchool.phone}")
            }
            startActivity(phoneDialIntent)
        }
    }
}