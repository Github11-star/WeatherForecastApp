package com.firozpoc.weatherforecastandroidapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.firozpoc.weatherforecastandroidapplication.databinding.ActivityMainBinding
import com.firozpoc.weatherforecastandroidapplication.model.Values
import com.firozpoc.weatherforecastandroidapplication.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocation()


        viewModel.weatherResponse.observe(this) { values ->

            binding.apply {
                //tvTemperature.text = values.temperature.toString()
            }
        }

        // Instance of values list using the data model class.
        try {
            val jsonString = getJSONFromAssets()!!
            val values = Gson().fromJson(jsonString, Values::class.java)
            tvTemperature.text = values.temperature.toString()
            windDirection.text = values.windDirection.toString()
            windGust.text = values.windGust.toString()
            tvLatitude.text = values.latitude.toString()
            tvLongitude.text = values.longitude.toString()

        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }
    }

    /**
     * Method to load the JSON from the Assets file and return the object
     */
    private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("Values.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    // location service code here
    // getting the current location using latitude and longitude
    private fun getCurrentLocation(){
        if (checkPermission()){
            if (isLocationEnabled()){

                // final latitude and longitude code here
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
                    val location : Location?=task.result
                    if (location == null){
                        Toast.makeText(this, "Null Recieved", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Get Success", Toast.LENGTH_LONG).show()
                        tvLatitude.text = ""+location.latitude
                        tvLongitude.text = ""+location.longitude
                    }
                }

            }else{
                // setting open here
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            // request permission here
            requestPermission()
        }
    }

    // this is the boolean method and checking the gps providers and network providers
    private fun isLocationEnabled() : Boolean {
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // checking the required coarse location and fine location
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    // checking the coarse location and fine location
    private fun checkPermission() : Boolean {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            return true
        }

        return false
    }

    // this method are checking the permission request access location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_LONG).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}