package org.taylorp.adventofcode2024.service

import org.apache.el.parser.Token
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.ceil
import kotlin.math.floor

@Service
class PebbleService {

    val blinksToDataToStonesMap = mutableMapOf<Int, MutableMap<String, Long>>()

    fun blinkAndCount(file: MultipartFile, blinks: Int): Long {
        val originalData = processFile(file)?.stream()?.toList()
        val currentStones = mutableListOf<String>()
        originalData?.forEach{currentStones.addAll(it.split(" "))}
        val shiftingPebbles = mutableListOf<String>()
        for(i in 0 until blinks){
            var currentStonesIndex = 0
            while(currentStonesIndex < currentStones.size){
                val current = currentStones[currentStonesIndex]
                if(current=="0"){
                    shiftingPebbles.add("1")
                } else if(current.length%2==0){
                    val length = current.length/2
                    shiftingPebbles.add(current.substring(0, length))
                    shiftingPebbles.add(
                        current.substring(length).toLong().toString()
                    )//strip leading zeros wth toInt

                }else{
                    shiftingPebbles.add(""+current.toLong()*2024)
                }
                currentStonesIndex++
            }
            currentStones.clear()
            currentStones.addAll(shiftingPebbles)
            shiftingPebbles.clear()
        }

        return currentStones.size.toLong()
    }

    fun blinkAndCount2(file: MultipartFile, blinks: Int): Long {
        val originalData = processFile(file)?.stream()?.toList()
        val currentStones = mutableListOf<String>()
        originalData?.forEach{currentStones.addAll(it.split(" "))}
        var count = 0L
        for(i in currentStones){
            count+=blinkItemsForOneValue(i, blinks)
            println(" $i resulted in new count $count")
        }

        return count
    }

    fun blinkItemsForOneValue(item: String, blinks: Int): Long {
        var count = 0L
        if (blinks == 0) {
            return 1
        }
        val existingDataForBlinks = blinksToDataToStonesMap[blinks]?.get(item)
        if (existingDataForBlinks != null) {
            count += existingDataForBlinks
        } else {
            if (item == "0") {
                val output = blinkItemsForOneValue("1", blinks - 1)
                blinksToDataToStonesMap.computeIfAbsent(blinks, { mutableMapOf() })[item] = output
                count += output
            } else if (item.length % 2 == 0) {
                val length = item.length / 2
                val value = item.substring(0, length)
                val value2 = item.substring(length).toLong().toString()
                val output1 = blinkItemsForOneValue(value, blinks - 1)
                val output2 = blinkItemsForOneValue(
                    value2,
                    blinks - 1
                )//strip leading zeros wth toInt
                blinksToDataToStonesMap.computeIfAbsent(blinks, { mutableMapOf() })[item] = output1+output2
                count += output1
                count += output2

            } else {
                val output = blinkItemsForOneValue("" + item.toLong() * 2024, blinks - 1)
                blinksToDataToStonesMap.computeIfAbsent(blinks, { mutableMapOf() })[item] = output
                count += output
            }
        }
        return count
    }
}

