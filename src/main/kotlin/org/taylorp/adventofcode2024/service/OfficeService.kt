package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.model.OccurrenceModel
import kotlin.math.abs


@Service
class OfficeService {

    //day 1 part 1

    fun calculateSumOfDiffs( file: MultipartFile): Int{
        val originalData = processFile(file)
        val regex = "[0-9]+".toRegex()
        val splitData = mutableListOf<Pair<Int, Int>>()
        originalData?.stream()?.forEach { value ->
            if (value != null && value.isNotEmpty()){
                val matchResult = regex.findAll(value.trim())
                val first = matchResult?.first()?.value?.toInt() ?: 0
                val second = matchResult?.last()?.value?.toInt() ?: 0
                splitData.add(Pair(first, second))
            }
        }
        return sumDiffData(splitData)
    }

    fun sumDiffData(splitData: MutableList<Pair<Int, Int>>): Int{
        var sum = 0
        var firstListSorted = mutableListOf<Int> ()
        var secondListSorted = mutableListOf<Int> ()
        splitData.stream().forEach { pair ->
            firstListSorted.add(pair.first)
            secondListSorted.add(pair.second)
        }
        firstListSorted.sort()
        secondListSorted.sort()
        firstListSorted.forEachIndexed { index, element ->
            sum += abs(element - secondListSorted[index])
        }
        return sum;
    }

    //day 1 part 2

    fun calculateOccurrenceDiff( file: MultipartFile): Int{
        val originalData = processFile(file)
        val regex = "[0-9]+".toRegex()
        val firstList = mutableListOf<Int>()
        val occurrenceMap = mutableMapOf<Int, OccurrenceModel>()
        originalData?.stream()?.forEach { value ->
            if (value != null && value.isNotEmpty()){
                val matchResult = regex.findAll(value.trim())
                val first = matchResult?.first()?.value?.toInt() ?: 0
                val second = matchResult?.last()?.value?.toInt() ?: 0
                firstList.add(first)
                if(occurrenceMap.containsKey(second)){
                    occurrenceMap[second]?.increment()
                }else{
                    occurrenceMap[second] = OccurrenceModel(second)
                }
            }
        }
        return sumOccurrence(firstList, occurrenceMap)
    }

    fun sumOccurrence(firstList: MutableList<Int>, dataOccurrenceMap: MutableMap<Int, OccurrenceModel>): Int{
        var sum = 0
        for(item in firstList){
            if(dataOccurrenceMap.containsKey(item)){
                val occurrenceMultiplied = item * (dataOccurrenceMap[item]?.occurrences?:0)
                sum += occurrenceMultiplied
            }
        }
        return sum
    }

}
