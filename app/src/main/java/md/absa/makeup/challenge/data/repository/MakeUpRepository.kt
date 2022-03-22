package md.absa.makeup.challenge.data.repository

import md.absa.makeup.challenge.data.api.RetrofitInterface
import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import md.absa.makeup.challenge.data.db.MakeUpDatabase
import javax.inject.Inject

class MakeUpRepository @Inject constructor(
    private val retrofitInterface: RetrofitInterface,
    private val makeUpDatabase: MakeUpDatabase
) : MakeUpRepositoryImpl {

    override suspend fun fetchMakeUp() =
        retrofitInterface.fetchMakeUpProducts().also {
            addToRoom(it.body())
        }

    override suspend fun addToRoom(response: MakeUpResponse?) {
        makeUpDatabase.makeUpItemDao().nukeTable()
        makeUpDatabase.makeUpItemDao().insert(response!!)
    }

    override fun getBrands() =
        makeUpDatabase.makeUpItemDao().getBrands()

    override fun getProductsByBrand(brandName: String) =
        makeUpDatabase.makeUpItemDao().getProductsByBrand(brandName)

    override suspend fun getProductById(id: String) =
        makeUpDatabase.makeUpItemDao().getProductById(id)

    override suspend fun getProductsByProductType(productType: String?) =
        makeUpDatabase.makeUpItemDao().getProductsByProductType(productType)
}
