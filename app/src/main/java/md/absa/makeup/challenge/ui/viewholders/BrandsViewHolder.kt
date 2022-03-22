package md.absa.makeup.challenge.ui.viewholders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import md.absa.makeup.challenge.R

class BrandsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.brand_name)

    init {
        itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("brand", name.text.toString())
            findNavController(itemView).navigate(R.id.action_brandsFragment_to_productsFragment, bundle)
        }
    }

    fun bind(brand: String) {
        name.text = brand.trim()
    }

    companion object {
        fun create(parent: ViewGroup): BrandsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_brands_list, parent, false)
            return BrandsViewHolder(view)
        }
    }
}
