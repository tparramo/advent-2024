package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.model.TestInputs


@Service
class ElephantWranglingService {

    //day 7 part 1 - not terribly efficient - perhaps terribly inefficient - but like always - it works?

    val finalOperandSets = mutableMapOf<Int, MutableList<String>>()

    fun getValidInputCounts(file:MultipartFile): Long{
        val inputSet = getInputs(file)
        var count = 0L
        for(input in inputSet){
            if(input.isValid.filter { it }.isNotEmpty()){
               count += input.testValue
            }
        }
        return count
    }

    fun getInputs(file: MultipartFile): MutableList<TestInputs>{
        val originalData = processFile(file)?.stream()?.toList()
        val testInputsList = mutableListOf<TestInputs>()
        if (originalData != null) {
            for(value in originalData){
                val split = value.split(":")
                val testValue = split[0]
                val testInputModel = TestInputs(testValue.toLong(), split[1].trim())
                testInputsList.add(testInputModel)
            }
        }
        constructCombinationInputString(testInputsList)
        evaluateValidity(testInputsList)
        //
        return testInputsList
    }

    fun constructCombinationInputString(testInputsList: MutableList<TestInputs>){
        for(testInputs in testInputsList){
            val originalInput = testInputs.inputs.split(" ").size-1
            if(finalOperandSets.containsKey(originalInput)){
                testInputs.operandPermutations = finalOperandSets[originalInput]!!
            }else {
                testInputs.operandPermutations = getListPermutations(originalInput)
                finalOperandSets[originalInput] = testInputs.operandPermutations
            }
        }
    }

    fun permutations(str: String): MutableList<String> {
        if (str.isEmpty()) {
            return mutableListOf("")
        }

        val result = mutableListOf<String>()
        for (i in str.indices) {
            val char = str[i]
            val remaining = str.removeRange(i, i + 1)
            for (perm in permutations(remaining)) {
                if(!result.contains(char+perm)) {
                    result.add(char + perm)
                }
            }
        }
        return result
    }

    fun getPermutations(list: MutableList<String>, finalList: MutableList<String>){
        finalList.add(list[0])
        finalList.add(list[list.size-1])
        for(i in 1 until list.size-1){//skip first and last
//            getPermutation(StringBuilder(list[i]),0, finalList)
            finalList.addAll(permutations(list[i]))
        }
    }

//    fun getPermutation(s: StringBuilder, idx: Int, finalList:MutableList<String>){
//
//        // Base case
//        if (idx == s.length - 1) {
//            if(!finalList.contains(s.toString())) {
//                finalList.add(s.toString())
//            }
//        }
//        for (i in idx until s.length) {
//
//            // Swapping
//            swap(s, idx, i)
//
//            // First idx+1 characters fixed
//            getPermutation(s, idx + 1, finalList)
//
//            // Backtrack
//            swap(s, idx, i)
//        }
//    }

    // Helper function to swap characters in the StringBuilder
    fun swap(s: StringBuilder, i: Int, j: Int) {
        val temp = s[i]
        s.setCharAt(i, s[j])
        s.setCharAt(j, temp)
    }


    fun getListPermutations (size: Int):MutableList<String>{
        var startString = ""
        val lists = mutableListOf<String>()
        for(i in 0 until size){
            startString+="+"
        }
        lists.add(startString)
        for(j in 0 until size){
            startString = startString.replaceRange(IntRange(j, j),"*")
            lists.add(startString)
        }

        val finalList = mutableListOf<String>()
        //skip first and last string in list
        getPermutations(lists, finalList)

        return finalList
    }


    fun evaluateValidity(testInputsList: MutableList<TestInputs>){
        for(item in testInputsList){
            for(i in 0 until item.operandPermutations.size){
                item.isValid.add(evaluate(item.inputs,item.operandPermutations[i])==item.testValue)
            }
        }
    }

    fun evaluate(string: String, operandList: String):Long{
        val values = mutableListOf<Int>()
        val regex = "[0-9]+".toRegex()
        regex.findAll(string).asIterable().forEach { match ->
            values.add((match.value as String).toInt())
        }

        var count = 0L
        for(index in 0 until values.size-1){
            if(count == 0L){
                if(operandList[index]=='*') {
                    count += (values[index] * values[index + 1])
                }else{
                    count+= (values[index]+values[index+1])
                }
                continue
            }
            if(operandList[index]=='+'){
                count += values[index + 1]
            }else{
                count *= values[index + 1]
            }
        }
        return count
    }
}