package com.adoishe.tortannochki.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adoishe.tortannochki.Profile
import com.adoishe.tortannochki.R
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var pofileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        pofileViewModel             = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root                    = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView      = root.findViewById(R.id.Profile)

        pofileViewModel.text.observe(viewLifecycleOwner, Observer {
                                                                        textView.text = it })

        var profileName : TextView = root.findViewById(R.id.Name)
        var profilePhone : EditText = root.findViewById(R.id.Phone)
        var profileEmail : EditText = root.findViewById(R.id.Email)

        var profile : Profile = Profile()

        profile.readProfile(context!!)

        profileName.setText(profile.getName())
        profilePhone.setText(profile.getphone().toString())

        var jsonObject = profile.getJSONObject()
        var jsonString : String

        try {
            jsonString  =  jsonObject.get("Email").toString()
        }catch( e: Exception){

            jsonString  =   ""

        }



        profileEmail.setText(jsonString)

        val button : Button =  root.findViewById(R.id.butonSaveProfile)

        button.setOnClickListener{

            profile.setName(profileName.text.toString())
            profile.setPhone(profileName.text.toString().toInt())

            var jsonObject = JSONObject()

            jsonObject.put("Email", profileEmail.toString())

            profile.setJSONObject(jsonObject)

        }

        return root
    }
}
