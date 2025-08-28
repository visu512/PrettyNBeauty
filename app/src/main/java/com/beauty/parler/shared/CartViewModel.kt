//package com.beauty.parler.shared
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.beauty.parler.model.CartItem
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
//    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
//    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()
//
//    private val _itemCount = MutableStateFlow(0)
//    val itemCount: StateFlow<Int> = _itemCount.asStateFlow()
//
//    private val _showSnackbar = MutableStateFlow(false)
//    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()
//
//    private val _snackbarMessage = MutableStateFlow("")
//    val snackbarMessage: StateFlow<String> = _snackbarMessage.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            cartRepository.getCartItems().collect { items ->
//                _cartItems.value = items
//                _itemCount.value = calculateTotalItems(items)
//            }
//        }
//    }
//
//    private fun calculateTotalItems(items: List<CartItem>): Int {
//        return items.sumOf { it.quantity }
//    }
//
//    fun addToCart(item: CartItem) {
//        viewModelScope.launch {
//            val currentItems = _cartItems.value.toMutableList()
//            val existingItemIndex = currentItems.indexOfFirst { it.productId == item.productId && !it.isPackage }
//
//            if (existingItemIndex >= 0) {
//                // Item exists, update quantity
//                val existingItem = currentItems[existingItemIndex]
//                currentItems[existingItemIndex] = existingItem.copy(
//                    quantity = existingItem.quantity + item.quantity
//                )
////                _snackbarMessage.value = "${item.name} quantity updated"
//            } else {
//                // New item, add to cart
//                currentItems.add(item)
////                _snackbarMessage.value = "${item.name} added to cart"
//            }
//
//            _cartItems.value = currentItems
//            _itemCount.value = calculateTotalItems(currentItems)
//            _showSnackbar.value = true
//            cartRepository.saveCartItems(currentItems)
//        }
//    }
//
//    fun updateQuantity(itemId: String, newQuantity: Int) {
//        viewModelScope.launch {
//            val currentItems = _cartItems.value.toMutableList()
//            val itemIndex = currentItems.indexOfFirst { it.id == itemId }
//
//            if (itemIndex != -1) {
//                if (newQuantity > 0) {
//                    currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = newQuantity)
//                    _snackbarMessage.value = "${currentItems[itemIndex].name} quantity updated"
//                } else {
//                    val removedItem = currentItems.removeAt(itemIndex)
//                    _snackbarMessage.value = "${removedItem.name} removed from cart"
//                }
//                _showSnackbar.value = true
//                _cartItems.value = currentItems
//                _itemCount.value = calculateTotalItems(currentItems)
//                cartRepository.saveCartItems(currentItems)
//            }
//        }
//    }
//
//    fun removeFromCart(itemId: String) {
//        viewModelScope.launch {
//            val currentItems = _cartItems.value.toMutableList()
//            val removedItem = currentItems.find { it.id == itemId }
//            currentItems.removeAll { it.id == itemId }
//
//            removedItem?.let {
//                _snackbarMessage.value = "${it.name} removed from cart"
//                _showSnackbar.value = true
//            }
//
//            _cartItems.value = currentItems
//            _itemCount.value = calculateTotalItems(currentItems)
//            cartRepository.saveCartItems(currentItems)
//        }
//    }
//
//    fun updatePersons(itemId: String, newPersons: Int) {
//        viewModelScope.launch {
//            if (newPersons < 1) {
//                removeFromCart(itemId)
//            } else {
//                cartRepository.updatePersons(itemId, newPersons)
//            }
//        }
//    }
//
//    fun clearCart() {
//        viewModelScope.launch {
//            _cartItems.value = emptyList()
//            _itemCount.value = 0
//            _snackbarMessage.value = "Cart cleared"
//            _showSnackbar.value = true
//            cartRepository.saveCartItems(emptyList())
//        }
//    }
//
//    fun resetSnackbar() {
//        _showSnackbar.value = false
//    }
//
//    fun isItemInCart(id: String): Boolean {
//        return _cartItems.value.any { it.productId == id }
//    }
//}











