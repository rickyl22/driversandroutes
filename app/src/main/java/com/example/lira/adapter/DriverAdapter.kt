package com.example.lira.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lira.R
import com.example.lira.data.Driver


class DriverAdapter(private val clickListener: DriverOnClickListener, var driverList: List<Driver>)
    : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.driver_item, parent, false)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
       holder.name?.text = driverList[position].name
        holder.name.setOnClickListener{
            clickListener.onClick(driverList[position])
        }
    }

    override fun getItemCount(): Int {
        return driverList.size
    }

    class DriverViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        val name : TextView = containerView.findViewById(R.id.name)
    }

    class DriverOnClickListener(val clickListener: (driver: Driver) -> Unit) {
        fun onClick(driver: Driver) = clickListener(driver)
    }

}