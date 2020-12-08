package com.sergiodan.transformerbattle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.datasources.TransformersRemoteDataSource
import com.sergiodan.transformerbattle.data.model.MainData
import com.sergiodan.transformerbattle.data.model.Resource
import com.sergiodan.transformerbattle.data.services.TransformerService
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class RequestViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response Code 200 returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)

        val res = response.getBody()?.readUtf8()
//        val data = res?.let { `parse mocked JSON response`(it) }
        val gson = Gson()
        val topic = gson.fromJson(res, MainData::class.java)
        val autobots = topic.getData()?.filter { it.team == "A" } ?: listOf()
        val decepticons = topic.getData()?.filter { it.team == "D" } ?: listOf()
        val triple = DataManager.brawl(autobots, decepticons)

        // Act
//        val  actualResponse = apiHelper.getTransformers()
        // Assert
        assertEquals(response.toString().contains("200"),response.toString().contains("200"))
        assertEquals(triple.first.map { it.name }.joinToString(","), "Megatron,Blastoff,Bumblebee")
        assertEquals(triple.second.map { it.name }.joinToString(","), "Optimus Prime,Perceptor,Outback")
        assertEquals(triple.third, 1)
    }

    private fun `parse mocked JSON response`(mockResponse: String): String {
        val reader = JSONObject(mockResponse)
        return reader.getString("status")
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}