package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.model.OrderingPrintModel

@Service
class SubBasement17Service {


    //TODO come back to this and refactor/simplify ...
    //but it WORKS
    //day 5 part 1

    fun getValidMiddles(file: MultipartFile, useGoodMean: Boolean): Int{
        val originalData = processFile(file)?.stream()
        val orderingList = mutableMapOf<Int,OrderingPrintModel>()
        val printingList = mutableMapOf<Boolean, MutableList<String>>()
        printingList[true] = mutableListOf()
        printingList[false] = mutableListOf()
        var wasEmpty = false
        var goodMeanSum = 0
        var badMeanSum = 0
        if (originalData != null) {
            for(item in originalData) {
                if(!wasEmpty) {
                    if (item != "" && item != "\\n") {
                        val splitGroups = item.trim().split("|")
                        val page = splitGroups[0].toInt()
                        val comesBefore = splitGroups[1].toInt()
                        if (orderingList.containsKey(page)) {
                            orderingList[page]?.orderedBefore?.add(comesBefore)
                        } else {
                            val orderingPrintModel = OrderingPrintModel(page)
                            orderingPrintModel.orderedBefore.add(comesBefore)
                            orderingList[page] = orderingPrintModel
                        }
                    }else{
                        wasEmpty = true
                    }
                }//finished separating ordering part - on to print part
                if (wasEmpty && item.isNotEmpty()) {
                    val splitValues = item.split(",");
                    var valid = true
                    var priorValue = 0
                    for (value in splitValues) {
                        if (orderingList[value.toInt()]?.orderedBefore?.contains(
                                priorValue
                            ) == true
                        ) {
                            printingList[false]?.add(item)
                            valid = false
                            break;
                        }
                        priorValue = value.toInt()
                    }
                    if (valid) {
                        printingList[true]?.add(item)
                        goodMeanSum += splitValues[splitValues.size / 2].toInt()
                    }

                }
            }
        }

        for(item in printingList[false]!!){
            val ordering = mutableListOf<OrderingPrintModel>()
            val list=item.trim().split(",")
            for(value in list) {
                if(orderingList.containsKey(value.toInt()) && orderingList[value.toInt()]!=null){
                    orderingList[value.toInt()]?.let { ordering.add(it) }
                }else{
                    orderingList[value.toInt()] = OrderingPrintModel(value.toInt())
                }
            }
            ordering.sort()
            badMeanSum += ordering[ordering.size/2].pageNumber
        }

        if(useGoodMean){
            return goodMeanSum
        }else{
            return badMeanSum
        }

    }
}