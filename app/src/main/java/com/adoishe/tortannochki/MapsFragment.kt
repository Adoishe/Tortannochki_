package com.adoishe.tortannochki


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MapsFragment : Fragment() , OnMapReadyCallback {

    lateinit var mMapView: MapView//? = null
    private var googleMap: GoogleMap? = null

    private val REQUEST_CAMERA = 0
    private val REQUEST_CAMERA_PERMISSION = 1
    private val REQUEST_ACCESS_FINE_LOCATION = 2
    private val REQUEST_ACCESS_COARSE_LOCATION = 3
    var PLACE_PICKER_REQUEST = 1

    var lat : Double = 0.0
    var lng : Double = 0.0
    /**
     * @param view
     * @brief requestForCameraPermission
     */
    fun requestForLocationPermission(view: View?, permission: String) {
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place =Autocomplete.getPlaceFromIntent(data!!);

                lat = place.latLng!!.latitude
                lng = place.latLng!!.longitude
            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                var status = Autocomplete.getStatusFromIntent(data!!)
               // Log.i("address", status.getStatusMessage());
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        requestForLocationPermission(rootView, "android.Manifest.permission.ACCESS_FINE_LOCATION")


        requestForLocationPermission(rootView, "android.Manifest.permission.ACCESS_COARSE_LOCATION")


        if (!Places.isInitialized()) Places.initialize(this.requireContext(),
            getString(R.string.google_maps_key),
            Locale.US)

        var fields=Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        var intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this.requireContext())
        startActivityForResult(intent, PLACE_PICKER_REQUEST)


        val mAddressEditText = childFragmentManager.findFragmentById(R.id.address) as AutocompleteSupportFragment

        mAddressEditText.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG))
        mAddressEditText.setHint("Address")
        mAddressEditText.setText("Test1") // Works fine at the beginning, disappears after selecting a place and shows only the hint

        mAddressEditText.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Log.d(TAG, "Place Selected")
                // Other Stuff
                mAddressEditText.setText("Test2") // Doesn't Work, all I can see is the hint
                mAddressEditText.setText(place.address) // Doesn't Work, all I can see is the hint
            }

            override fun onError(status: Status) {
             println("An error occurred: $status")

            // Log.e(TAG, "An error occurred: $status")
                //  invalidAddressDialog.show()
            }
        })


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


     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<String>, grantResults: IntArray
     ) {
         when (requestCode) {
             REQUEST_ACCESS_FINE_LOCATION -> {

                 if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                     Snackbar.make(this.requireView(),
                         "Permission has been denied by user",
                         Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show()


                 } else {
                     Snackbar.make(this.requireView(),
                         "Permission has been granted by user",
                         Snackbar.LENGTH_LONG)
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

