package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ElephantWranglingService {

    //day 7 both parts - not terribly efficient - perhaps terribly inefficient - but like always - it works?

    fun getValidInputCounts(file:MultipartFile, day2: Boolean): Long{
        return getInputs(file, day2)
    }

    fun getInputs(file: MultipartFile, day2: Boolean): Long{
        var operandSet = listOf('+','*')
        if(day2){
            operandSet = listOf('+','*','|')
        }
        val originalData = processFile(file)?.stream()?.toList()
        var count = 0L
        if (originalData != null) {
            for(value in originalData){
                val split = value.split(":")
                val testValue = split[0].toLong()
                val testInputs = split[1].trim().split(" ")
                val operandsSize = testInputs.size-1
                val operandCombinations =  generateOperandStrings(operandSet, operandsSize)

                for(combination in operandCombinations){
                    var index=1
                    var runningResult = testInputs[0].toLong()
                    for(operand in combination){
                        if(operand == '|'){
                            val combineBar = runningResult.toString()+testInputs[index]
                            runningResult=combineBar.toLong()
                        }else{
                            if(operand == '*'){
                                runningResult *=testInputs[index].toLong()
                            }else{
                                runningResult += testInputs[index].toLong()
                            }
                        }
                        index++
                    }
                    if(runningResult==testValue){
                        count+=(runningResult)
                        break
                    }
                }
            }
        }
        //
        return count
    }

    fun generateOperandStrings(elements: List<Char>, length: Int): List<String> {
        if (length == 0) {
            return listOf("")
        }

        val result = mutableListOf<String>()
        for (element in elements) {
            for (string in generateOperandStrings(elements, length - 1)) {
                result.add(element + string)
            }
        }
        return result
    }
}