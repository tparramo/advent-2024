package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.OfficeService

@RestController
class OfficeController {

    @Autowired
    private lateinit var service: OfficeService

    @PostMapping("/day1/part1")
    fun d1p1(@RequestPart("file") file: MultipartFile): Int {
        return service.calculateSumOfDiffs(file)
    }

    @PostMapping("/day1/part2")
    fun d1p2(@RequestPart("file") file: MultipartFile): Int {
        return service.calculateOccurrenceDiff(file)
    }
}