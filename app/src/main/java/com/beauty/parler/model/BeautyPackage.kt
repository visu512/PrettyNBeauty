package com.beauty.parler.model

data class BeautyPackage(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val servicesIncluded: List<String>,
    val serviceImageUrls: List<String>,
    val price: Double,
    val rating: Float,
//    val reviews: Int,
    val servicesDetails: List<Service>,
    val servicePrices: List<Double>,
    val durations: String
)





