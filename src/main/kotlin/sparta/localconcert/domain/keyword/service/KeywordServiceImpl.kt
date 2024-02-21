package sparta.localconcert.domain.keyword.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sparta.localconcert.domain.keyword.dto.response.KeywordResponse
import sparta.localconcert.domain.keyword.model.Keyword
import sparta.localconcert.domain.keyword.repository.KeywordRepository

@Service
class KeywordServiceImpl(
    private val keywordRepository: KeywordRepository
) : KeywordService {

    @Transactional
    override fun incrementKeywordCount(keyword: String) {
        val keywordEntity = keywordRepository.findByKeyword(keyword)
            ?: Keyword(keyword = keyword) // 새 Keyword 엔티티 생성
        keywordEntity.count += 1
        keywordRepository.save(keywordEntity)
    }

    @Transactional(readOnly = true)
    override fun getPopularKeywords(page: Int, size: Int): List<KeywordResponse> {
        return keywordRepository.findTopKeywords(page, size).map { KeywordResponse.fromEntity(it) }
    }
}