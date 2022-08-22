package com.firozpoc.weatherforecastandroidapplication

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firozpoc.weatherforecastandroidapplication.databinding.ActivityMainBinding
import com.firozpoc.weatherforecastandroidapplication.model.Values
import com.firozpoc.weatherforecastandroidapplication.viewmodel.WeatherViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}