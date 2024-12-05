package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.RudolfNuclearPlantService

@Controller
class RudolfNuclearPlantController {

    @Autowired
    private lateinit var service: RudolfNuclearPlantService

    @PostMapping("/day2/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.findSafeReports(file),HttpStatus.OK)
    }

    @PostMapping("/day2/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.findTemperedSafeReports(file), HttpStatus.OK)
    }
}