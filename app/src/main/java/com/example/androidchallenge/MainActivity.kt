package com.example.androidchallenge

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidchallenge.databinding.ActivityMainBinding
import com.example.androidchallenge.network.api.RetrofitService
import com.example.androidchallenge.network.api.WeatherApi
import com.example.androidchallenge.network.models.WeatherData
import com.example.androidchallenge.ui.ForecastFragment
import com.example.androidchallenge.ui.WeatherViewModel
import com.example.androidchallenge.utils.Result
import com.example.androidchallenge.utils.ViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private val TAG = MainActivity::class.java.name
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private lateinit var weather: WeatherData;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        toolBar = binding.toolbar

        //Add back navigation in the title bar
        setSupportActionBar(toolBar)

        binding.contentMain.forecastButton.setOnClickListener {
            replaceFragment(ForecastFragment.newInstance(), ForecastFragment.TAG)
        }
        initViewModels()
        initObservers()
    }

    private fun replaceFragment(fragment: Fragment, tag: String?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.add(R.id.fragmentContainerView, fragment, tag).commit()
    }

    private fun initViewModels() {
            viewModel = ViewModelProvider(this,
                ViewModelFactory(RetrofitService.createService(WeatherApi::class.java))
            ).get(
                WeatherViewModel::class.java)
            viewModel.loadData()
    }
    private fun initObservers() {
        viewModel.getWeather().observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    weather = result.data;
                    val geocoder = Geocoder(this, Locale.getDefault())
                    try {
                        val addresses = geocoder.getFromLocation(weather.lat, weather.lon, 1)
                        binding.contentMain.location.text = addresses[0].locality
                    } catch(exception:Exception){
                        Log.d(TAG,"Error location")
                    }
                    binding.contentMain.temp.text = weather.current.temp.toString().plus(" °F")
                    binding.contentMain.feelsLike.text = weather.current.feels_like.toString().plus(" °F")
                    binding.contentMain.humidity.text = weather.current.humidity.toString().plus(" %")
                    binding.contentMain.windSpeed.text = weather.current.wind_speed.toString().plus("m/s") +
                            " at  " + weather.current.wind_deg.toString().plus("°")
                    binding.contentMain.pressure.text = weather.current.pressure.toString().plus(" hPa")
                }
                is Result.InProgress -> {
                    print("in Progress")
                }
                is Result.Error -> {
                    Toast.makeText(this, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}