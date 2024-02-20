package sparta.localconcert.global.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch

@Aspect
@Component
class LogAspect {
    private val logger = LoggerFactory.getLogger("Execution Time Logger")

    @Around("@annotation(LogExecutionTime)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        stopWatch.start()

        // @LogExecutionTime 어노테이션이 붙어있는 타겟 메소드를 실행
        val proceed: Any? = joinPoint.proceed()

        stopWatch.stop()
        logger.info(stopWatch.prettyPrint())

        return proceed
    }
}