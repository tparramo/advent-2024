package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.LinenLayoutService

@Controller
class LinenLayoutController {

    @Autowired
    private lateinit var service: LinenLayoutService

    @PostMapping("/day19/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Long> {
        return ResponseEntity(service.getValidCombinations(file, false),HttpStatus.OK)
    }

    @PostMapping("/day19/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Long> {
        return ResponseEntity(service.getValidCombinations(file, true), HttpStatus.OK)
    }
}