package com.adoishe.tortannochki


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.app_bar_main.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeliveryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeliveryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var profile  : Profile

    private var step: String? = null

    var selectedItemIndex : Int = -1

    lateinit var textViewSelectedAddrr : TextView
    lateinit var arrAddrs : Array<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_delivery, container, false)

        profile = Profile()

        profile.readProfile(requireContext())

        var addressesArrCV = profile.readAddresses(requireContext())


        val arrNames : Array<String> = Array(addressesArrCV.size) { index ->
            addressesArrCV[index].getAsString("AddressName")

        }
         arrAddrs = Array(addressesArrCV.size){ index ->
            addressesArrCV[index].getAsString("Address")
        }
/*
        for(index in 0 until addressesArrCV.size){

            var cv = addressesArrCV[index]

            arrNames.set(index , cv.getAsString("AddressName") )
            arrAddrs.set(index , cv.getAsString("Address") )

        }


 */

        val spinnerAddr = root.findViewById<Spinner>(R.id.spinnerDeliveryAddress)

        step = resources.getString(R.string.delivery)

        this.requireActivity().toolbar.title = step

        // Настраиваем адаптер
        var adapter :  ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item,
                arrNames)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        spinnerAddr.adapter = adapter

        spinnerAddr.setSelection(0, false);




 //       spinnerAddr.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, data)


        spinnerAddr.post {
            spinnerAddr.onItemSelectedListener = Common4Fragments(
                this,
                arrNames,
                R.id.mapsFragment,
                step!!
            ).getListener()
        }

        textViewSelectedAddrr =  root.findViewById<TextView>(R.id.textViewDeliverySelectedAddress)


           // var addressesArrCV = profile.readAddresses(requireContext())

        return root
    }

            companion object {
                /**
                 * Use this factory method to create a new instance of
                 * this fragment using the provided parameters.
                 *
                 * @param param1 Parameter 1.
                 * @param param2 Parameter 2.
                 * @return A new instance of fragment DeliveryFragment.
                 */
                // TODO: Rename and change types and number of parameters

                fun newInstance(param1: String, param2: String) =
                    DeliveryFragment().apply {
                        arguments = Bundle().apply {
                            putString(ARG_PARAM1, param1)
                            putString(ARG_PARAM2, param2)
                        }
                    }


            }


}