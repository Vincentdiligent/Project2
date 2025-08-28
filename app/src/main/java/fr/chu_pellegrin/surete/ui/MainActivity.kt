package fr.chu_pellegrin.surete.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import fr.chu_pellegrin.surete.R
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider

/**
 * Activité principale : carte interactive, bouton d’alerte, accès historique (sûreté)
 * Affiche la position de l’utilisateur sur la carte (OpenStreetMap)
 */
class MainActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            enableMyLocation()
        } else {
            Toast.makeText(this, "La géolocalisation est nécessaire pour envoyer une alerte.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osm_prefs", MODE_PRIVATE))
        setContentView(R.layout.activity_main)

        map = findViewById(R.id.map)
        map.setMultiTouchControls(true)
        map.controller.setZoom(18.0)
        map.controller.setCenter(GeoPoint(44.8333, -0.6033)) // Coordonnées CHU Pellegrin

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), map)
        map.overlays.add(myLocationOverlay)

        val buttonAlerte = findViewById<Button>(R.id.buttonAlerte)
        buttonAlerte.setOnClickListener { showAlerteDialog() }

        val buttonHistorique = findViewById<Button>(R.id.buttonHistorique)
        val role = intent.getStringExtra("role")
        if (role == "surete") {
            buttonHistorique.visibility = android.view.View.VISIBLE
            buttonHistorique.setOnClickListener {
                startActivity(android.content.Intent(this, HistoriqueActivity::class.java))
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            enableMyLocation()
        }
    }

    private fun enableMyLocation() {
        myLocationOverlay.enableMyLocation()
    }

}
