package md.absa.makeup.challenge.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.absa.makeup.challenge.model.MakeUpItem
import md.absa.makeup.challenge.ui.viewholders.SimilarProductsViewHolder

class SimilarProductsAdapter(
    private val products: List<MakeUpItem?>?
) : RecyclerView.Adapter<SimilarProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarProductsViewHolder {
        return SimilarProductsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SimilarProductsViewHolder, position: Int) {
        if (products == null) return
        val product: MakeUpItem? = products[position]
        if (product != null) {
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return products!!.size
    }
}
