package md.absa.makeup.challenge.data.repository

import kotlinx.coroutines.flow.Flow
import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import md.absa.makeup.challenge.model.MakeUpItem
import retrofit2.Response

interface MakeUpRepository {

    suspend fun fetchMakeUp(): Response<MakeUpResponse>

    suspend fun addToRoom(response: List<MakeUpItem>)

    fun getBrands(): Flow<List<String>>

    fun getProductsByBrand(brandName: String): Flow<List<MakeUpItem>>

    suspend fun getProductById(id: String): MakeUpItem

    suspend fun getProductsByProductType(productType: String?): List<MakeUpItem>
}
