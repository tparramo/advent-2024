package org.taylorp.adventofcode2024.service

import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

const val POSITIVE = "Positive"
const val NEGATIVE = "Negative"
const val EQUAL = "Equal"

fun processFile( file: MultipartFile): MutableList<String>? {
    val fileReader = BufferedReader(InputStreamReader(file.inputStream))
    return fileReader.lines().toList()
}