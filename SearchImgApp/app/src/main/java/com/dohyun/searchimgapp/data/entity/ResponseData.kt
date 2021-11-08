package com.dohyun.searchimgapp.data.entity

import com.google.gson.annotations.SerializedName

data class ResponseData (
    @SerializedName("meta")
    val metaData: MetaData,
    @SerializedName("documents")
    val documents: MutableList<ImageInfo>
)