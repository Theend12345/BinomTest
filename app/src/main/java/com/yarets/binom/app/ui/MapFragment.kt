package com.yarets.binom.app.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.yarets.binom.R
import com.yarets.binom.app.App
import com.yarets.binom.app.util.CustomInfoWindow
import com.yarets.binom.app.util.UiState
import com.yarets.binom.app.util.createMarkerIcon
import com.yarets.binom.databinding.FragmentMapBinding
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.overlay.Marker
import javax.inject.Inject

private val MY_LOCATION = GeoPoint(58.048120, 38.852319)

@SuppressLint("UseCompatLoadingForDrawables")
class MapFragment : Fragment() {
    private val fakeLocation: Marker by lazy {
        Marker(binding.map).apply {
            icon = requireContext().getDrawable(R.drawable.ic_mylocation_55dp)
            position = MY_LOCATION
        }
    }

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.bottomContainer.bottomSheet)
    }

    private val preference: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
    }

    private val binding: FragmentMapBinding by lazy {
        FragmentMapBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmFactory: VMFactory

    private val viewModel: MapViewModel by lazy {
        ViewModelProvider(this, vmFactory)[MapViewModel::class]
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            map.setMultiTouchControls(true)
            map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            myLocationBtn.setOnClickListener {
                centerMap()
            }
            zoomMinusBtn.setOnClickListener {
                map.controller.zoomOut()
            }
            zoomPlusBtn.setOnClickListener {
                map.controller.zoomIn()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.injectMapFragment(this@MapFragment)

        Configuration.getInstance().load(
            requireActivity().applicationContext,
            preference
        )
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(
            requireActivity().applicationContext,
            preference
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.overlays.add(fakeLocation)
        centerMap()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.personListResponse.collect { state ->
                    when (state) {
                        UiState.Default -> {}
                        is UiState.Error -> {}
                        UiState.Loading -> {}
                        is UiState.Success -> {
                            state.data.forEach {
                                addMarker(
                                    it.getGeoPosition(),
                                    it.name,
                                    "GPS, 14:00",
                                    it.id.toString(),
                                    it.image
                                )
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.personResponse.collect {
                    with(binding) {
                        bottomContainer.nameTxt.text = it.name
                        bottomContainer.image.setImageResource(it.image)
                    }
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                }
            }
        }
    }

    private fun onMarkerClick(id: String) {
        viewModel.fetchPersonById(id.toInt())
    }

    private fun addMarker(
        geoPoint: GeoPoint,
        title: String,
        description: String,
        id: String,
        image: Int
    ) {
        with(binding) {
            val marker = Marker(map)
            marker.position = geoPoint
            marker.id = id
            marker.title = title
            marker.icon =
                BitmapDrawable(
                    requireContext().resources,
                    createMarkerIcon(requireContext(), image)
                )
            marker.snippet = description
            marker.infoWindow = CustomInfoWindow(map)
            marker.showInfoWindow()
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)

            marker.setOnMarkerClickListener { m, _ ->
                onMarkerClick(m.id)
                true
            }
        }
    }

    private fun centerMap() {
        binding.map.controller.setZoom(17.5)
        binding.map.controller.animateTo(fakeLocation.position)
    }
}
