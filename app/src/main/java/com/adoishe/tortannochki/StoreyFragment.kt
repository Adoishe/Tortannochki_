package com.adoishe.tortannochki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var storeysQty: Int = 0

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
        val root                            = inflater.inflate(R.layout.fragment_storey, container, false)
        val bundle                          = Bundle()
        var oneStorey : ImageView           = root.findViewById(R.id.singleStoreyPic)
        var multyStorey : ImageView         = root.findViewById(R.id.multyStoreyPic)
        var navController: NavController    = Navigation.findNavController(
                                                    this.requireActivity(),
                                                    R.id.nav_host_fragment
                                                )

        oneStorey.setOnClickListener{

            storeysQty = 0

            bundle.putInt("storeysQty", storeysQty)
            navController.navigate(R.id.tasteFragment, bundle);
        }

        multyStorey.setOnClickListener{

            storeysQty = 1

            bundle.putInt("storeysQty", storeysQty)
            navController.navigate(R.id.sizeMultyStoreyFragment, bundle)

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
         * @return A new instance of fragment StoreyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoreyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}