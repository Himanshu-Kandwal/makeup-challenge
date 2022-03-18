package md.absa.makeup.challenge.ui.viewholders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import md.absa.makeup.challenge.R
import md.absa.makeup.challenge.model.MakeUpItem

class SimilarProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val productId: TextView = itemView.findViewById(R.id.productId)
    private val image: ImageView = itemView.findViewById(R.id.image)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val price: TextView = itemView.findViewById(R.id.price)
    private val category: TextView = itemView.findViewById(R.id.category)
    private val rating: TextView = itemView.findViewById(R.id.rating)

    init {
        itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("product_id", productId.text.toString())
            val navController: NavController = findNavController(itemView)
            navController.run {
                popBackStack()
                navigate(R.id.productFragment, bundle)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(product: MakeUpItem) {
        productId.text = product.id.toString()
        val imageUrl = product.image_link ?: product.api_featured_image
        image.load(imageUrl) {
            placeholder(R.drawable.placeholder)
        }
        name.text = product.name
        price.text = (product.currency ?: "KES:") + product.price
        category.text = "Category: " + (product.category ?: "N/A")
        rating.text = "Rating: " + (product.rating ?: "N/A")
    }

    companion object {
        fun create(parent: ViewGroup): SimilarProductsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_products_similar_list, parent, false)
            return SimilarProductsViewHolder(view)
        }
    }
}
