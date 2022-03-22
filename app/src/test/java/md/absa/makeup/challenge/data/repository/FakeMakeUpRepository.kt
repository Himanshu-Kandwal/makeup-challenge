package md.absa.makeup.challenge.data.repository

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import md.absa.makeup.challenge.model.MakeUpItem
import retrofit2.Response

class FakeMakeUpRepository : MakeUpRepositoryImpl {

    private val makeUpItemList = mutableListOf<MakeUpItem>()
    private val observableMakeUpItemList = MutableLiveData<List<MakeUpItem>>(makeUpItemList)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun fetchMakeUp(): Response<MakeUpResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun addToRoom(response: MakeUpResponse?) {
        TODO("Not yet implemented")
    }

    override fun getBrands(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

    override fun getProductsByBrand(brandName: String): Flow<List<MakeUpItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: String): MakeUpItem {
        TODO("Not yet implemented")
    }

    override suspend fun getProductsByProductType(productType: String?): List<MakeUpItem> {
        TODO("Not yet implemented")
    }
}
