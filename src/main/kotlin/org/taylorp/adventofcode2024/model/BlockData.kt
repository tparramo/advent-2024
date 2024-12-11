package org.taylorp.adventofcode2024.model

import org.taylorp.adventofcode2024.service.processFile

data class BlockData(
    val index: Int,
    val length: Int,
    var space: Int
)
