package noh.jinil.utils.smartlog

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SmartLogTest {

    private val smartLog = SmartLog(
        exception = { log -> println(log) },
        lifecycle = { log -> println(log) },
        debugging = { log -> println(log) },
        verbose = { log -> println(log) }
    ).spring

    @Test
    @DisplayName("스프링 라이프 사이클 로그 테스트")
    fun springTest() {
        smartLog.controller(method = "GET", requestUri = "https://www.naver.com/api")
        smartLog.data("샘플데이터", sampleData)
        smartLog.service()
        smartLog.usecase()
        smartLog.function()
        smartLog.detail("detail data")
        smartLog.error(Exception("Error Test"))
    }
}