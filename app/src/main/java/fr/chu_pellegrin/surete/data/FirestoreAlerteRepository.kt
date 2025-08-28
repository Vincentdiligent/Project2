package fr.chu_pellegrin.surete.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import fr.chu_pellegrin.surete.model.Alerte

class FirestoreAlerteRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("alertes")

    fun envoyerAlerte(alerte: Alerte, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val data = hashMapOf(
            "type" to alerte.type,
            "commentaire" to alerte.commentaire,
            "service" to alerte.service,
            "latitude" to alerte.latitude,
            "longitude" to alerte.longitude,
            "date" to alerte.date
        )
        collection.add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it.message ?: "Erreur Firestore") }
    }

    fun recupererAlertes(onResult: (List<Alerte>) -> Unit, onError: (String) -> Unit) {
        collection.orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val alertes = result.mapNotNull { doc ->
                    try {
                        Alerte(
                            type = doc.getString("type") ?: "",
                            commentaire = doc.getString("commentaire") ?: "",
                            service = doc.getString("service") ?: "",
                            latitude = doc.getDouble("latitude") ?: 0.0,
                            longitude = doc.getDouble("longitude") ?: 0.0,
                            date = doc.getLong("date") ?: 0L
                        )
                    } catch (e: Exception) { null }
                }
                onResult(alertes)
            }
            .addOnFailureListener { onError(it.message ?: "Erreur Firestore") }
    }
}
