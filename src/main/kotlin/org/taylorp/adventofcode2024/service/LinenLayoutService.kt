package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.stream.Collectors.toList

@Service
class LinenLayoutService {

    //day 19 both parts
    val patternPossibility = mutableMapOf<String, Long>()

    fun getValidCombinations(file:MultipartFile, day2: Boolean): Long{
        return getInputs(file, day2)
    }

    fun getInputs(file: MultipartFile, day2: Boolean): Long{

        val originalData = processFile(file)?.stream()?.toList()
        var count = 0L
        if (originalData != null) {
            val availableTowels = originalData[0].split(", ").toList()
            for(index in 1 until originalData.size){
                if(originalData[index].isNullOrEmpty()){
                    continue
                }
                val value = originalData[index]
                val combinations = processCombinations(value, availableTowels)
                if(combinations>0L) {
                    if (!day2) {
                        count++
                    } else {
                        count+=combinations
                    }
                }
            }
        }
        return count
    }

    fun processCombinations(line: String, availableTowels: List<String>):Long{
        val cached = patternPossibility[line]
        if(cached != null){
            return cached
        }
        var combinations = 0L
        for(pattern in availableTowels){
            if(line==pattern){
                combinations++
            }
            else if(line.startsWith(pattern)){
                combinations+=processCombinations(line.substring(pattern.length), availableTowels)
            }
        }

        patternPossibility[line] = combinations
        return combinations
    }
}