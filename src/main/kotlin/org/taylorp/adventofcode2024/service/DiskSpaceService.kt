package org.taylorp.adventofcode2024.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.model.BlockData

@Service
class DiskSpaceService {
    fun sortDiskSpace(file: MultipartFile, day1:Boolean): Long {
        val originalData = processFile(file)?.stream()?.toList()?.get(0) // only one line
        val blocks = mutableListOf<BlockData>()
        val finalBlocks = mutableListOf<String>()
        if (originalData != null) {
            var fileId = 0
            for( i in originalData.indices){
                if(i%2==1){
                    continue//skip every other item as it is used in the previously ran loop
                }
                val size = originalData[i].digitToInt()
                var space = 0
                if(i != originalData.length-1){
                    //not the last item so space will not be 0
                    space = originalData[i+1].digitToInt()
                }
                val block = BlockData(fileId, size, space)
                blocks.add(block)
                for(j in 0 until size){
                    finalBlocks.add(fileId.toString())
                }
                for(k in 0 until space){
                    finalBlocks.add(".")
                }
                fileId++
            }
        }

        var count = 0L
        if(day1) {
            for (i in finalBlocks.indices) {
                if (finalBlocks[i] == ".") {
                    for (j in finalBlocks.size - 1 downTo i) {
                        if (finalBlocks[j] != ".") {
                            finalBlocks[i] = finalBlocks[j]
                            finalBlocks[j] = "."
                            break;
                        }
                    }
                }
            }

            var filtered = finalBlocks.filter { it != "." }

            for (index in filtered.indices) {
                count += (index * filtered[index].toLong())
            }
        }else{
            for(i in blocks.size-1 downTo 1){
                val mover = blocks[i]
                val moverLength = mover.length
                for(j in 0 until i){
                    val block = blocks[j]
                    val remainingSpace = block.space-moverLength
                    if(remainingSpace>=0){
                        block.space = 0
                        if(j!=(i-1)){
                            blocks[i-1].space += moverLength+mover.space
                            mover.space = remainingSpace
                        }else{
                            mover.space+=remainingSpace+moverLength
                        }
                        blocks.add(j+1, blocks[i])
                        blocks.removeAt(i+1)
                        break;
                    }
                }
            }


            var finals = mutableListOf<String>()
            for(block in blocks){
                for(i in 0 until block.length){
                    finals.add(block.index.toString())
                }
                for(j in 0 until block.space){
                    finals.add(".")
                }
            }

            for(index in 0 until finals.size){
                if(finals[index] == "."){
                    continue
                }
                println("count = $index * ${finals[index]}")
                println("$count")
                count+=(index * finals[index].toLong())
                println("$count")
            }

        }
        return count
    }

}
