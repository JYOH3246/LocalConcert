package sparta.localconcert.domain.concerts.repository


import org.springframework.data.domain.Page
import org.springframework.data.redis.connection.zset.Aggregate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import sparta.localconcert.global.objectmapper.MapperConfig


@Repository
class RedisConcertRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: MapperConfig
) {
    fun exists(key: String): Boolean {
        val result = redisTemplate.hasKey(key)
        return result
    }

    fun saveZSetData(key: String, value: Any) {
        redisTemplate.opsForZSet().addIfAbsent(key, value, 0.0)
        redisTemplate.opsForZSet().incrementScore(key, value, 1.0)
    }

    fun getZSetValue(key: String): Set<Any> {
        return redisTemplate.opsForZSet().range(key, 0, -1) ?: throw IllegalArgumentException("없음")
    }

    fun <T> saveZSetJsonData(key: String, data: Page<T>) {
        val mapper = objectMapper.objectMapper()
        for (element in data.content) {
            val value: String = mapper.writeValueAsString(element)
            redisTemplate.opsForZSet().addIfAbsent(key, value, 0.0)
            redisTemplate.opsForZSet().incrementScore(key, value, 1.0)
        }
        searchKey()
        unionZSets()
    }

    fun <T> saveHashJsonData(key: String, values: Page<T>) {
        val mapper = objectMapper.objectMapper()
        val value = mapper.writeValueAsString(values)
        val a = mapOf(
            "page" to value,
        )
        redisTemplate.opsForHash<String, Any>().putAll(key, a)
    }

    fun getHashValue(key: String): Map<String, Any> {
        val a = redisTemplate.opsForHash<String, Any>().entries(key)
        return a

    }

    fun searchKey(): Set<String> {
        val keys = redisTemplate.keys("keyword::*")
        return keys
    }

    fun unionZSets() {
        redisTemplate.opsForZSet().unionAndStore("empty", searchKey(), "postRanking", Aggregate.SUM)
    }

    fun getSearchRanking(key: String): Set<Any> {
        val rank = redisTemplate.opsForZSet().reverseRange(key, 0, -1) ?: throw IllegalArgumentException("없음")
        return rank
    }

}