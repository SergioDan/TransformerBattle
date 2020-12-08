package com.sergiodan.transformerbattle.data.datasources

import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.TransformersList
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.data.services.TransformerService
import retrofit2.Response
import java.io.IOException

class TransformersRemoteDataSource(private val service: TransformerService) {

    private fun formatHeader(token: String): String {
        return "Bearer $token"
    }

    suspend fun retrieveToken(): Result<String> {
        return try {
            val response = service.requestToken()

            getResultString(response, onError = {
                Result.Error(
                    IOException("Error retrieving token ${response.code()} ${response.message()}")
                )
            })

        } catch (exception: Exception) {
            Result.Error(
                IOException("Error retrieving token ${exception.localizedMessage}")
            )
        }
    }

    suspend fun getTransformers(authorizationToken: String, transformerId: String? = null): Result<List<Transformer>> {
        return try {
            val response = transformerId?.let {
                service.getTransformer(formatHeader(authorizationToken), it)
            } ?: run {
                service.getTransformers(formatHeader(authorizationToken))
            }
            // use the name of the argument to avoid a anonymous callback
            getResultList(response, onError = {
                Result.Error(
                    IOException("Error getting the stored transformers ${response.code()} ${response.message()}")
                )
            })
        } catch (exception: Exception) {
            Result.Error(
                IOException("Error getting the stored transformers", exception)
            )
        }
    }

    suspend fun createTransformer(authorizationToken: String, transformer: Transformer): Result<Transformer> {
        return try {
            val response = service.createTransformer(formatHeader(authorizationToken), transformer.toMap())
            getResultOne(response, onError = {
                Result.Error(
                    IOException("Error creating the transformer ${response.code()} ${response.message()}")
                )
            })
        } catch (exception: Exception) {
            Result.Error(
                IOException("Error creating the data", exception)
            )
        }
    }

    suspend fun deleteTransformer(authorizationToken: String, transformerId: String): Result<Boolean> {
        return try {
            service.deleteTransformer(formatHeader(authorizationToken), transformerId)
            return Result.Success(true)
        } catch (exception: Exception) {
            Result.Error(
                IOException("Error creating the transformer", exception)
            )
        }
    }

    suspend fun updateTransformer(authorizationToken: String, transformer: Transformer): Result<Transformer> {
        return try {
            val response = service.updateTransformer(formatHeader(authorizationToken), transformer.toMap())
            getResultOne(response, onError = {
                Result.Error(
                    IOException("Error updating the transformer ${response.code()} ${response.message()}")
                )
            })
        } catch (exception: Exception) {
            Result.Error(
                IOException("Error updating the data", exception)
            )
        }
    }

    private inline fun getResultString(
        response: Response<String>,
        onError: () -> Result.Error
    ): Result<String> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }


    private inline fun getResultList(
        response: Response<TransformersList>,
        onError: () -> Result.Error
    ): Result<List<Transformer>> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body.transformers)
            }
        }
        return onError.invoke()
    }

    private inline fun getResultOne(
        response: Response<Transformer>,
        onError: () -> Result.Error
    ): Result<Transformer> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }

    private inline fun getResultBoolean(
        response: Response<Boolean>,
        onError: () -> Result.Error
    ): Result<Boolean> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }

}