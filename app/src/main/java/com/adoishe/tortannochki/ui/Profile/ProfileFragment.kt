package com.adoishe.tortannochki.ui.Profilerofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adoishe.tortannochki.R
import com.adoishe.tortannochki.DatabaseHelper
import com.adoishe.tortannochki.ui.Profile.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var pofileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        pofileViewModel             = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root                    = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView      = root.findViewById(R.id.text_profile)

        pofileViewModel.text.observe(viewLifecycleOwner, Observer {
                                                                        textView.text = it })
        return root
    }
}
