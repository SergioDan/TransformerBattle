package com.sergiodan.transformerbattle.data.model

data class BrawlResult(val allDefeated: List<Transformer>,
                       val survivorsLosingTeam: List<Transformer>,
                    val winning: List<Transformer>,
                    val winningTeamId: String)