package com.julian.taipeitour.domain


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("data")
    val data: List<NewsData>,
    @SerializedName("total")
    val total: Int
) {
    data class NewsData(
        @SerializedName("begin")
        val begin: Any,
        @SerializedName("description")
        val description: String,
        @SerializedName("end")
        val end: Any,
        @SerializedName("files")
        val files: List<Any>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("links")
        val links: List<Link>,
        @SerializedName("modified")
        val modified: String,
        @SerializedName("posted")
        val posted: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
        //判斷是否展開
        var isExpend: Boolean = false
    ) {
        data class Link(
            @SerializedName("src")
            val src: String,
            @SerializedName("subject")
            val subject: String
        )
    }
}