package com.example.qimo.checkpoint

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qimo.R
import kotlinx.android.synthetic.main.fragment_checkpoint.*


class CheckpointFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_cpoint.setOnClickListener {
            val intent = Intent(activity, CheckpointActivity::class.java)
            startActivity(intent)
        }
        button_challenge.setOnClickListener {

            Toast.makeText(this.context,"敬请期待！！", Toast.LENGTH_LONG).show()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkpoint, container, false)
    }
}