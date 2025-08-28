package com.beauty.parler.shared

import android.content.Context
import com.beauty.parler.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("CART_PREFERENCES", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addToCart(cartItem: CartItem) {
        val cartItems = getCartItems().toMutableList()

        // Check if item already exists
        val existingItemIndex = cartItems.indexOfFirst { it.id == cartItem.id }
        if (existingItemIndex != -1) {
            // Update quantity if item exists
            val existingItem = cartItems[existingItemIndex]
            cartItems[existingItemIndex] = existingItem.copy(quantity = existingItem.quantity + cartItem.quantity)
        } else {
            // Add new item
            cartItems.add(cartItem)
        }

        saveCartItems(cartItems)
    }

    fun updateCartItem(updatedItem: CartItem) {
        val cartItems = getCartItems().toMutableList()
        val index = cartItems.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            cartItems[index] = updatedItem
            saveCartItems(cartItems)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        val cartItems = getCartItems().toMutableList()
        cartItems.removeAll { it.id == cartItem.id }
        saveCartItems(cartItems)
    }

    fun getCartItems(): List<CartItem> {
        val json = sharedPreferences.getString("CART_ITEMS", null)
        return if (json != null) {
            val type = object : TypeToken<List<CartItem>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun clearCart() {
        sharedPreferences.edit().remove("CART_ITEMS").apply()
    }

    private fun saveCartItems(items: List<CartItem>) {
        val json = gson.toJson(items)
        sharedPreferences.edit().putString("CART_ITEMS", json).apply()
    }
}