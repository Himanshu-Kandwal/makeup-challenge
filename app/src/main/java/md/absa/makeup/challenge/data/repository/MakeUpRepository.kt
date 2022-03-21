package md.absa.makeup.challenge.data.repository

import md.absa.makeup.challenge.data.api.RetrofitInterface
import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import md.absa.makeup.challenge.data.db.MakeUpDatabase
import retrofit2.Response
import javax.inject.Inject

class MakeUpRepository @Inject constructor(
    private val retrofitInterface: RetrofitInterface,
    private val makeUpDatabase: MakeUpDatabase
) {
    suspend fun fetchMakeUp(): Response<MakeUpResponse> =
        retrofitInterface.fetchMakeUpProducts().also {
            addToRoom(it.body())
        }

    private suspend fun addToRoom(response: MakeUpResponse?) {
        makeUpDatabase.makeUpItemDao().nukeTable()
        makeUpDatabase.makeUpItemDao().insert(response!!)
    }

    fun getBrands() =
        makeUpDatabase.makeUpItemDao().getBrands()

    fun getProductsByBrand(brandName: String) =
        makeUpDatabase.makeUpItemDao().getProductsByBrand(brandName)

    suspend fun getProductById(id: String) =
        makeUpDatabase.makeUpItemDao().getProductById(id)

    suspend fun getProductsByProductType(productType: String?) =
        makeUpDatabase.makeUpItemDao().getProductsByProductType(productType)
}
