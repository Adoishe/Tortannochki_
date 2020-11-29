package com.adoishe.tortannochki


import android.Manifest

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class MapsFragment : Fragment() , OnMapReadyCallback {

    lateinit var mMapView: MapView//? = null
    private var googleMap: GoogleMap? = null

    private val REQUEST_CAMERA = 0
    private val REQUEST_CAMERA_PERMISSION = 1
    private val REQUEST_ACCESS_FINE_LOCATION = 2
    private val REQUEST_ACCESS_COARSE_LOCATION = 3


    /**
     * @param view
     * @brief requestForCameraPermission
     */
    fun requestForLocationPermission(view: View?, permission : String) {
        //Log.v(TAG, "Requesting Camera Permission")
        if (ContextCompat.checkSelfPermission(requireActivity(), permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    permission)
            ) {
                showPermissionRationaleDialog("sdfasdfasdfsdfasdf",
                    permission)
            } else {
                requestForPermission(permission)
            }
        } else {
            //launch()
        }
    }

    /**
     * @param message
     * @param permission
     * @brief showPermissionRationaleDialog
     */
    private fun showPermissionRationaleDialog(message: String, permission: String) {
        AlertDialog.Builder(requireActivity())
            .setMessage(message)
            .setPositiveButton("Ok",
                DialogInterface.OnClickListener { dialog, which ->
                    this.requestForPermission(permission)
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> })
            .create()
            .show()
    }

    /**
     * @param permission
     * @brief requestForPermission
     */
    private fun requestForPermission(permission: String) {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(permission),
            REQUEST_CAMERA_PERMISSION)
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        requestForLocationPermission(rootView, "android.Manifest.permission.ACCESS_FINE_LOCATION")


        requestForLocationPermission(rootView, "android.Manifest.permission.ACCESS_COARSE_LOCATION")


/*
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)

        ActivityCompat.requestPermissions(requireActivity(), permissions, 0)

 */

      //   mMapView = rootView.findViewById(R.id.mapView)

        /*
               mMapView.onCreate(savedInstanceState)
               mMapView.onResume() // needed to get the map to display immediately


               try {
                   MapsInitializer.initialize(activity!!.applicationContext)
               } catch (e: Exception) {
                   e.printStackTrace()
               }*/

      //  mMapView.getMapAsync(this)
          /* { mMap ->
            googleMap = mMap


            if (ActivityCompat.checkSelfPermission(this.context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return
            }
            googleMap!!.isMyLocationEnabled = true

            // For dropping a marker at a point on the Map
            val sydney = LatLng((-34).toDouble(), 151.0)
            googleMap!!.addMarker(MarkerOptions().position(sydney).title("Marker Title")
                .snippet("Marker Description"))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


        }*/
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        mMapView = view.findViewById(R.id.mapView)

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        mMapView.getMapAsync(this)
/*
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

 */


      /*
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)   */


    }

    override fun onMapReady(map: GoogleMap) {

        googleMap           = map
        googleMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL


        if (ActivityCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

            ActivityCompat.requestPermissions(requireActivity(), permissions, 1)

             permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)

            ActivityCompat.requestPermissions(requireActivity(), permissions, 1)


            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return
        }



        googleMap!!.uiSettings.isMyLocationButtonEnabled = true;
        googleMap!!.uiSettings.isMapToolbarEnabled = true;
        googleMap!!.uiSettings.isZoomControlsEnabled = true;
        //googleMap!!.isMyLocationEnabled = true;
        googleMap!!.isTrafficEnabled = true;
        googleMap!!.isIndoorEnabled = true;
        googleMap!!.isBuildingsEnabled = true;
        googleMap!!.uiSettings.isZoomControlsEnabled = true;


    // Add a marker in Sydney and move the camera
    val sydney = LatLng(-34.0, 151.0)

        googleMap!!.addMarker(MarkerOptions()
            .position(sydney).title("Marker in Sydney")
            .snippet("В Сиднее много котов"))
    googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))




    }


    /*
    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

     */

/*
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


*/


     override fun onRequestPermissionsResult(requestCode: Int,
                                              permissions: Array<String>, grantResults: IntArray) {
         when (requestCode) {
             REQUEST_ACCESS_FINE_LOCATION -> {

                 if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                     Snackbar.make(this.requireView(), "Permission has been denied by user", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show()


                 } else {
                     Snackbar.make(this.requireView(), "Permission has been granted by user", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show()

                     googleMap!!.uiSettings.isMyLocationButtonEnabled = true;
                     googleMap!!.uiSettings.isMapToolbarEnabled = true;
                     googleMap!!.uiSettings.isZoomControlsEnabled = true;
                     //googleMap!!.isMyLocationEnabled = true;
                     googleMap!!.isTrafficEnabled = true;
                     googleMap!!.isIndoorEnabled = true;
                     googleMap!!.isBuildingsEnabled = true;
                     googleMap!!.uiSettings.isZoomControlsEnabled = true;


                 }
             }
         }
     }



}

