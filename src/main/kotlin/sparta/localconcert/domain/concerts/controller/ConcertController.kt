package sparta.localconcert.domain.concerts.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sparta.localconcert.domain.concerts.dto.request.AddConcertRequest
import sparta.localconcert.domain.concerts.dto.request.UpdateConcertRequest
import sparta.localconcert.domain.concerts.dto.response.FindConcertResponse
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.domain.concerts.service.ConcertService
import sparta.localconcert.global.aop.LogExecutionTime

@RequestMapping("/concerts")
@RestController
class ConcertController(
    private val concertService: ConcertService,
) {

    @GetMapping()
    fun searchConcert(@RequestParam(name = "title") title: String) : ResponseEntity<List<SearchConcertResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(concertService.searchConcert(title))
    }

    @LogExecutionTime
    @GetMapping("/v2")
    fun searchCacheConcert(@RequestParam(name = "title") title: String) : ResponseEntity<List<SearchConcertResponse>>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(concertService.searchCacheConcert(title))
    }

    @GetMapping("/{concertId}")
    fun findConcert(@PathVariable concertId: Long): ResponseEntity<FindConcertResponse>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(concertService.findConcert(concertId))
    }

    @PostMapping()
    fun addConcert(request: AddConcertRequest):ResponseEntity<String>{
        concertService.addConcert(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("등록이 완료되었습니다.")
    }

    @PutMapping("/{concertId}")
    fun updateConcert(@PathVariable concertId: Long, request: UpdateConcertRequest):ResponseEntity<String>{
        concertService.updateConcert(request, concertId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("수정이 완료되었습니다.")
    }

    @DeleteMapping("/{concertId}")
    fun deleteConcert(@PathVariable concertId: Long):ResponseEntity<Void>{
        concertService.deleteConcert(concertId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .build()
    }
}