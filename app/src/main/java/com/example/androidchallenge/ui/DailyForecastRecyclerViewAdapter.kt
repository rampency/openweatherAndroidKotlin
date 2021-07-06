package com.example.androidchallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidchallenge.databinding.FragmentItemBinding
import com.example.androidchallenge.network.models.ForecastWeather
import java.text.SimpleDateFormat
import java.util.*


class DailyForecastRecyclerViewAdapter(
    private val weatherList: List<ForecastWeather>
) : RecyclerView.Adapter<DailyForecastRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherList[position]
        holder.temp.text = item.temp.day.toString().plus(" °F")
        holder.tempDayMaxMin.text = item.temp.max.toString().plus(" °F") + "-" +item.temp.min.toString().plus(" °F")
        val time = item.dt * 1000.toLong()
        val date = Date(time)
        val format = SimpleDateFormat("MM-dd-yy")
        format.setTimeZone(TimeZone.getTimeZone("GMT"))
        holder.date.text = format.format(date).toString()
    }

    override fun getItemCount(): Int = weatherList.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val temp: TextView = binding.temp
        val tempDayMaxMin: TextView = binding.tempDayMaxMin
        val date: TextView = binding.date

        override fun toString(): String {
            return super.toString() + " '" + temp.text + "'"
        }
    }

}