package com.example.jpmccodingchallenge.views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.databinding.SchoolDetailsFragmentBinding
import com.example.jpmccodingchallenge.models.SATResults
import com.example.jpmccodingchallenge.models.School
import com.example.jpmccodingchallenge.viewmodels.SchoolDetailsViewModel

class SchoolDetailsFragment : Fragment() {
    private val viewModel: SchoolDetailsViewModel by viewModels()
    private lateinit var mSchool: School
    private lateinit var satResultsList: List<SATResults>
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
        viewModel.getSATResults().observe(requireActivity()){ satResults ->
            if(satResults.isNullOrEmpty()){
                missingSchoolDataAlert(getString(R.string.missingSATResultsWarningMessage))
            } else {
                satResultsList = satResults
                val schoolSATResults = satResults.find { it.dbn == mSchool.dbn }
                if (schoolSATResults != null){
                    setupUI(schoolSATResults)
                } else {
                    missingSchoolDataAlert(getString(R.string.missingSchoolDataWarningMessage))
                }
            }
        }

        return viewBinding.root
    }

    private fun setupUI(satResults: SATResults) {
        viewBinding.schoolName.text = mSchool.schoolName
        viewBinding.schoolOverview.text = mSchool.overview
        viewBinding.opportunityOne.text = mSchool.academicOpportunities1
        viewBinding.satMathScore.text = getString(R.string.mathPlaceholderText).plus(satResults.avgMathScore)
        viewBinding.satReadingScore.text = getString(R.string.readingPlaceholderText).plus(satResults.avgReadingScore)
        viewBinding.satWritingScore.text = getString(R.string.writingPlaceholderText).plus(satResults.avgWritingScore)
    }

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
}