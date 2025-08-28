package com.beauty.parler.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppliedCoupon(
    val code: String,
    val discountPercentage: Int,
    val title: String? = null,
    val description: String? = null,
    val formattedDate: String? = null
) : Parcelable