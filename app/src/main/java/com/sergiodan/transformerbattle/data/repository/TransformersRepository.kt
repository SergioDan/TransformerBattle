package com.sergiodan.transformerbattle.data.repository

import com.sergiodan.transformerbattle.data.datasources.TransformersRemoteDataSource
import com.sergiodan.transformerbattle.data.model.Result
import com.sergiodan.transformerbattle.data.model.Transformer

class TransformersRepository(private val dataSource: TransformersRemoteDataSource) {

    suspend fun getTransformers(transformerId: String? = null): Result<List<Transformer>> =
        dataSource.getTransformers(transformerId)

    suspend fun createTransformer(transformer: Transformer): Result<Transformer> =
        dataSource.createTransformer(transformer)
}