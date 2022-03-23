package md.absa.makeup.challenge.data.repository

import md.absa.makeup.challenge.data.api.RetrofitInterface
import md.absa.makeup.challenge.data.db.MakeUpDatabase
import md.absa.makeup.challenge.model.MakeUpItem
import timber.log.Timber
import javax.inject.Inject

open class MakeUpRepositoryImpl @Inject constructor(
    private val retrofitInterface: RetrofitInterface,
    private val makeUpDatabase: MakeUpDatabase
) : MakeUpRepository {

    override suspend fun fetchMakeUp() =
        retrofitInterface.fetchMakeUpProducts().also {
            Timber.e("ADDED TO ROOM > $it")
            val list = mutableListOf<MakeUpItem>()
            for (item in it.body()!!) {
                list.add(item)
            }
            addToRoom(list)
        }

    override suspend fun addToRoom(response: List<MakeUpItem>) {
        makeUpDatabase.makeUpItemDao().nukeTable()
        makeUpDatabase.makeUpItemDao().insert(response)
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
