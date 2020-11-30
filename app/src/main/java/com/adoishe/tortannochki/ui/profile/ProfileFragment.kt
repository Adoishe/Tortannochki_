package com.adoishe.tortannochki.ui.profile

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adoishe.tortannochki.Profile
import com.adoishe.tortannochki.R
import org.json.JSONObject


class ProfileFragment : Fragment() {

    private lateinit var pofileViewModel: ProfileViewModel

    lateinit var profile : Profile

    fun addRowToTable (addressCV : ContentValues){

        var tableAddresses = requireActivity().findViewById<TableLayout>(R.id.tableAddresses)
        var tableRow = TableRow(requireContext())


        tableRow.setLayoutParams(TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT))

        var textView = TextView(requireContext())
        var editText = EditText(requireContext())

        textView.text = addressCV.getAsString("AddressName")

        editText.setText(addressCV.getAsString("Address"), TextView.BufferType.EDITABLE)

        tableRow.addView(textView)
        tableRow.addView(editText)

        tableAddresses.addView(tableRow,
            TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pofileViewModel             = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root                    = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView      = root.findViewById(R.id.Profile)

        pofileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var profileName : TextView  = root.findViewById(R.id.editTextName)
        var profilePhone : EditText = root.findViewById(R.id.editTextPhone)
        var profileEmail : EditText = root.findViewById(R.id.editTextEmail)
        profile                     = Profile()

        profile.readProfile(requireContext())

        var addressesArrCV = profile.readAddresses(requireContext())

        for (addressCV  in addressesArrCV){

            addRowToTable(addressCV)

        }

        profileName.setText(profile.getName())
        profilePhone.setText(profile.getphone().toString())

        var jsonObject = profile.getJSONObject()

        var jsonString : String

        try {
            jsonString  =  jsonObject.get("Email").toString()
        }catch (e: Exception){

            jsonString  =   ""

        }
        profileEmail.setText(jsonString)

        val button : Button =  root.findViewById(R.id.buttonSaveProfile)

        button.setOnClickListener{

            profile.setName(profileName.text.toString())
            profile.setPhone(profilePhone.text.toString().toInt())

            var jsonObject = JSONObject()

            jsonObject.put("Email", profileEmail.text.toString())

            profile.setJSONObject(jsonObject)

            profile.writeProfile(requireContext())

        }

        val buttonAddAddrr : Button =  root.findViewById(R.id.buttonAddAddress)

        buttonAddAddrr.setOnClickListener{

            var cv = ContentValues()

            cv.put("AddressName" , "")
            cv.put("Address" , "")

            addRowToTable(cv)

        }



        return root
    }
}
