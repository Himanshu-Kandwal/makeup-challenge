package md.absa.makeup.challenge.ui.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import md.absa.makeup.challenge.data.api.resource.NetworkResource
import md.absa.makeup.challenge.data.api.response.MakeUpResponse
import md.absa.makeup.challenge.data.repository.MakeUpRepository
import md.absa.makeup.challenge.model.MakeUpItem
import md.absa.makeup.challenge.prefs_datastore.PrefsStore
import javax.inject.Inject

@HiltViewModel
class MakeUpViewModel @Inject constructor(
    private val repository: MakeUpRepository,
    private val prefsStore: PrefsStore
) : ViewModel() {

    private val _makeUpItems: MutableLiveData<NetworkResource<MakeUpResponse?>> = MutableLiveData()
    val makeUpItems: LiveData<NetworkResource<MakeUpResponse?>>
        get() {
            return _makeUpItems
        }

    private val _singleProduct: MutableLiveData<NetworkResource<MakeUpItem?>> = MutableLiveData()
    val singleProduct: LiveData<NetworkResource<MakeUpItem?>>
        get() {
            return _singleProduct
        }

    private val _similarProducts: MutableLiveData<NetworkResource<List<MakeUpItem?>>> = MutableLiveData()
    val similarProducts: LiveData<NetworkResource<List<MakeUpItem?>>>
        get() {
            return _similarProducts
        }

    fun fetchMakeup() =
        viewModelScope.launch {
            _makeUpItems.value = NetworkResource.loading(message = "Loading")
            kotlin.runCatching {
                repository.fetchMakeUp()
            }.onSuccess { response ->
                _makeUpItems.value = NetworkResource.success(data = response.body())
            }.onFailure { error ->
                _makeUpItems.value = NetworkResource.error(message = error.message ?: "Some error occurred")
            }
        }

    fun getProductById(id: String) =
        viewModelScope.launch {
            _singleProduct.value = NetworkResource.loading(message = "Loading")
            kotlin.runCatching {
                repository.getProductById(id)
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
                repository.getProductsByProductType(productType)
            }.onSuccess { response ->
                _similarProducts.value = NetworkResource.success(data = response)
            }.onFailure { error ->
                _similarProducts.value = NetworkResource.error(message = error.message ?: "Some error occurred")
            }
        }

    fun toggleNightMode() {
        viewModelScope.launch {
            prefsStore.toggleNightMode()
        }
    }
    val darkThemeEnabled = prefsStore.isNightMode().asLiveData()

    fun welcomeScreenInfo() = prefsStore.isWelcomeScreenShown().asLiveData()

    suspend fun setWelcomeScreenInfo(value: Boolean) = prefsStore.setWelcomeScreenShown(value)

    fun getBrands() = repository.getBrands()

    fun getProductsByBrand(brandName: String) = repository.getProductsByBrand(brandName)
}
