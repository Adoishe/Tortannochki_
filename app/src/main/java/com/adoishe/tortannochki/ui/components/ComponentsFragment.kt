package com.adoishe.tortannochki.ui.components

//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.adoishe.tortannochki.DatabaseHelper
import com.adoishe.tortannochki.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_components.*


//import kotlinx.android.synthetic.main.fragment_components.*


class ComponentsFragment : Fragment() {

    private lateinit var componentsViewModel: ComponentsViewModel
    internal lateinit var spinner: Spinner
    internal lateinit var spinnerChild: Spinner
    internal lateinit var fabButton: FloatingActionButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        componentsViewModel = ViewModelProviders.of(this).get(ComponentsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_components, container, false)
        val textView: TextView = root.findViewById(R.id.text_components)

        spinner = root.findViewById(R.id.spinner)
        spinnerChild = root.findViewById(R.id.spinnerChild)
        var spinnerChildArray: Array<String> = arrayOf("test")

        var spinnerInitialized = false
        var spinnerChildInitializedTimes: Int = 0

        componentsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val arrDecors: MutableList<String> = ArrayList()
        var arrResultStrings          = ArrayList<String>()
        fabButton = activity!!.findViewById<FloatingActionButton>(R.id.fab)

        //activity?.findViewById<FloatingActionButton>(R.id.fab)

        fabButton.setOnClickListener { //анимация скрытия Floating Action Button

            for (string  in arrResultStrings ) {


                val toast = makeText(
                    context!!,
                    string, LENGTH_SHORT
                )
                toast.show()
            }
        }
        // создаем адаптер
//        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrDecors)
        // устанавливаем для списка адаптер
//        listComponents.adapter = adapter;

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?
                , itemSelected: View?
                , selectedItemPosition: Int
                , selectedId: Long
            ) {

                val choose = resources.getStringArray(R.array.Components)
                val selectedComponent = choose[selectedItemPosition]
                //var arrResultStrings          = ArrayList<String>()
                val toast = makeText(
                    context!!,
                    "Ваш выбор: $selectedComponent", LENGTH_SHORT
                )
                toast.show()


                var dH =  DatabaseHelper(root.context)
                //val db   = dH.writableDatabase
                val arrStrings = dH.componentsByName(selectedComponent)

                spinnerChildArray = arrStrings.toTypedArray()

                val dataChildAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    spinnerChildArray
                )
                dataChildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // attaching data adapter to spinner
                if (spinnerChildInitializedTimes > 1) spinnerChildInitializedTimes = 1
                spinnerChild.adapter = dataChildAdapter


          }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        val listenerChild = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?
                , itemSelected: View?
                , selectedItemPosition: Int
                , selectedId: Long
            ) {

                val choose = spinnerChildArray //resources.getStringArray(R.array.Components)
                val selectedComponent = choose[selectedItemPosition]
               // var arrResultStrings          = ArrayList<String>()
                val toast = makeText(
                    context!!,
                    "Ваш выбор: $selectedComponent", LENGTH_SHORT
                )
                toast.show()


                if  (spinnerChildInitializedTimes > 1)
                arrResultStrings.add(selectedComponent)

                val adapter: ListAdapter = ArrayAdapter(
                    root.context,
                    android.R.layout.simple_list_item_1,
                    arrResultStrings
                )

                listViewComponent.adapter = adapter
                spinnerChildInitializedTimes ++
                spinnerInitialized = false
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerChild.onItemSelectedListener = listenerChild
        spinner.onItemSelectedListener = listener

        return root
    }
}
