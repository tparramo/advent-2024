package org.taylorp.adventofcode2024.service

import org.apache.el.parser.Token
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.math.ceil
import kotlin.math.floor

@Service
class ClawService {


    fun findTokens(file: MultipartFile, day2: Boolean): Double{
        val originalData = processFile(file)?.stream()?.toList()
        val regex ="[0-9]+|-[0-9]+".toRegex()
        var tokens = mutableListOf<TokensNeeded>()
        if (originalData != null) {
            var buttonA1 = ""//ax
            var buttonA2 = ""//ay
            var buttonB1 = ""//bx
            var buttonB2 = ""//by
            var c1 = ""
            var c2 = ""
            for(line in originalData){
                if(line.contains("Button A:")){
                    val splitMatches = regex.findAll(line).toList()
                    buttonA1 = splitMatches[0].value
                    buttonA2 = splitMatches[1].value
                }
                if(line.contains("Button B:")){
                    val splitMatches = regex.findAll(line).toList()
                    buttonB1 = splitMatches[0].value
                    buttonB2 = splitMatches[1].value
                }
                if(line.contains("Prize:")) {
                    val splitMatches = regex.findAll(line).toList()
                    c1 = splitMatches[0].value
                    c2 = splitMatches[1].value

                    var c1Final:Long;
                    var c2Final:Long;
                    if(day2){
                        c1Final = c1.toLong()+ 10000000000000L
                        c2Final = c2.toLong()+ 10000000000000L
                    }else{
                        c1Final = c1.toLong()
                        c2Final = c2.toLong()
                    }
                    tokens.add(foundPrize(buttonA1.toLong(), buttonB1.toLong(), buttonA2.toLong(), buttonB2.toLong(), c1Final, c2Final, day2))
                    buttonA1 = ""//ax
                    buttonB1 = ""//ay
                    buttonA2 = ""//bx
                    buttonB2 = ""//by
                }
            }
        }
        var count = 0.0
        tokens.filter { it.valid }.forEach { count += (it.x*3 + it.y) }
        return count
    }

    fun foundPrize(buttonA1: Long, buttonB1: Long, buttonA2: Long, buttonB2: Long, c1: Long, c2: Long, day2: Boolean): TokensNeeded{

        val numerator1 = ((buttonB1*(-c2))-(buttonB2*(-c1)))
        val numerator2 = (((-c1)*buttonA2)-((-c2)*buttonA1))
        val denominator = ((buttonA1*buttonB2)-(buttonA2*buttonB1))

        val x = numerator1.toDouble()/denominator
        val y = numerator2.toDouble()/denominator

        var tokensNeeded: TokensNeeded? = null
        tokensNeeded = if(floor(x) != ceil(x) && floor(y) != ceil(y)){//is whole number
            TokensNeeded(x, y, false)
        }else{
            TokensNeeded(x, y, true)
        }
        return tokensNeeded
    }

}

data class TokensNeeded(
    var x: Double,
    var y: Double,
    var valid: Boolean
)
