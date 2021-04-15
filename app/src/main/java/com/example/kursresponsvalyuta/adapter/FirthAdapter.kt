package com.example.kursresponsvalyuta.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.databinding.ItemFirthBinding
import com.example.kursresponsvalyuta.fragment.FirthFragment
import com.example.kursresponsvalyuta.model.ValyutaResponsItem

class FirthAdapter(var data: ArrayList<ValyutaResponsItem>, var mcontext: FirthFragment) :
    RecyclerView.Adapter<FirthAdapter.MyViewHolder>() {


    inner class MyViewHolder(var binding: ItemFirthBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(valyutaResponsItem: ValyutaResponsItem) {

            binding.name.text = valyutaResponsItem.CcyNm_UZ
            binding.number.text = valyutaResponsItem.Rate + "so\'m"
            binding.trending.text = valyutaResponsItem.Diff

            if (valyutaResponsItem.Diff > 0.toString()) {
                binding.delete.setImageResource(R.drawable.ic_baseline_trending_up_24)
                binding.trending.setTextColor(Color.parseColor("#00FF0A"))
                binding.trending.text = " + " + valyutaResponsItem.Diff

            } else {
                if (valyutaResponsItem.Diff == 0.toString()) {
                    binding.delete.setImageResource(R.drawable.ic_baseline_remove_24)
                    binding.trending.setTextColor(Color.parseColor("#000000"))

                } else {
                    binding.delete.setImageResource(R.drawable.trending_kamayish)
                    binding.trending.setTextColor(Color.parseColor("#FF0000"))
                }
            }

            itemView.setOnClickListener {
              mcontext.getDataa(valyutaResponsItem)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        ItemFirthBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount() = data.size



}