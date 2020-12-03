package com.sergiodan.transformerbattle.data.services

import com.sergiodan.transformerbattle.data.model.Transformer
import retrofit2.Response
import retrofit2.http.*

/**
 * Models the Tranformers API.
 *
 * docs: https://transformers-api.firebaseapp.com/api-docs/
 */
interface TransformerService {

    @POST("/transformers")
    suspend fun createTransformer(@Body transformerParameters: Map<String, @JvmSuppressWildcards Any>): Response<Transformer>

    @GET("/transformers")
    suspend fun getTransformers(): Response<List<Transformer>>

    @GET("/transformers/{transformerId}")
    suspend fun getTransformer(@Path("transformerId") transformerId: String): Response<List<Transformer>>

    @PUT("/transformers")
    suspend fun updateTransformer(@Body transformerParameters: Map<String, @JvmSuppressWildcards Any>): Response<Transformer>

    @DELETE("/transformers/{transformerId}")
    suspend fun deleteTransformer(@Path("transformerId") transformerId: String)

    @GET("/allspark")
    suspend fun requestToken(): Response<String>

    companion object {
        const val END_POINT = "https://transformers-api.firebaseapp.com"
    }
}