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
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject


class ProfileFragment : Fragment() {

    private lateinit var pofileViewModel: ProfileViewModel

    lateinit var profile : Profile

    lateinit var tableAddresses : TableLayout

   // var addressArrCV : ArrayList<ContentValues> = ArrayList()
    var addressEditTextArr : ArrayList<ArrayList<EditText> > = ArrayList()


    fun addRowToTable(addressCV: ContentValues) : ArrayList<EditText> {

      //  var tableAddresses          = requireActivity().findViewById<TableLayout>(R.id.tableAddresses)
        var tableRow : TableRow     = TableRow(requireContext())
        var rowLayOutParams         = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT)
  //      var editTextLayPrm          = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        var editTextLayPrm          = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        var tableAddressLayoutParam = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.WRAP_CONTENT)
        var editTextName  : EditText = EditText(requireContext())
        var editTextAddr  : EditText = EditText(requireContext())
        var addressElem             = ContentValues()
     //   editTextName.layoutParams   = editTextLayPrm
     //   editTextAddr.layoutParams   = editTextLayPrm
        tableRow.layoutParams       = rowLayOutParams

        val editTextArr: ArrayList<EditText> = ArrayList()


        editTextName.setText(addressCV.getAsString("AddressName"), TextView.BufferType.EDITABLE)

        if(addressCV.getAsBoolean("Reading") ){

            editTextAddr.setText(addressCV.getAsString("Address"), TextView.BufferType.EDITABLE)

        }else{

            editTextAddr.setHint(R.string.EnterAddress)
        }

        tableRow.addView(editTextName)
        tableRow.addView(editTextAddr)

        editTextArr.add(editTextName)
        editTextArr.add(editTextAddr)

        tableAddresses.addView(tableRow, tableAddressLayoutParam)


        return editTextArr

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pofileViewModel         = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root                = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView  = root.findViewById(R.id.Profile)



        pofileViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var profileName : TextView  = root.findViewById(R.id.editTextName)
        var profilePhone : EditText = root.findViewById(R.id.editTextPhone)
        var profileEmail : EditText = root.findViewById(R.id.editTextEmail)
        tableAddresses              = root.findViewById(R.id.tableAddresses)
        profile                     = Profile()



        profile.readProfile(requireContext())

        var addressesArrCV = profile.readAddresses(requireContext())

        for (addressCV  in addressesArrCV){

            addressCV.put("Reading", true)

            addressEditTextArr.add(addRowToTable(addressCV))

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

            var addrCVArr : ArrayList<ContentValues> = ArrayList()

            for (addr : ArrayList<EditText>  in this.addressEditTextArr){

                    var elemName :EditText  = addr[0]
                    var elem  :EditText  = addr[1]

              //  var elemView : EditText = root.findViewById(elem)
               // var elemViewName : EditText= root.findViewById(elemName)

                var addrCV = ContentValues()

                addrCV.put("Address", elem.text.toString())
                addrCV.put("AddressName", elemName.text.toString())

                addrCVArr.add(addrCV)

            }

            profile.writeProfile(requireContext(), addrCVArr)

        }

        val buttonAddAddrr : Button =  root.findViewById(R.id.buttonAddAddress)

        buttonAddAddrr.setOnClickListener{

            var cv = ContentValues()

            cv.put("AddressName", requireContext().resources.getString(R.string.addressName))
            cv.put("Address", requireContext().resources.getString(R.string.address))
            cv.put("Reading", false)

            addressEditTextArr.add(addRowToTable(cv))

        }

        return root
    }
}
