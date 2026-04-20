package com.example.seedrapp.ui.adapter
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.*
import com.example.seedrapp.model.*

class FileAdapter(private val list: List<SeedrFile>, val click:(SeedrFile)->Unit)
: RecyclerView.Adapter<FileAdapter.VH>() {

    class VH(val tv: TextView): RecyclerView.ViewHolder(tv)

    override fun onCreateViewHolder(p: ViewGroup, v: Int): VH {
        val tv = TextView(p.context)
        tv.textSize = 18f
        tv.setPadding(20,20,20,20)
        return VH(tv)
    }

    override fun onBindViewHolder(h: VH, i: Int) {
        val f = list[i]
        h.tv.text = f.name
        h.tv.setOnClickListener { click(f) }
    }

    override fun getItemCount() = list.size
}
