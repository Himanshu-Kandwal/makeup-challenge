package md.absa.makeup.challenge.ui.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import md.absa.makeup.challenge.data.api.resource.NetworkResource
import md.absa.makeup.challenge.data.repository.MakeUpRepositoryImpl
import md.absa.makeup.challenge.model.MakeUpItem
import javax.inject.Inject

@HiltViewModel
class MakeUpViewModel @Inject constructor(
    private val repositoryImpl: MakeUpRepositoryImpl
) : ViewModel() {

    private val _makeUpItems: MutableLiveData<NetworkResource<List<MakeUpItem?>>> = MutableLiveData()
    val makeUpItems: LiveData<NetworkResource<List<MakeUpItem?>>> = _makeUpItems

    private val _singleProduct: MutableLiveData<NetworkResource<MakeUpItem?>> = MutableLiveData()
    val singleProduct: LiveData<NetworkResource<MakeUpItem?>> = _singleProduct

    private val _similarProducts: MutableLiveData<NetworkResource<List<MakeUpItem?>>> = MutableLiveData()
    val similarProducts: LiveData<NetworkResource<List<MakeUpItem?>>> = _similarProducts

    fun fetchMakeup() =
        viewModelScope.launch {
            _makeUpItems.value = NetworkResource.loading(message = "Loading")
            kotlin.runCatching {
                repositoryImpl.fetchMakeUp()
            }.onSuccess { response ->
                val data = mutableListOf<MakeUpItem>()
                for (item in response.body()!!) {
                    data.add(item)
                }
                _makeUpItems.value = NetworkResource.success(data = data)
            }.onFailure { error ->
                _makeUpItems.value = NetworkResource.error(message = error.message ?: "Some error occurred")
            }
        }

    fun getProductById(id: String) =
        viewModelScope.launch {
            _singleProduct.value = NetworkResource.loading(message = "Loading")
            kotlin.runCatching {
                repositoryImpl.getProductById(id)
            }.onSuccess { response ->
                _singleProduct.value = NetworkResource.success(data = response)
            }.onFailure { error ->
                _singleProduct.value = NetworkResource.error(message = error.message ?: "Some error occurred")
            }
        }

    fun getProductsByProductType(productType: String?) =
        viewModelScope.launch {
            _similarProducts.value = NetworkResource.loading(message = "Loading")
            kotlin.runCatching {
                repositoryImpl.getProductsByProductType(productType)
            }.onSuccess { response ->
                _similarProducts.value = NetworkResource.success(data = response)
            }.onFailure { error ->
                _similarProducts.value = NetworkResource.error(message = error.message ?: "Some error occurred")
            }
        }

    fun getBrands() = repositoryImpl.getBrands()

    fun getProductsByBrand(brandName: String) = repositoryImpl.getProductsByBrand(brandName)
}
