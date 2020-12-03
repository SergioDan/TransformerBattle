package com.sergiodan.transformerbattle.data.datasources

import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer
import com.sergiodan.transformerbattle.data.model.toMap
import com.sergiodan.transformerbattle.data.services.TransformerService
import retrofit2.Response
import java.io.IOException

class TransformersRemoteDataSource(private val service: TransformerService) {

    suspend fun getTransformers(transformerId: String? = null): Result<List<Transformer>> {
        return try {
            val response = transformerId?.let {
                service.getTransformer(it)
            } ?: run {
                service.getTransformers()
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

    suspend fun createTransformer(transformer: Transformer): Result<Transformer> {
        return try {
            val response = service.createTransformer(transformer.toMap())
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


    private inline fun getResultList(
        response: Response<List<Transformer>>,
        onError: () -> Result.Error
    ): Result<List<Transformer>> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
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
}