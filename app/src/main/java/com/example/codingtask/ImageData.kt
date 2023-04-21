package com.example.codingtask

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData(
        var bitmap: Bitmap? = null,
): Parcelable