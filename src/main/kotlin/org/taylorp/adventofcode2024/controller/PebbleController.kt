package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.CeresMonitoringService
import org.taylorp.adventofcode2024.service.ClawService
import org.taylorp.adventofcode2024.service.PebbleService

@Controller
class PebbleController {

    @Autowired
    private lateinit var service: PebbleService

    @PostMapping("/day11/{blinks}")
    fun d11(@RequestPart("file") file: MultipartFile, @PathVariable blinks: String): ResponseEntity<Long> {
        return ResponseEntity(service.blinkAndCount(file, blinks.toInt()),HttpStatus.OK)
    }

    @PostMapping("/day11/{blinks}/v2")
    fun d11v2(@RequestPart("file") file: MultipartFile, @PathVariable blinks: String): ResponseEntity<Long> {
        return ResponseEntity(service.blinkAndCount2(file, blinks.toInt()),HttpStatus.OK)
    }


}