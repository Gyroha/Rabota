package com.example.pm3

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

class Jobs {
    @SerializedName("uuid")
    @Expose
    var uuid: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("normalized_job_tittle")
    @Expose
    var normalized_job_tittle: String? = null
    @SerializedName("parent_uuid")
    @Expose
    var parent_uuid: String? = null
}

interface API{
    @GET("jobs")
    fun jobs(): Call<List<Jobs>>
}