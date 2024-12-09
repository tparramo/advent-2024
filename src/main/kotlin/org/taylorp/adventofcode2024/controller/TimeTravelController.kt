package org.taylorp.adventofcode2024.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.taylorp.adventofcode2024.service.TimeTravelService

@RestController
class TimeTravelController {

    @Autowired
    private lateinit var service: TimeTravelService

    @PostMapping("/day6/part1")
    fun d1p1(@RequestPart("file") file: MultipartFile): Int {
        return service.trackRouteGuardLocations(file)
    }

    @PostMapping("/day6/part2")
    fun d1p2(@RequestPart("file") file: MultipartFile): Int {
        return service.obstacleGoofiness(file)
    }
}