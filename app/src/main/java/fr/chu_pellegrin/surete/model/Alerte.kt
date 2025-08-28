package fr.chu_pellegrin.surete.model

data class Alerte(
    val type: String,
    val commentaire: String,
    val service: String,
    val latitude: Double,
    val longitude: Double,
    val date: Long
)
