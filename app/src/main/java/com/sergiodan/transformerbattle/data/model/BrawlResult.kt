package com.sergiodan.transformerbattle.data.model

data class BrawlResult(val defeated: List<Transformer>,
                    val winning: List<Transformer>,
                    val winningTeamId: String)