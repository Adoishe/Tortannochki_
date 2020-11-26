package com.adoishe.tortannochki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var storeysQty: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            storeysQty = it.getInt("storeysQty")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root                            = inflater.inflate(R.layout.fragment_taste, container, false)

        val spinner : Spinner               = root.findViewById(R.id.tasteSpinner)

        var spinnerInitializedTimes: Int   = 0
        /*
            val arrTastes : Array<String>       = this.activity?.resources!!.getStringArray(R.array.Tastes)
            val adapter: ArrayAdapter<String>   = ArrayAdapter<String>(this.context!!, R.layout.taste_spinner, R.id.tasteSpinner, arrTastes)
            spinner.adapter                     = adapter
    */


        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?
                , itemSelected: View?
                , selectedItemPosition: Int
                , selectedId: Long
            ) {

                val choose              = resources.getStringArray(R.array.Components)
                val selectedComponent   = choose[selectedItemPosition]
                //var arrResultStrings          = ArrayList<String>()
                val toast               = Toast.makeText(
                    context!!,
                    "Ваш выбор: $selectedComponent", Toast.LENGTH_SHORT
                )

                toast.show()

                val bundle                          = Bundle()
                var navController: NavController = Navigation.findNavController(
                    activity!!,
                    R.id.nav_host_fragment
                )
                bundle.putString("selectedComponent", selectedComponent)
                bundle.putInt("storeysQty", storeysQty!!)



/*
                var dH              = DatabaseHelper(root.context)
                val arrStrings      = dH.componentsByName(selectedComponent)
                spinnerChildArray   = arrStrings.toTypedArray()

                val dataChildAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    spinnerChildArray
                )

                dataChildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                // attaching data adapter to spinner
                */
                if (spinnerInitializedTimes == 1)
                {
                    navController.navigate(R.id.decorFragment, bundle)
                }

                spinnerInitializedTimes++





            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner.onItemSelectedListener = listener



        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TasteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TasteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString("storeysQty", param2)
                }
            }
    }
}