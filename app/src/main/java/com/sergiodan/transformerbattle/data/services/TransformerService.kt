package com.sergiodan.transformerbattle.data.services

import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.TransformersList
import retrofit2.Response
import retrofit2.http.*

/**
 * Models the Tranformers API.
 *
 * docs: https://transformers-api.firebaseapp.com/api-docs/
 */
interface TransformerService {

    @POST("/transformers")
    suspend fun createTransformer(
        @Header("Authorization") authorizationToken: String,
        @Body transformerParameters: Map<String, @JvmSuppressWildcards Any?>): Response<Transformer>

    @GET("/transformers")
    suspend fun getTransformers(@Header("Authorization") authorizationToken: String): Response<TransformersList>

    @GET("/transformers/{transformerId}")
    suspend fun getTransformer(@Header("Authorization") authorizationToken: String,
                               @Path("transformerId") transformerId: String): Response<TransformersList>

    @PUT("/transformers")
    suspend fun updateTransformer(@Header("Authorization") authorizationToken: String,
                                  @Body transformerParameters: Map<String, @JvmSuppressWildcards Any?>): Response<Transformer>

    @DELETE("/transformers/{transformerId}")
    suspend fun deleteTransformer(@Header("Authorization") authorizationToken: String,
        @Path("transformerId") transformerId: String):Response<String>

    @GET("/allspark")
    suspend fun requestToken(): Response<String>

    companion object {
        const val END_POINT = "https://transformers-api.firebaseapp.com"
    }
}