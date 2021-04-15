package com.example.kursresponsvalyuta.adapter

import android.app.Dialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.databinding.ItemSecondBinding
import com.example.kursresponsvalyuta.fragment.SecondFragment
import com.example.kursresponsvalyuta.model.ValyutaResponsItem

class SecondAdapter(var data: ArrayList<ValyutaResponsItem>, var main: SecondFragment) :
    RecyclerView.Adapter<SecondAdapter.MyViewHolder>() {


    inner class MyViewHolder(var binding: ItemSecondBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(valyutaResponsItem: ValyutaResponsItem) {

            binding.secondData.text = valyutaResponsItem.Date
            binding.secondSumm.text = valyutaResponsItem.Rate + "so\'m"



            if (valyutaResponsItem.Diff > 0.toString()) {
                binding.secondImage.setImageResource(R.drawable.ic_baseline_trending_up_24)
                binding.secondDiff.setTextColor(Color.parseColor("#00FF0A"))
                binding.secondDiff.text = " + " + valyutaResponsItem.Diff

            } else {
                if (valyutaResponsItem.Diff == 0.toString()) {
                    binding.secondImage.setImageResource(R.drawable.ic_baseline_remove_24)
                    binding.secondDiff.text = valyutaResponsItem.Diff
                    binding.secondDiff.setTextColor(Color.parseColor("#000000"))

                } else {
                    binding.secondImage.setImageResource(R.drawable.trending_kamayish)
                    binding.secondDiff.text = valyutaResponsItem.Diff
                    binding.secondDiff.setTextColor(Color.parseColor("#FF0000"))
                }
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemSecondBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount() = data.size
}