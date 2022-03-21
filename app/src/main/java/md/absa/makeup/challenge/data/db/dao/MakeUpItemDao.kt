package md.absa.makeup.challenge.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import md.absa.makeup.challenge.model.MakeUpItem

@Dao
interface MakeUpItemDao : BaseDao<MakeUpItem> {

    @Query("SELECT DISTINCT(brand) FROM make_up_item ORDER BY id")
    fun getBrands(): Flow<List<String>>

    @Query("SELECT * FROM make_up_item WHERE brand = :brandName ORDER BY id")
    fun getProductsByBrand(brandName: String): Flow<List<MakeUpItem>>

    @Query("SELECT * FROM make_up_item WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: String): MakeUpItem

    @Query("SELECT * FROM make_up_item WHERE product_type = :productType LIMIT 10")
    suspend fun getProductsByProductType(productType: String?): List<MakeUpItem>

    @Query("DELETE FROM make_up_item")
    fun nukeTable()
}
