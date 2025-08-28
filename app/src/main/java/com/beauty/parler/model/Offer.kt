package com.beauty.parler.model

data class Offer(
    val id: String,
    val title: String,
    val name: String,
    val imageUrl:String,
    val description: String,
    val discountPercentage: Int,
    val formattedDate: String,
    val useCode: String,
    val originalPrice: Int?,
    val discountedPrice: Int?,
    val usedCount: Int,
    val remainingCount: Int
)