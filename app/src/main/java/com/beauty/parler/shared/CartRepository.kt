package com.beauty.parler.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.beauty.parler.model.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart_prefs")

class CartRepository(private val context: Context) {
    private val gson = Gson()
    private val CART_ITEMS_KEY = stringPreferencesKey("cart_items")

    suspend fun saveCartItems(items: List<CartItem>) {
        context.dataStore.edit { preferences ->
            preferences[CART_ITEMS_KEY] = gson.toJson(items)
        }
    }

    suspend fun updatePersons(itemId: String, newPersons: Int) {
        // update persons  data store
    }

    fun getCartItems(): Flow<List<CartItem>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[CART_ITEMS_KEY] ?: return@map emptyList()
            try {
                val type = object : TypeToken<List<CartItem>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}