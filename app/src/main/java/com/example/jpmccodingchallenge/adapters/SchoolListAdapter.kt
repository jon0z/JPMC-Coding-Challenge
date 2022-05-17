package com.example.jpmccodingchallenge.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.models.School

// Custom RecyclerView adapter responsible for populating the RecyclerView
// with elements of the schools list
class SchoolListAdapter(private var schoolList: List<School>,
                        private val callback: (School)-> Unit): RecyclerView.Adapter<SchoolListAdapter.SchoolHolder>()
{

    inner class SchoolHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardView: CardView = itemView.findViewById(R.id.cardview)
        val schoolName: TextView = itemView.findViewById(R.id.school_name)
        val schoolAddress: TextView = itemView.findViewById(R.id.school_address)
        private val gradesStudentsContainer: LinearLayoutCompat = itemView.findViewById(R.id.grades_students_container)
        val schoolGrades: TextView = gradesStudentsContainer.findViewById(R.id.school_grades)
        val schoolNumberStudents: TextView = gradesStudentsContainer.findViewById(R.id.school_number_students)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolHolder {
        return SchoolHolder( LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_card_item, parent, false) )
    }

    override fun onBindViewHolder(holder: SchoolHolder, position: Int) {
        val schoolItem = schoolList[position]
        holder.schoolName.text = schoolItem.schoolName
        val fullAddress = schoolItem.primaryAddressLine1.plus(" ").plus(schoolItem.city).plus(", ").plus(schoolItem.state).plus(" ").plus(schoolItem.zipCode)
        holder.schoolAddress.text = fullAddress
        holder.schoolGrades.text =  schoolItem.grades
        holder.schoolNumberStudents.text = schoolItem.totalStudents
        holder.cardView.setOnClickListener {
            callback(schoolItem)
        }
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }
}