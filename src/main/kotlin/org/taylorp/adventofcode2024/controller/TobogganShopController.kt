package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.TobogganShopService

@Controller
class TobogganShopController {

    @Autowired
    private lateinit var service: TobogganShopService

    @PostMapping("/day3/part1")
    fun d2p1(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.processMultipications(file),HttpStatus.OK)
    }

    @PostMapping("/day3/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Int> {
        return ResponseEntity(service.processMultipicationsDosAndDonts(file), HttpStatus.OK)
    }
}