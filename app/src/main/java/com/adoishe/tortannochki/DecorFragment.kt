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
 * Use the [DecorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DecorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var step: String? = null
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
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root                = inflater.inflate(R.layout.fragment_decor, container, false)
        val spinner : Spinner   = root.findViewById(R.id.decorSpinner)


        step = resources.getString(R.string.taste)

        this.requireActivity().toolbar.title = step


        step = resources.getString(R.string.decor)

        this.requireActivity().toolbar.title = step

        spinner.setSelection(0, false);

        spinner.post { spinner.onItemSelectedListener = Common4Fragments(
            this,
            R.array.Decor,
            R.id.otherSweetsFragment,
            step!!
        ).getListener() }
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DecorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DecorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}