package com.example.jpmccodingchallenge.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.adapters.SchoolListAdapter
import com.example.jpmccodingchallenge.databinding.ActivityMainBinding
import com.example.jpmccodingchallenge.models.School
import com.example.jpmccodingchallenge.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var listAdapter: SchoolListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModelAndGetSchoolsList()
        inflateViewBindingAndSetContentView()
        supportActionBar?.title = getString(R.string.actionBarTitlePlaceholder)
    }

    override fun onBackPressed() {
        // overriding back button navigation from fragment back to activity. If a fragment is present, it removes
        // it from container view, otherwise go back to next activity on the backstack or exit application
        val activeFragment = supportFragmentManager.findFragmentByTag( getString(R.string.detailsFragmentTag) )
        if(activeFragment != null) {
            viewBinding.fragmentContainer.visibility = View.GONE
            supportFragmentManager.commit {
                remove(activeFragment)
            }
            supportActionBar?.title = getString(R.string.actionBarTitlePlaceholder)
        } else {
            super.onBackPressed()
        }
    }

    // Using view binding for a cleaner way to bind and inflate views
    // and avoid multiple calls to findViewById()
    private fun inflateViewBindingAndSetContentView(){
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    private fun initializeViewModelAndGetSchoolsList(){
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getSchoolsList().observe(this) { schoolsList ->
            if (schoolsList.isNullOrEmpty()) {
                Toast.makeText(this, getString(R.string.nullSchoolListWarningMsg), Toast.LENGTH_SHORT).show()
                backToIntroActivity()
            } else {
                hideLoadingIndicator()
                displaySchoolsList(schoolsList)
            }
        }
    }

    private fun backToIntroActivity(){
        startActivity(Intent(this@MainActivity, IntroActivity::class.java))
        finish()
    }

    private fun hideLoadingIndicator(){
        if(viewBinding.circularProgressContainer.root.visibility == View.VISIBLE){
            viewBinding.circularProgressContainer.root.visibility = View.GONE
        }
    }

    private fun displaySchoolsList(schoolList: List<School>){
        listAdapter = SchoolListAdapter(schoolList, ::displaySchoolDetailsFragment)
        viewBinding.listview.layoutManager = LinearLayoutManager(this)
        viewBinding.listview.adapter = listAdapter
    }

    private fun displaySchoolDetailsFragment(selectedSchool: School) {
        if(viewBinding.fragmentContainer.visibility != View.VISIBLE){
            viewBinding.fragmentContainer.visibility = View.VISIBLE
            val schoolDetailsFrag = SchoolDetailsFragment.newInstance(selectedSchool)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, schoolDetailsFrag, getString(R.string.detailsFragmentTag))
            }
        }
    }
}