package com.beauty.parler.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beauty.parler.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _itemCount = MutableStateFlow(0)
    val itemCount: StateFlow<Int> = _itemCount.asStateFlow()

    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar: StateFlow<Boolean> = _showSnackbar.asStateFlow()

    private val _snackbarMessage = MutableStateFlow("")
    val snackbarMessage: StateFlow<String> = _snackbarMessage.asStateFlow()

    init {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { items ->
                _cartItems.value = items
                _itemCount.value = calculateTotalItems(items)
            }
        }
    }

    private fun calculateTotalItems(items: List<CartItem>): Int {
        return items.sumOf { it.quantity }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            val existingItemIndex = currentItems.indexOfFirst { it.productId == item.productId && !it.isPackage }

            if (existingItemIndex >= 0) {
                // Item exists, update quantity
                val existingItem = currentItems[existingItemIndex]
                currentItems[existingItemIndex] = existingItem.copy(
                    quantity = existingItem.quantity + item.quantity
                )
                _snackbarMessage.value = "${item.name} quantity updated"
            } else {
                // New item, add to cart
                currentItems.add(item)
                _snackbarMessage.value = "${item.name} added to cart"
            }

            _cartItems.value = currentItems
            _itemCount.value = calculateTotalItems(currentItems)
            _showSnackbar.value = true
            cartRepository.saveCartItems(currentItems)
        }
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            val itemIndex = currentItems.indexOfFirst { it.id == itemId }

            if (itemIndex != -1) {
                if (newQuantity > 0) {
                    currentItems[itemIndex] = currentItems[itemIndex].copy(quantity = newQuantity)
                    _snackbarMessage.value = "${currentItems[itemIndex].name} quantity updated"
                } else {
                    val removedItem = currentItems.removeAt(itemIndex)
                    _snackbarMessage.value = "${removedItem.name} removed from cart"
                }
                _showSnackbar.value = true
                _cartItems.value = currentItems
                _itemCount.value = calculateTotalItems(currentItems)
                cartRepository.saveCartItems(currentItems)
            }
        }
    }

    fun updateCartItem(updatedItem: CartItem) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            val itemIndex = currentItems.indexOfFirst { it.id == updatedItem.id }

            if (itemIndex != -1) {
                currentItems[itemIndex] = updatedItem
                _cartItems.value = currentItems
                cartRepository.saveCartItems(currentItems)
            }
        }
    }

    fun removeFromCart(itemId: String) {
        viewModelScope.launch {
            val currentItems = _cartItems.value.toMutableList()
            val removedItem = currentItems.find { it.id == itemId }
            currentItems.removeAll { it.id == itemId }

            removedItem?.let {
                _snackbarMessage.value = "${it.name} removed from cart"
                _showSnackbar.value = true
            }

            _cartItems.value = currentItems
            _itemCount.value = calculateTotalItems(currentItems)
            cartRepository.saveCartItems(currentItems)
        }
    }

    fun updatePersons(itemId: String, newPersons: Int) {
        viewModelScope.launch {
            if (newPersons < 1) {
                removeFromCart(itemId)
            } else {
                // Update persons logic
                val currentItems = _cartItems.value.toMutableList()
                val itemIndex = currentItems.indexOfFirst { it.id == itemId }

                if (itemIndex != -1) {
                    currentItems[itemIndex] = currentItems[itemIndex].copy(persons = newPersons)
                    _cartItems.value = currentItems
                    cartRepository.saveCartItems(currentItems)
                }
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartItems.value = emptyList()
            _itemCount.value = 0
            _snackbarMessage.value = "Cart cleared"
            _showSnackbar.value = true
            cartRepository.saveCartItems(emptyList())
        }
    }

    fun resetSnackbar() {
        _showSnackbar.value = false
    }

    fun isItemInCart(id: String): Boolean {
        return _cartItems.value.any { it.productId == id }
    }
}