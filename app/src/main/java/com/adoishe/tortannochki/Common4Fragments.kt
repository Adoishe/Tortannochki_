package com.adoishe.tortannochki

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class Common4Fragments {

    var fragment : Fragment
     var stringsResource : Int
     var targetFragment: Int
    var spinnerInitializedTimes : Int

    constructor(fragment : Fragment, stringsResource : Int, targetFragment: Int , spinnerInitializedTimes : Int ){

        this.fragment = fragment
        this.stringsResource = stringsResource
        this.targetFragment = targetFragment
        this.spinnerInitializedTimes = spinnerInitializedTimes

    }


    public fun getListener() : AdapterView.OnItemSelectedListener{
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?
                , itemSelected: View?
                , selectedItemPosition: Int
                , selectedId: Long
            ) {





               //val bundle                          = Bundle()
                var navController: NavController = Navigation.findNavController(
                    fragment.activity!!,
                    R.id.nav_host_fragment
                )
                //bundle.putString("selectedComponent", selectedComponent)
                //bundle.putInt("storeysQty", storeysQty!!)



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
                //if (spinnerInitializedTimes == 1)
               // {

                    if ( stringsResource != 0) {

                        val choose = fragment.activity!!.resources.getStringArray(stringsResource)
                        val selectedComponent = choose[selectedItemPosition]

                        (fragment.activity as MainActivity).order.bodyJson.put(fragment.javaClass.simpleName, selectedComponent )

                        fragment.arguments!!.putString(fragment.javaClass.simpleName , selectedComponent )

                        val toast = Toast.makeText(
                            fragment.context!!,
                            "Ваш выбор: $selectedComponent", Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                    navController.navigate(targetFragment, fragment.arguments)
               // }

               // spinnerInitializedTimes++


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}