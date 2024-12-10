package org.taylorp.adventofcode2024

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.math.pow


@SpringBootTest
class AdventOfCode2024ApplicationTests {

    @Test
    fun contextLoads() {
        val testAble1 = "3267: 81 40 27".split(":")
        val testAble2 = "161011: 16 10 13 15 28".split(":")

        val string = "16*10+13+15*28"//5264
        println(""+evaluate(string))

        val testVal1 = testAble1[0]
        val testString1 = testAble1[1]

        val testVal2 = testAble2[0]
        val testString2 = testAble2[1]

        val testItems = testString1.trim().split(" ")
        val testItems2 = testString2.trim().split(" ")


        val totalOperands = testItems2.size-1
        var startString = ""
        val lists = mutableListOf<String>()
        for(i in 0 until totalOperands){
            startString+="+"
        }
        lists.add(startString)
        for(j in 0 until totalOperands){
            startString = startString.replaceRange(IntRange(j, j),"*")
            lists.add(startString)
        }


        val finalList = mutableListOf<String>()
        //skip first and last string in list
        getPermutations(lists, finalList)

        println(finalList.size)

    }

    fun getPermutations(list: MutableList<String>, finalList: MutableList<String>){
        finalList.add(list[0])
        finalList.add(list[list.size-1])
        for(i in 1 until list.size-1){//skip first and last
            finalList.addAll(permutations(list[i]))
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

    // Helper function to swap characters in the StringBuilder
    fun swap(s: StringBuilder, i: Int, j: Int) {
        val temp = s[i]
        s.setCharAt(i, s[j])
        s.setCharAt(j, temp)
    }

    fun evaluate(string: String):Int{
        val values = mutableListOf<Int>()
        val operands = mutableListOf<Char>()
        val regex = "[0-9]+".toRegex()
        regex.findAll(string).asIterable().forEach { match ->
            values.add((match.value as String).toInt())
        }
        string.forEach{item ->
            if(item=='+' || item=='*'){
                operands.add(item)
            }
        }

        var count = 0
        for(index in 0 until values.size-1){
            if(count == 0){
                if(operands[index]=='*') {
                    count += (values[index] * values[index + 1])
                }else{
                    count+= (values[index]+values[index+1])
                }
                continue
            }
            if(operands[index]=='+'){
                count += values[index + 1]
            }else{
                count *= values[index + 1]
            }
        }
        return count
    }

}
