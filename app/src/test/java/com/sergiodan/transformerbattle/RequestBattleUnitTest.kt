package com.sergiodan.transformerbattle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.sergiodan.transformerbattle.data.AUTOBOT_TEAM_IDENTIFIER
import com.sergiodan.transformerbattle.data.DECEPTICON_TEAM_IDENTIFIER
import com.sergiodan.transformerbattle.data.DataManager
import com.sergiodan.transformerbattle.data.model.BrawlResult
import com.sergiodan.transformerbattle.data.model.MainData
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class RequestBattleUnitTest {

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
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch transformers and check result of battle`() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)

        val res = response.getBody()?.readUtf8()
        val gson = Gson()
        val topic = gson.fromJson(res, MainData::class.java)
        val autobots = topic.getData()?.filter { it.team == AUTOBOT_TEAM_IDENTIFIER } ?: listOf()
        val decepticons = topic.getData()?.filter { it.team == DECEPTICON_TEAM_IDENTIFIER } ?: listOf()
        val brawlResult: BrawlResult = DataManager.brawl(autobots, decepticons)

        assertEquals(response.toString().contains("200"),response.toString().contains("200"))
        assertEquals(brawlResult.allDefeated.map { it.name }.joinToString(","), "Megatron,Blastoff,Bumblebee")
        assertEquals(brawlResult.winning.map { it.name }.joinToString(","), "Optimus Prime,Perceptor,Outback")
        assertEquals(brawlResult.winningTeamId, AUTOBOT_TEAM_IDENTIFIER)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}