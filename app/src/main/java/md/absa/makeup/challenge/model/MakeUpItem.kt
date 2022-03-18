package md.absa.makeup.challenge.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * I noticed some fields are NULL
 * So I decided to make all fields nullable
 * because I'm not sure if the data will change tomorrow
 * ..except the ID
 */
@Entity(tableName = "make_up_item")
data class MakeUpItem(
    @PrimaryKey(autoGenerate = true) val localId: Int,
    val id: Int,
    val brand: String?,
    val created_at: String?,
    val currency: String?,
    val name: String?,
    val price: String?,
    val rating: Double?,
    val api_featured_image: String?,
    val image_link: String?,
    val price_sign: String?,
    val product_api_url: String?,
    val product_link: String?,
    val product_type: String?,
    val website_link: String?,
    val updated_at: String?,
    val category: String?,
    val description: String?,
    val tag_list: ArrayList<String>?,
    val product_colors: ArrayList<ProductColor>?,
)