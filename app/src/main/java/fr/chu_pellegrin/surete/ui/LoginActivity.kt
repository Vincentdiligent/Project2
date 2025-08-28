package fr.chu_pellegrin.surete.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.chu_pellegrin.surete.R

/**
 * Activité de connexion du personnel (immatriculation + mot de passe)
 * Gère la distinction entre profils sûreté et employés.
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Récupération des éléments de la vue
        val editTextImmatriculation = findViewById<EditText>(R.id.editTextImmatriculation)
        val editTextMotDePasse = findViewById<EditText>(R.id.editTextMotDePasse)
        val buttonConnexion = findViewById<Button>(R.id.buttonConnexion)

        buttonConnexion.setOnClickListener {
            val immatriculation = editTextImmatriculation.text.toString()
            val motDePasse = editTextMotDePasse.text.toString()

            // Authentification locale fictive pour démonstration (à remplacer par une vraie logique)
            if (immatriculation == "surete" && motDePasse == "surete") {
                // Accès profil sûreté
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("role", "surete")
                startActivity(intent)
                finish()
            } else if (immatriculation.isNotEmpty() && motDePasse.isNotEmpty()) {
                // Accès profil employé
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("role", "employe")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, getString(R.string.erreur_connexion), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
