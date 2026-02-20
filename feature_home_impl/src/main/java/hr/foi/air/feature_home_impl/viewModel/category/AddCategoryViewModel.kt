package hr.foi.air.feature_home_impl.viewModel.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.foi.air.core.network.CategoryApiService
import hr.foi.air.core.network.data.CategoryData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryApi: CategoryApiService
) : ViewModel() {

    private val _categoryAdded = MutableStateFlow(false)
    val categoryAdded: StateFlow<Boolean> = _categoryAdded
    fun addCategory(category: CategoryData) {
        Log.d("ExpenseDebug", "Category to send: $category")
        viewModelScope.launch {
            val response = categoryApi.createCategory(category)
            println("RESPONSE: ${response.code()} ${response.isSuccessful}")
            if (response.isSuccessful) {
                _categoryAdded.value = true  // oznaka da je unos uspješan
            }else{println("ERROR BODY: ${response.errorBody()?.string()}")}
        }
    }

    fun onSnackShown() {
        _categoryAdded.value = false
    }
}
