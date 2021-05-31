package com.example.myweatherapp.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.databinding.FragmentDetailsBinding
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.utils.showSnackBar
import com.example.myweatherapp.app.AppState
import com.example.myweatherapp.viewmodel.DetailsViewModel


class WeatherFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherBundle: Weather
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Weather()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setWeather(appState.weatherData[0])
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reload),
                        {
                            viewModel.getWeatherFromRemoteSource(
                                    weatherBundle.city.lat,
                                    weatherBundle.city.lon
                            )
                        })
            }
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"
        fun newInstance(bundle: Bundle): WeatherFragment {
            val fragment = WeatherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun setWeather(weather: Weather) {
        with(binding) {
            val city = weatherBundle.city
            cityName.text = city.city
            cityCoordinates.text = String.format(
                    getString(R.string.city_coordinates),
                    city.lat.toString(),
                    city.lon.toString()
            )

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
