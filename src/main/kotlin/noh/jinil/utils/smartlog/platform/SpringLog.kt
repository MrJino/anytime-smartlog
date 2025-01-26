package noh.jinil.utils.smartlog.platform

import noh.jinil.utils.smartlog.JsonHelper

class SpringLog(
    private val exception: (String) -> Unit,
    private val lifecycle: (String) -> Unit,
    private val debugging: (String) -> Unit,
    private val verbose: (String) -> Unit,
) {
    private var jsonMaxArray = 2
    private val jsonHelper = JsonHelper { jsonMaxArray }

    fun testSetup() {
        lifecycle("🍎 testSetup() ******************************")
    }

    fun testFunc(vararg params: String) {
        lifecycle("🍒 ${makeParam(*params)}")
    }

    fun controller(method: String, requestUri: String) {
        lifecycle("🔵 [${method}] ------------ $requestUri")
    }

    fun service(vararg params: String) {
        debugging("🟠 ${makeParam(*params)}")
    }

    fun usecase(vararg params: String) {
        debugging("⚪️ ${makeParam(*params)}")
    }

    fun function(vararg params: String) {
        debugging(makeParam(*params))
    }

    fun detail(vararg params: String) {
        params.iterator().forEach {
            verbose("-> $it")
        }
    }

    fun error(e: Exception) {
        exception(e.stackTraceToString())
    }

    fun state(log: String) {
        debugging("‼️ $log")
    }

    fun data(tag: String, data: Any) {
        jsonHelper.convert(tag, data)?.let { json ->
            verbose(json)
        }
    }

    private fun makeParam(vararg params: String, withBasicInfo: Boolean = true): String {
        val basicInfo = if (withBasicInfo) {
            Thread.currentThread().stackTrace[4].let {
                "[${it.className.split('.').last()}] ${it.methodName}()"
            }
        } else {
            ""
        }
        val strBuffer = StringBuffer().apply {
            append(basicInfo)
            params.iterator().forEach {
                append(" $it")
            }
        }
        return strBuffer.toString()
    }
}