package no.jinil.utils.smartlog

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class JsonHelperTest {
    @Test
    @DisplayName("Json 데이터 pretty 포멧 변경 테스트")
    fun convertTest() {
        val jsonHelper = JsonHelper(getMaxArray = { 2 })
        val result = jsonHelper.convert("샘플데이터", sampleData)
        println(result)
        Assertions.assertTrue(true)
    }
}