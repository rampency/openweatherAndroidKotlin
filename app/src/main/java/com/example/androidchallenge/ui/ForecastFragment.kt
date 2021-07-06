package com.example.androidchallenge.ui

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.androidchallenge.MainActivity
import com.example.androidchallenge.databinding.ForecastFragmentBinding
import com.example.androidchallenge.network.models.ForecastWeather
import com.example.androidchallenge.utils.Result
import java.util.*

class ForecastFragment : Fragment() {
    private lateinit var binding: ForecastFragmentBinding
    private val viewModel: WeatherViewModel by activityViewModels()
    companion object {
        val TAG = ForecastFragment::class.qualifiedName
        @JvmStatic fun newInstance() = ForecastFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = ForecastFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this@ForecastFragment
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.getWeather().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    val weather = result.data;
                    renderList(weather.daily)

                }
                is Result.InProgress -> {
                    print("in Progress")
                }
                is Result.Error -> {
                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(weatherList: List<ForecastWeather>) {
        if (weatherList.isNotEmpty()) {
            //when screen starts
            setRecyclerData(weatherList)
        } else {
            Log.d(TAG, "Error Rendering List")
        }
    }

    private fun setRecyclerData(weatherList: List<ForecastWeather>) {
        with(binding.list) {
            adapter = DailyForecastRecyclerViewAdapter(weatherList)
        }
    }

}