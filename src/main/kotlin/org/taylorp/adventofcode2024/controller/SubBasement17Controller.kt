package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.RudolfNuclearPlantService
import org.taylorp.adventofcode2024.service.SubBasement17Service

@Controller
class SubBasement17Controller {

    @Autowired
    private lateinit var service: SubBasement17Service

    @PostMapping("/day5/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.getValidMiddles(file, true),HttpStatus.OK)
    }

    @PostMapping("/day5/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.getValidMiddles(file, false), HttpStatus.OK)
    }
}