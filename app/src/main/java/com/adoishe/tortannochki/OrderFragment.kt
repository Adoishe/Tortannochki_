package com.adoishe.tortannochki

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_order.*
import org.json.JSONObject


class CustomRecyclerAdapter(private val values: List<Array<String>>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.order_recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = values[position][1]
        holder.smallTextView?.text = values[position][0]
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: TextView? = null
        var smallTextView: TextView? = null

        init {
            largeTextView = itemView?.findViewById(R.id.textViewLarge)
            smallTextView = itemView?.findViewById(R.id.textViewSmall)
        }
    }
}

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var step: String? = null

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
        var root                                =  inflater.inflate(R.layout.fragment_order, container, false)
            /*
        var jSONOut : EditText                  = root.findViewById(R.id.jSONString)
        jSONOut.text                            = (Editable.Factory.getInstance().newEditable((this.activity as MainActivity).order.bodyJson.toString()))
        */
        var orderRecViewElem : RecyclerView     = root.findViewById(R.id.orderRecView)

        orderRecViewElem.layoutManager          = LinearLayoutManager(this.context)
        orderRecViewElem.adapter                = CustomRecyclerAdapter(fillList())

        step = resources.getString(R.string.order)

        this.requireActivity().toolbar.title = step

        return root
    }
    private fun fillList(): List<Array<String>> {
        val data = mutableListOf<Array<String>>()
        //(0..30).forEach { i -> data.add("\$i element") }

       var jsonObject =  (this.activity as MainActivity).order.bodyJson

       var keys = jsonObject.keys()

        while(keys.hasNext()) {
            var key = keys.next();

            var arr = Array<String>(2) { i -> (i).toString() }

            arr[0] = key
            arr[1] = jsonObject.get(key).toString()

            data.add(arr )
            }



        return data
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}