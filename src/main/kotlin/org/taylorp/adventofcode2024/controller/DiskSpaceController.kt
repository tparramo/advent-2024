package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.DiskSpaceService

@Controller
class DiskSpaceController {

    @Autowired
    private lateinit var service: DiskSpaceService


    @PostMapping("/day9/part1")
    fun d2p1b(@RequestPart("file") file: MultipartFile): ResponseEntity<Long> {
        return ResponseEntity(service.sortDiskSpace(file, true),HttpStatus.OK)
    }

    @PostMapping("/day9/part2")
    fun d2p2(@RequestPart("file") file: MultipartFile): ResponseEntity<Long> {
        return ResponseEntity(service.sortDiskSpace(file, false),HttpStatus.OK)
    }

}