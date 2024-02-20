package sparta.localconcert.domain.concerts.repository


import org.springframework.data.redis.connection.zset.Aggregate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import sparta.localconcert.domain.concerts.dto.response.SearchConcertResponse
import sparta.localconcert.global.config.ModuleConfig


@Repository
class RedisConcertRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ModuleConfig
) {
    fun <T> saveData(key: String, data: T) {
        val mapper = objectMapper.objectMapper()
        val value: String = mapper.writeValueAsString(data)
        redisTemplate.opsForValue().set(key, value)
    }

    fun saveListData(key: String, data: List<Any>) {
        val mapper = objectMapper.objectMapper()
        for (element in data) {
            val value: String = mapper.writeValueAsString(element)
            redisTemplate.opsForList().leftPush(key, value)
        }
    }

    fun exists(key: String): Boolean {
        val result = redisTemplate.hasKey(key)
        return result
    }

    fun saveZSetData(key: String, value: Any) {
        redisTemplate.opsForZSet().addIfAbsent(key, value, 0.0)
        redisTemplate.opsForZSet().incrementScore(key, value, 1.0)
    }
    fun getZSetValue(key: String) : Set<Any> {
        redisTemplate.opsForZSet().range(key,0,-1) ?: throw IllegalArgumentException("없음")
        return redisTemplate.opsForZSet().range(key,0,-1) ?: throw IllegalArgumentException("없음")
        /*
        val a = redisTemplate.opsForZSet().range(key,0,-1) ?: throw IllegalArgumentException("없음")
        val json = mapper.readValue(a,SearchConcertResponse::class.java)

         */
    }

    fun saveZSetJsonData(key: String, data: List<Any>) {
        val mapper = objectMapper.objectMapper()
        for (element in data) {
            val value: String = mapper.writeValueAsString(element)
            redisTemplate.opsForZSet().addIfAbsent(key, value, 0.0)
            redisTemplate.opsForZSet().incrementScore(key, value, 1.0)
        }
        searchKey()
        println(searchKey())
        unionZSets()
    }

    fun searchKey(): Set<String> {
        val keys = redisTemplate.keys("keyword::*")
        return keys
    }

    fun unionZSets() {
        redisTemplate.opsForZSet().unionAndStore("empty", searchKey(), "postRanking", Aggregate.SUM)
    }

}