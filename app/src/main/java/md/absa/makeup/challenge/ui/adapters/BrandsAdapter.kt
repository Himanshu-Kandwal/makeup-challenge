package md.absa.makeup.challenge.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.absa.makeup.challenge.ui.viewholders.BrandsViewHolder

class BrandsAdapter(private val brands: List<String?>) : RecyclerView.Adapter<BrandsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        return BrandsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BrandsViewHolder, position: Int) {
        val brand: String? = brands[position]
        if (brand != null) {
            holder.bind(brand)
        }
    }

    override fun getItemCount(): Int {
        return brands.size
    }
}
