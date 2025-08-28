package com.beauty.parler.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val id: String? = null,
    val productId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    var quantity: Int,
    val imageUrl: String = "",
    val duration: String = "",
    val dateTime: String? = null,
    val originalPrice: Double? = null,
    val isPackage: Boolean = false,
    val packageServices: List<String> = emptyList(),
    val selectedServices: String = "",
    var persons: Int = 1, //  persons
    val serviceDuration: Int = 0, //  duration in minutes


) : Parcelable





