package com.example.kursresponsvalyuta.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.databinding.ItemCalendarBinding
import com.example.kursresponsvalyuta.model.ValyutaResponsItem

class AdaprerCalendar(var data: List<ValyutaResponsItem>) :
    RecyclerView.Adapter<AdaprerCalendar.MyCalendarHolder>() {


    inner class MyCalendarHolder(var binding: ItemCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(valyutaResponsItem: ValyutaResponsItem) {

            binding.calendarName.text = valyutaResponsItem.CcyNm_UZ
            binding.Calendarnumber.text = valyutaResponsItem.Rate + "so\'m"
            binding.trending.text = valyutaResponsItem.Diff

            if (valyutaResponsItem.Diff > 0.toString()) {
                binding.Calendardelete.setImageResource(R.drawable.ic_baseline_trending_up_24)
                binding.trending.setTextColor(Color.parseColor("#00FF0A"))
                binding.trending.text = " + " + valyutaResponsItem.Diff

            } else {
                if (valyutaResponsItem.Diff == 0.toString()) {
                    binding.Calendardelete.setImageResource(R.drawable.ic_baseline_remove_24)
                    binding.trending.setTextColor(Color.parseColor("#000000"))

                } else {
                    binding.Calendardelete.setImageResource(R.drawable.trending_kamayish)
                    binding.trending.setTextColor(Color.parseColor("#FF0000"))
                }
            }

            itemView.setOnClickListener {

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyCalendarHolder(
        ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyCalendarHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount() = data.size

}