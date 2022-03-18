package md.absa.makeup.challenge.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import md.absa.makeup.challenge.model.MakeUpItem
import md.absa.makeup.challenge.ui.viewholders.ProductsViewHolder

class ProductsAdapter(
    private val products: List<MakeUpItem?>
) : RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product: MakeUpItem? = products[position]
        if (product != null) {
            holder.bind(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
