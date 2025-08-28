package com.beauty.parler.model

data class Service(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val duration: Int,
    val rating: Float,
//    val reviews: Int,
    val imageUrl: String,
    val categoryId:String,
    val categoryName:String
)