package com.example.jpmccodingchallenge.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.models.School

class SchoolListAdapter(private val schoolList: List<School>): RecyclerView.Adapter<SchoolListAdapter.SchoolHolder>() {
    inner class SchoolHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val schoolName: TextView = itemView.findViewById(R.id.school_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolHolder {
        return SchoolHolder( LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_card_item, parent, false) )
    }

    override fun onBindViewHolder(holder: SchoolHolder, position: Int) {
        val schoolItem = schoolList[position]
        holder.schoolName.text = schoolItem.schoolName
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }
}