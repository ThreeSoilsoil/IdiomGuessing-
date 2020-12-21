package com.example.qimo

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ChecpointAdapter(activity: Activity,val resourced:Int,data:List<CheckPoints>):ArrayAdapter<CheckPoints>(activity,resourced,data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view=LayoutInflater.from(context).inflate(resourced,parent,false)
        val checkpointname:TextView = view.findViewById(R.id.textView_checkpointName)
        val checkpointxinji:TextView = view.findViewById(R.id.textView_checkpointXinji)
        val checkpoint =getItem(position)
        if(checkpoint!=null){
            checkpointname.text=checkpoint.guanka
            checkpointxinji.text=checkpoint.xingji
        }
        return view
    }
}