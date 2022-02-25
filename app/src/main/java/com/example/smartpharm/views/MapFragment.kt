package com.example.smartpharm.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.smartpharm.R
import com.example.smartpharm.controllers.ClientController
import com.example.smartpharm.controllers.ClientController.getListPharmacies
import com.example.smartpharm.controllers.ClientController.listPharmacies
import com.example.smartpharm.controllers.ClientController.listPharmaciesOfMap
import com.example.smartpharm.controllers.ClientController.onCleared
import com.example.smartpharm.databinding.FragmentMapBinding
import com.example.smartpharm.models.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MapFragment : Fragment(), OnMapReadyCallback {

    private val MY_POSITION = "Votre Position"
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var latitude : MutableLiveData<Double> = MutableLiveData(0.0)
    private  var longtitude : MutableLiveData<Double> = MutableLiveData(0.0)
    private var placeLiveData: MutableLiveData<String> = MutableLiveData(MY_POSITION)
    private var setLocation: MutableLiveData<Location> = MutableLiveData( Location(MY_POSITION))
    private var mCircle: Circle? = null
    private var mMarker: Marker? = null





    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        onCleared()
        getListPharmacies(requireActivity())

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_map,container,false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if(navBar != null){
            navBar.isVisible = false
        }


        binding.BtnMyLocation.setOnClickListener {
            val zoomLevel = 15f
            mMap.clear()
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        setLocation.value = location
                        latitude.value = location.latitude
                        longtitude.value = location.longitude
                        placeLiveData.value = MY_POSITION

                        var currentPosition = longtitude.value?.let { latitude.value?.let { it1 -> LatLng(it1, it) } }

                        currentPosition?.let {
                            MarkerOptions()
                                .position(it)
                                .title(placeLiveData.value)
                        }?.let {
                            mMap.addMarker(it)
                        }

                        currentPosition?.let { CameraUpdateFactory.newLatLngZoom(it,zoomLevel) }?.let { mMap.moveCamera(it) }
                    }
                }
        }


        placeLiveData.observe(viewLifecycleOwner) {
                binding.textCoordinate.text = it
        }

        binding.buttonProceed.setOnClickListener{
            var list:MutableList<User> = mutableListOf<User>()

            val distance = FloatArray(2)


            if(mCircle!=null && !listPharmacies.value.isNullOrEmpty()){

                for(pharmacy in listPharmacies.value!!){
                    Log.v("MAD", "pharmacy : ${pharmacy.locationUser}")
                    var lati : Double = if(pharmacy.coordinate!!["latitude"] !=null ) pharmacy.coordinate!!["latitude"] as Double else 0.0
                    var long : Double = if(pharmacy.coordinate!!["longitude"] !=null ) pharmacy.coordinate!!["longitude"] as Double else 0.0

                    Location.distanceBetween(lati , long, mCircle!!.center.latitude, mCircle!!.center.longitude, distance)
                    Log.v("RAD", "lati : $lati , long : $long , centerlati : ${mCircle!!.center.latitude}, centerlong : ${mCircle!!.center.longitude}, distance: ${distance[0]} ,radius : ${mCircle!!.radius}")
                    if( distance[0] <= mCircle!!.radius){

                        Log.v("MAD", "add : ${pharmacy.locationUser}")
                        list.add(pharmacy)
                    }
                }

                listPharmacies.value =  list

                Log.v("WIT", "list of : ${listPharmacies.value}")


            }

            activity?.findNavController(R.id.myNavHostFragment)?.popBackStack()
        }


        return binding.root
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            mMap.isMyLocationEnabled = true
        }
        else {
            activity?.requestPermissions(
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMaOnClick(map: GoogleMap) {

        map.setOnMapClickListener { latLng ->
            latitude.value = latLng.latitude
            longtitude.value = latLng.longitude

            val zoomLevel = 15f
            map.clear()
            var currentPosition = longtitude.value?.let { latitude.value?.let { it1 -> LatLng(it1, it) } }

            val geocoder = Geocoder(requireContext())
            val address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)
            val locality = address[0].locality
            placeLiveData.value = locality

            drawMarkerWithCircle(latLng)


            currentPosition?.let {
                MarkerOptions()
                    .position(it)
                    .title(it.toString())
            }?.let {
                mMap.addMarker(it)
            }


            currentPosition?.let { CameraUpdateFactory.newLatLngZoom(it,zoomLevel) }?.let {
                mMap.moveCamera(it)
            }
        }
    }



    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mMap = googleMap

        val zoomLevel = 15f
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    setLocation.value = location
                    latitude.value = location.latitude
                    longtitude.value = location.longitude
                    placeLiveData.value = MY_POSITION

                    var currentPosition = longtitude.value?.let { latitude.value?.let { it1 -> LatLng(it1, it) } }
                    val latLang = LatLng(location.latitude,location.longitude)
                    drawMarkerWithCircle(latLang)
                    currentPosition?.let {
                        MarkerOptions()
                            .position(it)
                            .title(placeLiveData.value)
                    }?.let {
                        mMap.addMarker(it)
                    }

                    currentPosition?.let { CameraUpdateFactory.newLatLngZoom(it,zoomLevel) }?.let { mMap.moveCamera(it) }
                }
            }

        setMaOnClick(mMap)
        enableMyLocation()

    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun drawMarkerWithCircle(position: LatLng) {
        val radiusInMeters = 500.0
        val strokeColor = -0x10000 //red outline
        val shadeColor = 0x44ff0000 //opaque red fill
        val circleOptions =
            CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor)
                .strokeColor(strokeColor).strokeWidth(8f)
        mCircle = mMap.addCircle(circleOptions)
        val markerOptions = MarkerOptions().position(position)
        mMarker = mMap.addMarker(markerOptions)
    }
}

