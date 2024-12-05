package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.abs


@Service
class TobogganShopService {

    //day 3 part 1

    fun processMultipications(file: MultipartFile): Int{
        val originalData = processFile(file)
        val regex = "mul\\(([0-9]+),([0-9]+)\\)".toRegex()
        var finalData = 0
        originalData?.stream()?.forEach { value ->
            if (value != null && value.isNotEmpty()){
                val matchResult = regex.findAll(value.trim())
                for (result in matchResult) {//group 0 is the whole match, group 1 and 2 are the numbers
                    finalData += ((result.groups[1]?.value?.toInt()?:0).times(result.groups[2]?.value?.toInt()?:0))
                }
            }
        }
        return finalData
    }

    //day 3 part 2

    fun processMultipicationsDosAndDonts(file: MultipartFile): Int{
        val originalData = processFile(file)
        val regex = "(do\\(\\))|(don't\\(\\))|mul\\(([0-9]+),([0-9]+)\\)".toRegex()//creates 5 groups in matches
        var finalData = 0
        var doIt = true
        originalData?.stream()?.forEach { value ->
            if (value != null && value.isNotEmpty()){
                val matchResult = regex.findAll(value.trim())
                for (result in matchResult) {
                    val firstGroupVal = result.groups[0]?.value?:""
                    if(firstGroupVal.contains("don't(")){
                        doIt = false
                        continue
                    }
                    if(firstGroupVal.contains("do(")){
                        doIt = true
                        continue
                    }
                    if(!doIt){
                        continue
                    }
                    finalData += ((result.groups[3]?.value?.toInt()?:0).times(result.groups[4]?.value?.toInt()?:0))
                }
            }
        }
        return finalData
    }
}