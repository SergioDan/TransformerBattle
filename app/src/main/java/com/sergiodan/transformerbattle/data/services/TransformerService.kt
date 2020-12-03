package com.sergiodan.transformerbattle.data.services

import retrofit2.http.*

/**
 * Models the Tranformers API.
 *
 * docs: https://transformers-api.firebaseapp.com/api-docs/
 */
interface TransformerService {

    @POST("/transformers")
    suspend fun createTransformer(@Body transformerParameters: Map<String, @JvmSuppressWildcards Any>)

    @GET("/transformers")
    suspend fun getTransformers()

    @PUT("/transformers")
    suspend fun updateTransformer(@Body transformerParameters: Map<String, @JvmSuppressWildcards Any>)

    @DELETE("/transformers/{transformerId}")
    suspend fun deleteTransformer(@Path("transformerId") transformerId: String)

    @GET("/allspark")
    suspend fun requestToken()

    companion object {
        const val END_POINT = "https://transformers-api.firebaseapp.com"
    }
}