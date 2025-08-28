package fr.chu_pellegrin.surete.data

import android.content.Context
import fr.chu_pellegrin.surete.model.Alerte
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class RemoteAlerteRepository(val context: Context) {
    private val client = OkHttpClient()
    private val baseUrl = "https://api.chu-pellegrin.fr/alertes" // À remplacer par l’URL réelle du backend

    fun envoyerAlerte(alerte: Alerte, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val json = JSONObject()
        json.put("type", alerte.type)
        json.put("commentaire", alerte.commentaire)
        json.put("service", alerte.service)
        json.put("latitude", alerte.latitude)
        json.put("longitude", alerte.longitude)
        json.put("date", alerte.date)
        val body = RequestBody.create(MediaType.get("application/json"), json.toString())
        val request = Request.Builder()
            .url(baseUrl)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { onError(e.message ?: "Erreur réseau") }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) onSuccess() else onError(response.message())
            }
        })
    }

    fun recupererAlertes(onResult: (List<Alerte>) -> Unit, onError: (String) -> Unit) {
        val request = Request.Builder().url(baseUrl).get().build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) { onError(e.message ?: "Erreur réseau") }
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) { onError(response.message()) ; return }
                val alertes = mutableListOf<Alerte>()
                val array = JSONArray(response.body()?.string())
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    alertes.add(Alerte(
                        type = obj.getString("type"),
                        commentaire = obj.getString("commentaire"),
                        service = obj.getString("service"),
                        latitude = obj.getDouble("latitude"),
                        longitude = obj.getDouble("longitude"),
                        date = obj.getLong("date")
                    ))
                }
                onResult(alertes)
            }
        })
    }
}
