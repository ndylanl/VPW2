package com.example.vpw2.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.vpw2.Interface.CardListener
import com.example.vpw2.Model.Hewan
import com.example.vpw2.R
import com.example.vpw2.databinding.CardHewanBinding


class ListDataRVAdapter(val listHewan: ArrayList<Hewan>, val cardListener: CardListener) :
    Adapter<ListDataRVAdapter.viewHolder>() {

    class viewHolder (val itemview: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemview){

        val binding = CardHewanBinding.bind(itemview)

        fun setData(data: Hewan){
            binding.tvNama.text = data.name
            binding.tvJenis.text = data.species
            binding.tvUsia.text = "Age: " + data.usia
            if (data.imageUri.isNotEmpty()){
                binding.cvIMG.setImageURI(Uri.parse(data.imageUri))
            }else{
                binding.cvIMG.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context, // Context
                        R.drawable.uc_logo // Drawable
                    )
                )
            }
            binding.editBTN.setOnClickListener{
                cardListener1.onEdit(adapterPosition)
            }

            binding.delBTN.setOnClickListener(){
                cardListener1.onDelete(adapterPosition)
            }

            binding.feedBTN.setOnClickListener(){
                cardListener1.onFeed(adapterPosition)
            }

            binding.soundBTN.setOnClickListener(){
                cardListener1.onSound(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_hewan, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listHewan[position])
    }

    override fun getItemCount(): Int {
        return listHewan.size
    }


}