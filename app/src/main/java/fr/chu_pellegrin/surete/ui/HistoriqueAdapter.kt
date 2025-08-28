package fr.chu_pellegrin.surete.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.chu_pellegrin.surete.R
import fr.chu_pellegrin.surete.model.Alerte
import java.text.SimpleDateFormat
import java.util.*

class HistoriqueAdapter(context: Context, alertes: List<Alerte>) : ArrayAdapter<Alerte>(context, 0, alertes) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val alerte = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_alerte, parent, false)
        view.findViewById<TextView>(R.id.textType).text = alerte?.type
        view.findViewById<TextView>(R.id.textService).text = alerte?.service
        view.findViewById<TextView>(R.id.textCommentaire).text = alerte?.commentaire
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE)
        view.findViewById<TextView>(R.id.textDate).text = alerte?.date?.let { sdf.format(Date(it)) }
        return view
    }
}
