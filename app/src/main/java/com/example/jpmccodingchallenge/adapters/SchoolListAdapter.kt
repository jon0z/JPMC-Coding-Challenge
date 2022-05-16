package com.example.jpmccodingchallenge.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.models.School

class SchoolListAdapter(private var schoolList: List<School>, private val context: Context, private val callback: (School)-> Unit): RecyclerView.Adapter<SchoolListAdapter.SchoolHolder>() {

    inner class SchoolHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardView: CardView = itemView.findViewById(R.id.cardview)
        val schoolName: TextView = itemView.findViewById(R.id.school_name)
        val schoolAddressLine1: TextView = itemView.findViewById(R.id.school_address)
        val schoolCityAndState: TextView = itemView.findViewById(R.id.school_city_state)
        val schoolGrades: TextView = itemView.findViewById(R.id.school_grades)
        val schoolNumberStudents: TextView = itemView.findViewById(R.id.school_number_students)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolHolder {
        return SchoolHolder( LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_card_item, parent, false) )
    }

    override fun onBindViewHolder(holder: SchoolHolder, position: Int) {
        val schoolItem = schoolList[position]
        holder.schoolName.text = schoolItem.schoolName
        holder.schoolAddressLine1.text = schoolItem.primaryAddressLine1
        holder.schoolCityAndState.text = schoolItem.city.plus(", ").plus(schoolItem.state).plus(" ").plus(schoolItem.zipCode)
        holder.schoolGrades.text =  context.getString(R.string.gradesPlaceholderTxt).plus(schoolItem.grades)
        holder.schoolNumberStudents.text = context.getString(R.string.studentsPlaceholderTxt).plus(schoolItem.totalStudents)
        holder.cardView.setOnClickListener {
            callback(schoolItem)
        }
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }
}