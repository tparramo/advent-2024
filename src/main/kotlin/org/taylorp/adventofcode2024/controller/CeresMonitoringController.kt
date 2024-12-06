package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.CeresMonitoringService

@Controller
class CeresMonitoringController {

    @Autowired
    private lateinit var service: CeresMonitoringService

    @PostMapping("/day4/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.findXmasPuzzle1(file),HttpStatus.OK)
    }

    @PostMapping("/day4/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.findX_MassPuzzle(file), HttpStatus.OK)
    }
}