package sparta.localconcert.global.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Aspect
@Component
class CacheAspect {
    private val cache = ConcurrentHashMap<String, Any>()

    @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
    fun cache(joinPoint: ProceedingJoinPoint): Any? {
        val cacheKey = generateCacheKey(joinPoint)

        if (cache.containsKey(cacheKey)) {
            return cache[cacheKey]
        } else {
            val result = joinPoint.proceed()
            cache[cacheKey] = result
            return cache[cacheKey]
        }
    }

    private fun generateCacheKey(joinPoint: ProceedingJoinPoint): String {
        // 메서드의 인자를 기반으로 고유한 캐시 키 생성
        return "${joinPoint.signature.name}_${joinPoint.args[0]}"
    }
}