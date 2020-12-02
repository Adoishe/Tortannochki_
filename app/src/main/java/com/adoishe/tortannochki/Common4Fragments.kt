package com.adoishe.tortannochki

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class Common4Fragments {

    lateinit var fragment : Fragment
     var stringsResource : Int = 0
   lateinit var stringsResourceArr : Array<String>
    var targetFragment: Int = 0
    var step : String = ""
      var fragmentSequence : Boolean

    var  selectedItemIndex : Int = -1



    constructor(fragment : Fragment, stringsResource : Int, targetFragment: Int , step : String ){

        this.fragment = fragment
        this.stringsResource = stringsResource

        this.targetFragment = targetFragment
        this.step = step

        fragmentSequence = true

    }

    constructor(fragment : Fragment, stringsResource : Array<String>, targetFragment: Int , step : String ){

        this.fragment = fragment
        this.stringsResourceArr = stringsResource
        this.targetFragment = targetFragment
        this.step = step

        fragmentSequence = false


    }
 /*
    constructor(step : String){

        fragment.requireActivity().toolbar.title = step

    }



    fun setTtile() {

    }

  */

    public fun getListener() : AdapterView.OnItemSelectedListener{
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?,
                selectedItemPosition: Int,
                selectedId: Long
            ) {


               //val bundle                          = Bundle()
                var navController: NavController = Navigation.findNavController(
                    fragment.requireActivity(),
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

                    if ( stringsResource == 0) {

                        if(!fragmentSequence){

                            val selectedComponent = stringsResourceArr[selectedItemPosition]

                            val toast = Toast.makeText(
                                fragment.requireContext(),
                                "Ваш выбор адреса: $selectedComponent", Toast.LENGTH_SHORT
                            )
                            toast.show()

                            (fragment as DeliveryFragment).selectedItemIndex = selectedItemPosition

                            (fragment as DeliveryFragment).textViewSelectedAddrr.text = (fragment as DeliveryFragment).arrAddrs[selectedItemPosition]

                        }

                    }
                    else{

                        if(fragmentSequence) {

                            var choose =
                                fragment.requireActivity().resources.getStringArray(stringsResource)


                            val selectedComponent = choose[selectedItemPosition]

                            (fragment.activity as MainActivity).order.bodyJson.put(step,
                                selectedComponent)

                            fragment.requireArguments()
                                .putString(fragment.javaClass.simpleName, selectedComponent)

                            val toast = Toast.makeText(
                                fragment.requireContext(),
                                "Ваш выбор: $selectedComponent", Toast.LENGTH_SHORT
                            )
                            toast.show()
                            navController.navigate(targetFragment, fragment.arguments)
                        }
                        else{


                        }
                    }

               // }

               // spinnerInitializedTimes++


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}