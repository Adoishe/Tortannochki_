package com.adoishe.tortannochki.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adoishe.tortannochki.DatabaseHelper
import com.adoishe.tortannochki.MainActivity
import com.adoishe.tortannochki.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fun act (View: View) {
            val root = inflater.inflate(R.layout.fragment_home, container, false)
            val i = Intent(root.context, HomeFragment::class.java)

            if (View.getId() == R.id.create) {
                Log.d("act", "create")

                var databaseHelper  = DatabaseHelper(root.context)

                val db   = databaseHelper!!.writableDatabase

                databaseHelper!!.createTables(db)
                //  showStatus("Started!")
            }
            if (View.getId() == R.id.delete) {
                Log.d("act", "√")

                var databaseHelper  = DatabaseHelper(root.context)

                val db   = databaseHelper!!.writableDatabase

                databaseHelper!!.deleteTables(db)

                //  showStatus("Started!")
            }
        }

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}
