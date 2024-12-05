package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.abs


@Service
class RudolfNuclearPlantService {

    //day 2 part 1

    fun findSafeReports( file: MultipartFile): Int{
        val splitData = getReports(file)
        return calculateSafeReports(splitData)
    }

    fun getReports(file: MultipartFile): MutableList<MutableList<Int>>{
        val originalData = processFile(file)
        val regex = "[0-9]+".toRegex()
        val splitData = mutableListOf<MutableList<Int>>()
        originalData?.stream()?.forEach { value ->
            if (value != null && value.isNotEmpty()){
                val numSetForLine = mutableListOf<Int>()
                val matchResult = regex.findAll(value.trim())
                matchResult.asIterable().forEach { match ->
                    numSetForLine.add((match.value as String).toInt())
                }
                splitData.add(numSetForLine)
            }
        }
        return splitData
    }

    fun calculateSafeReports(splitData: MutableList<MutableList<Int>>): Int{
        var safeCount = 0
        for(dataSet in splitData){
            if(isSafe(dataSet)) {
                safeCount++
            } //made it through all checks - add report to safe count
        }

        return safeCount
    }

    fun getDiffState(diff: Int):String{
        if(diff>0){
            return POSITIVE
        }else if(diff<0){
            return NEGATIVE
        }else{
            return EQUAL
        }
    }

    //day 2 part 2

    fun findTemperedSafeReports( file: MultipartFile): Int{
        val splitData = getReports(file)
        return calculateTemperedSafeReports(splitData)
    }

    //Perhaps not efficient but checks initial order for safety. If not safe initially, checks all combinations of removing one element to find if there are any safe combinations.
    fun calculateTemperedSafeReports(splitData: MutableList<MutableList<Int>>): Int{
        var safeCount = 0
        for(dataSet in splitData){
            var safeSet = mutableListOf<Boolean>()
            val isSafeToStart = isSafe(dataSet)
            safeSet.add(isSafeToStart)
            if(isSafeToStart){//safety on entire set - quick exit
                safeCount++
                continue
            }
            for(index in 0 until dataSet.size){
                val newList = dataSet.toMutableList()
                newList.removeAt(index)
                safeSet.add(isSafe(newList))
            }
            if(safeSet.filter { value -> value }.isNotEmpty()){
                //more than one unsafe combination
                safeCount++
            }
        }

        return safeCount
    }

    fun isSafe(dataSet: MutableList<Int>): Boolean{
        var firstDiffState: String? = null
        var safe = true;
        for(index in 0 until dataSet.size-1){
            val diff = dataSet[index] - dataSet[index + 1]
            val diffState = getDiffState(diff)
            if(firstDiffState==null){//set initial diff
                firstDiffState = diffState
            }
            if(diffState==EQUAL || diffState!=firstDiffState || abs(diff)>3 ){
                safe = false
                break
            }
        }
        return safe
    }



}