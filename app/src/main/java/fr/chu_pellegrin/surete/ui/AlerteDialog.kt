package fr.chu_pellegrin.surete.ui

import android.app.AlertDialog
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import fr.chu_pellegrin.surete.R

/**
 * Dialogue d’envoi d’alerte : type, commentaire, service, géolocalisation
 * Enregistre l’alerte dans l’historique local
 */
fun MainActivity.showAlerteDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_alerte, null)
    val spinnerType = dialogView.findViewById<Spinner>(R.id.spinnerTypeAlerte)
    val editTextCommentaire = dialogView.findViewById<EditText>(R.id.editTextCommentaire)
    val editTextService = dialogView.findViewById<EditText>(R.id.editTextServiceAssocie)

    val adapter = ArrayAdapter.createFromResource(
        this,
        R.array.types_alerte,
        android.R.layout.simple_spinner_item
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinnerType.adapter = adapter

    val dialog = AlertDialog.Builder(this)
        .setTitle(R.string.alerte)
        .setView(dialogView)
        .setNegativeButton(android.R.string.cancel, null)
        .create()

    dialogView.findViewById<Button>(R.id.buttonEnvoyerAlerte).setOnClickListener {
        val type = spinnerType.selectedItem.toString()
        val commentaire = editTextCommentaire.text.toString()
        val service = editTextService.text.toString()
        val location: Location? = myLocationOverlay.myLocation
        if (location != null) {
            // Envoi de l’alerte via Firestore (synchronisation multi-agents)
            val alerte = fr.chu_pellegrin.surete.model.Alerte(
                type = type,
                commentaire = commentaire,
                service = service,
                latitude = location.latitude,
                longitude = location.longitude,
                date = System.currentTimeMillis()
            )
            fr.chu_pellegrin.surete.data.FirestoreAlerteRepository().envoyerAlerte(alerte,
                onSuccess = {
                    Toast.makeText(this, "Alerte envoyée !", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                },
                onError = {
                    Toast.makeText(this, "Erreur d’envoi : $it", Toast.LENGTH_LONG).show()
                }
            )
        } else {
            Toast.makeText(this, "Position inconnue. Activez la localisation.", Toast.LENGTH_SHORT).show()
        }
    }
    dialog.show()
}
