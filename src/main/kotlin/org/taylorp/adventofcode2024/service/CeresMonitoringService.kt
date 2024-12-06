package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CeresMonitoringService {

    //day 4 part 1 AKA matrix math without using matrices or math libraries. YUCK!

    //Create list of each line and each diagonal line.
    //Regex find on each lineString for backwards and forwards xmas string

    //solution drawback - any line greater than max string size (2+billion characters)

    val xmasRegex = "XMAS".toRegex()
    val samxRegex = "SAMX".toRegex()
    //This is messy and I hate it :( but it WORKS
    fun findXmasPuzzle1(file: MultipartFile): Int{
        val originalData = processFile(file)
        val horizontalLines = mutableListOf<String>()
        val vertAndDiagLines = mutableListOf<String>()
        originalData?.stream()?.forEach { value ->
            horizontalLines.add(value)
        }
        val stringLength = horizontalLines[0].length
        //verticals
        for(index in 0 until stringLength){
            var vertical = ""
            for(index2 in 0 until horizontalLines.size){
                vertical+=(horizontalLines[index2][index])
            }
            vertAndDiagLines.add(vertical)
        }
        //diagonals
        for(row in 0 until horizontalLines.size){//one way diagonals - moving right
            if(row==0) {
                for (col in 0 until stringLength) {//gets the starting point
                    var diagonal = ""
                    var index = 0
                    //from starting point going down one step and right one step
                    while (index < horizontalLines.size && col + index < stringLength) {//primary diagonal and upper half of matrix only
                        diagonal += horizontalLines[index][col + index]
                        index++
                    }
                    vertAndDiagLines.add(diagonal)
                }
            }else{// to reduce duplicates do only lower half of 'matrix'
                var diagonal = ""
                var index = 0
                //from starting point going down one step and right one step
                while (row + index < horizontalLines.size && index < stringLength) {
                    diagonal += horizontalLines[row + index][index]
                    index++
                }
                vertAndDiagLines.add(diagonal)
            }
        }

        for(row in 0 until horizontalLines.size){//the other ways diagonals - moving left (mirror of above)
            if(row==0) {
                for (col in 0 until stringLength) {
                    var diagonal = ""
                    var index = 0
                    while ( index < horizontalLines.size && col + index < stringLength) {
                        diagonal += horizontalLines[index][stringLength - col - index - 1]
                        index++
                    }
                    vertAndDiagLines.add(diagonal)
                }
            }else{
                var diagonal = ""
                var index = 0
                while (row + index < horizontalLines.size && index < stringLength) {
                    diagonal += horizontalLines[row + index][stringLength - index - 1]
                    index++
                }
                vertAndDiagLines.add(diagonal)
            }
        }

        return countInstance(vertAndDiagLines) + countInstance(horizontalLines)
    }

    fun countInstance(list: MutableList<String>):Int{
        var xmasCount = 0
        list.forEach{ item ->
            xmasCount+=xmasRegex.findAll(item).count()
            xmasCount+=samxRegex.findAll(item).count()
        }
        return xmasCount
    }

    //day 4 part 2
    //MUCH BETTER THAN PART 1!!!!
    //Pretty happy with this solution

    fun findX_MassPuzzle(file: MultipartFile): Int{
        val originalData = processFile(file)
        val horizontalLines = mutableListOf<String>()
        originalData?.stream()?.forEach { value ->
            horizontalLines.add(value)
        }

        val stringLength = horizontalLines[0].length
        var count = 0
        //row, col
        for(row in 1 until horizontalLines.size-1){
            for(col in 1 until stringLength-1) {
                val possibleCrossPoint = horizontalLines[row][col]
                //starting at 1,1, find A. (skip first row and first column as it cannot contain the crosspoint of an x)
                //when A found, check four corners
                if (possibleCrossPoint == 'A') {
                    val cornerUpLeft = horizontalLines[row-1][col-1]
                    val cornerUpRight = horizontalLines[row-1][col+1]
                    val cornerDownRight = horizontalLines[row+1][col+1]
                    val cornerDownLeft = horizontalLines[row+1][col-1]
                    val crossLeftTopToRightBot = cornerUpLeft + "" + possibleCrossPoint + cornerDownRight
                    val crossRightTopToLeftBot = cornerUpRight + "" + possibleCrossPoint + cornerDownLeft
                    if((crossLeftTopToRightBot == "SAM" || crossLeftTopToRightBot == "MAS") &&(crossRightTopToLeftBot == "SAM" || crossRightTopToLeftBot == "MAS")){
                        count++
                    }
                }
            }
        }

        return count
    }

}
