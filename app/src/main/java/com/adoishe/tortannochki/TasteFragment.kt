package com.adoishe.tortannochki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.app_bar_main.*

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
    private var step: String? = null
    private var storeysQty: Int? = null
    var spinnerInitializedTimes: Int   = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments = Bundle().apply {
            putString(ARG_PARAM1, param1)
            putString(ARG_PARAM2, param2)
        }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            storeysQty = it.getInt("storeysQty")
        }





    }

    public fun getStep(): String? {

        return step
    }

    override fun onCreateView(
                                inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?
                                ): View? {
        // Inflate the layout for this fragment
        val root                            = inflater.inflate(R.layout.fragment_taste,
                                                                container,
                                                                false)
        val spinner : Spinner               = root.findViewById(R.id.tasteSpinner)

        spinner.setSelection(0, false);

        step                                            = resources.getString(R.string.taste)
        this.requireActivity().toolbar.title            = step
        spinner.post { spinner.onItemSelectedListener   = Common4Fragments(
                                                            this,
                                                            R.array.Tastes,
                                                            R.id.decorFragment,
                                                            step!!
                                                        ).getListener()
        }

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