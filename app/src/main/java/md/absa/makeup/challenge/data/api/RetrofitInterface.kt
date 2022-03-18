package md.absa.makeup.challenge.data.api

import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("api/v1/products.json")
    suspend fun fetchMakeUpProducts(): Response<MakeUpResponse>
}
