package sparta.localconcert.domain.keyword.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sparta.localconcert.domain.keyword.dto.response.KeywordResponse
import sparta.localconcert.domain.keyword.service.KeywordService

@RestController
@RequestMapping("keywords")
class KeywordController(
    private val keywordService: KeywordService
) {
    @GetMapping("/popular")
    fun getPopularKeywords(
        @RequestParam(name = "page", defaultValue = "1") page: Int,
        @RequestParam(name = "size", defaultValue = "10") size: Int
    ): ResponseEntity<List<KeywordResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(keywordService.getPopularKeywords(page, size))
    }
}