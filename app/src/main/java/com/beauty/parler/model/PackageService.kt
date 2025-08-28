package com.beauty.parler.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class PackageService(
    val name: String,
    val price: Double,
    val duration: Int,
    val imageUrl: String
) : Parcelable