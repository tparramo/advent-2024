package org.taylorp.adventofcode2024.model

data class TestInputs(
    val testValue: Long,
    val inputs: String,
    var isValid: MutableList<Boolean> = mutableListOf(),
    var operandPermutations: MutableList<String> = mutableListOf()
)
