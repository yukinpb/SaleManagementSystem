package com.example.fe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.fe.databinding.ItemStatisticCustomerTypeBinding

class CustomSpinnerStatisticCustomerType(context: Context, resource: Int, objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return customView(position, convertView, parent)
    }

    private fun customView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemStatisticCustomerTypeBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.tvSpinnerItem.text = getItem(position)
        return binding.root
    }

}