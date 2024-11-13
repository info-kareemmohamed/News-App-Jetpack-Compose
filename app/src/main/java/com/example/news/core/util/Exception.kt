package com.example.news.core.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.ByteArrayOutputStream


fun Uri.toBytArray(context: Context) =
    context.contentResolver.openInputStream(this)?.use { it.buffered().readBytes() }

fun Bitmap.toBytArray(context: Context) =
    this.compress(Bitmap.CompressFormat.PNG, 100, ByteArrayOutputStream())

