package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class TimeTravelService {

    val LEFT = "LEFT"
    val UP = "UP"
    val RIGHT = "RIGHT"
    val DOWN = "DOWN"
    val OBSTACLE = '#'

    fun trackRouteGuardLocations(file: MultipartFile): Int{
        val originalData = processFile(file)?.stream()?.toList()
        val hitsList = routeMapAndProcessing(originalData)
        return hitsList.size
    }

    fun routeMapAndProcessing(originalData: MutableList<String>?): MutableMap<String,String>{
        val hitsList = mutableMapOf<String, String>()
        if (!originalData.isNullOrEmpty()) {
            val rows = originalData.count().toInt()
            val cols = originalData[0].length
            val fullArray = Array(rows) { CharArray(cols) }
            val boolArray = Array(rows) { BooleanArray(cols) }
            var startingPosition = "0,0," //row, col, direction (1 for right, -1 for left, 0 for up)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    val currentChar = originalData[i][j]
                    fullArray[i][j] = currentChar
                    boolArray[i][j] = false
                    if (currentChar == '>') {
                        startingPosition = "$i,$j,$RIGHT"
                        hitsList["$i,$j"] = RIGHT
                        boolArray[i][j] = true
                    } else if (currentChar == '<') {
                        startingPosition = "$i,$j,$LEFT"
                        hitsList["$i,$j"] = LEFT
                        boolArray[i][j] = true
                    } else if (currentChar == '^') {
                        startingPosition = "$i,$j,$UP"
                        hitsList["$i,$j"] = UP
                        boolArray[i][j] = true
                    }
                }
            }
            routeProcessing(cols, fullArray, boolArray, startingPosition, hitsList)
        }
        return hitsList
    }

    fun routeProcessing(lineSize: Int, fullArray: Array<CharArray>, boolArray: Array<BooleanArray>, startingPosition: String, hitsList: MutableMap<String, String>){
        val startingPoints = startingPosition.split(",")
        if(startingPoints.size<3){
            return
        }
        val startingRow = startingPoints[0].toInt()
        val startingCol = startingPoints[1].toInt()
        if(startingRow ==-1 || startingCol==-1){
            return
        }
        val startingDirection = startingPoints[2]
        if(startingDirection == LEFT){
            var index = startingCol
            var currentChar = fullArray[startingRow][index]
            while(index>=0&&currentChar!=OBSTACLE){
                currentChar = fullArray[startingRow][index]
                if(currentChar!=OBSTACLE) {
                    if(!hitsList.containsKey("$startingRow,$index")) {
                        hitsList["$startingRow,$index"] = LEFT
                    }
                    boolArray[startingRow][index] = true
                    fullArray[startingRow][index] = 'X'
                    index--
                }
            }
            if(currentChar==OBSTACLE){
                //obstacle moving left means turn right (technically UP on the map) (but move 'back' one spot to avoid the obstacle)
                val newStartingPosition = "$startingRow,${index+1},$UP"
                routeProcessing(lineSize, fullArray, boolArray, newStartingPosition, hitsList)
            }else{
                //we reached the end of a line without turning - thats the end of the pattern. escape

                return
            }
        }else if(startingDirection == RIGHT){
            var index = startingCol
            var currentChar = fullArray[startingRow][index]
            while(index<lineSize&&currentChar!=OBSTACLE){
                currentChar = fullArray[startingRow][index]
                if(currentChar!=OBSTACLE){
                    if(!hitsList.containsKey("$startingRow,$index")) {
                        hitsList["$startingRow,$index"] = RIGHT
                    }
                    boolArray[startingRow][index] = true
                    fullArray[startingRow][index] = 'X'
                    index++
                }
            }
            if(currentChar==OBSTACLE){
                //obstacle moving right means turn right (technically DOWN on the map) (but move 'back' one spot to avoid the obstacle)
                val newStartingPosition = "$startingRow,${index-1},$DOWN"
                routeProcessing(lineSize, fullArray, boolArray, newStartingPosition, hitsList)
            }else{
                //we reached the end of a line without turning - thats the end of the pattern. escape

                return
            }
        }else if(startingDirection == DOWN){
            var index = startingRow
            var currentChar = fullArray[index][startingCol]
            while(index<fullArray.size&&currentChar!=OBSTACLE){
                currentChar = fullArray[index][startingCol]
                if(currentChar!=OBSTACLE) {
                    if(!hitsList.containsKey("$index,$startingCol")){
                        hitsList["$index,$startingCol"] = DOWN
                    }
                    boolArray[index][startingCol] = true
                    fullArray[index][startingCol] = 'X'
                    index++
                }
            }
            if(currentChar==OBSTACLE){
                //obstacle moving right means turn right (technically LEFT on the map) (but move 'back' one spot to avoid the obstacle)
                val newStartingPosition = "${index-1},$startingCol,$LEFT"
                routeProcessing(lineSize, fullArray, boolArray, newStartingPosition, hitsList)
            }else{
                //we reached the end of a line without turning - thats the end of the pattern. escape
                return
            }
        }else if(startingDirection == UP){
            var index = startingRow
            var currentChar = fullArray[index][startingCol]
            while(index>=0&&currentChar!=OBSTACLE){
                currentChar = fullArray[index][startingCol]
                if(currentChar!=OBSTACLE) {
                    if(!hitsList.containsKey("$index,$startingCol")){
                        hitsList["$index,$startingCol"] = UP
                    }
                    boolArray[index][startingCol] = true
                    fullArray[index][startingCol] = 'X'
                    index--
                }
            }
            if(currentChar==OBSTACLE){
                //obstacle moving up means turn right (technically RIGHT on the map) (but move 'back' one spot to avoid the obstacle)
                val newStartingPosition = "${index+1},$startingCol,$RIGHT"
                routeProcessing(lineSize, fullArray, boolArray, newStartingPosition,hitsList)
            }else{
                //we reached the end of a line without turning - thats the end of the pattern. escape
                return
            }
        }
        return

    }

    fun obstacleGoofiness(file: MultipartFile): Int{
        val originalData = processFile(file)?.stream()?.toList()
        val hitsList = routeMapAndProcessing(originalData)

        if(originalData.isNullOrEmpty()){
            return 0
        }
        //looking for 0,1(left), 0,2 (right), 1,2 (bot right), 1,1 (bot left) - so find 3 and create the 4th
        

        return hitsList.size-1
    }
}