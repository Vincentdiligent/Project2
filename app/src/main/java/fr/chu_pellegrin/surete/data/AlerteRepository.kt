package fr.chu_pellegrin.surete.data

import android.content.Context
import fr.chu_pellegrin.surete.model.Alerte

/**
 * Repository local pour la gestion de l’historique des alertes (SharedPreferences)
 */
class AlerteRepository(context: Context) {
    private val prefs = context.getSharedPreferences("alertes", Context.MODE_PRIVATE)

    fun ajouterAlerte(alerte: Alerte) {
        val historique = getAlertes().toMutableList()
        historique.add(alerte)
        val serialized = historique.joinToString(";;;") {
            listOf(it.type, it.commentaire, it.service, it.latitude, it.longitude, it.date).joinToString("|||")
        }
        prefs.edit().putString("historique", serialized).apply()
    }

    fun getAlertes(): List<Alerte> {
        val serialized = prefs.getString("historique", null) ?: return emptyList()
        return serialized.split(";;;").mapNotNull {
            val parts = it.split("|||")
            if (parts.size == 6) {
                Alerte(
                    type = parts[0],
                    commentaire = parts[1],
                    service = parts[2],
                    latitude = parts[3].toDoubleOrNull() ?: 0.0,
                    longitude = parts[4].toDoubleOrNull() ?: 0.0,
                    date = parts[5].toLongOrNull() ?: 0L
                )
            } else null
        }
    }
}
