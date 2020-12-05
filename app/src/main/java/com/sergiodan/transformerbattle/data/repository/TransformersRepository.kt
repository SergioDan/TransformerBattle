package com.sergiodan.transformerbattle.data.repository

import com.sergiodan.transformerbattle.data.datasources.TransformersRemoteDataSource
import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer

class TransformersRepository(private val dataSource: TransformersRemoteDataSource) {

    suspend fun getTransformers(authorizationToken: String, transformerId: String? = null): Result<List<Transformer>> =
        dataSource.getTransformers(authorizationToken, transformerId)

    suspend fun createTransformer(authorizationToken: String, transformer: Transformer): Result<Transformer> =
        dataSource.createTransformer(authorizationToken, transformer)

    suspend fun retrieveToken(): Result<String> =
        dataSource.retrieveToken()
}