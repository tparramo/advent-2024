package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.CeresMonitoringService
import org.taylorp.adventofcode2024.service.ClawService

@Controller
class ClawController {

    @Autowired
    private lateinit var service: ClawService

    @PostMapping("/day13/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Double> {
        return ResponseEntity(service.findTokens(file, false),HttpStatus.OK)
    }

    @PostMapping("/day13/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Double> {
        return ResponseEntity(service.findTokens(file, true), HttpStatus.OK)
    }
}