package fr.chu_pellegrin.surete.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import fr.chu_pellegrin.surete.R
import fr.chu_pellegrin.surete.data.AlerteRepository
import fr.chu_pellegrin.surete.model.Alerte

/**
 * Activité d’affichage de l’historique des alertes (réservé à la sûreté)
 */
class HistoriqueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        val listView = findViewById<ListView>(R.id.listViewAlertes)
        fr.chu_pellegrin.surete.data.FirestoreAlerteRepository().recupererAlertes(
            onResult = { alertes ->
                listView.adapter = HistoriqueAdapter(this, alertes)
            },
            onError = {
                android.widget.Toast.makeText(this, "Erreur de chargement : $it", android.widget.Toast.LENGTH_LONG).show()
            }
        )
    }
}
