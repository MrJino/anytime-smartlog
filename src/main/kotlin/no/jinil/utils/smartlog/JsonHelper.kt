package no.jinil.utils.smartlog

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.min

class JsonHelper(
    private val getMaxArray: () -> Int
) {
    private val gson = Gson()

    fun convert(tag: String, data: Any): String? {
        return if (data is String) {
            handleJson(tag, data)
        } else {
            handleJson(tag, gson.toJson(data))
        }
    }

    private fun handleJson(tag: String, data: String?): String? {
        val builder = StringBuilder()

        val json = try {
            data?.let {
                JSONObject(data)
            } ?: "Empty Data!!"
        } catch (e: Exception) {
            try {
                JSONArray(data)
            } catch (e: Exception) {
                data
            }
        }

        val delimiter = " ---------------------------------------------------------------------------|"
        //val delimiter1 = "|-------[ $tag ]-----------------------------------------------------------|"
        //val delimiter2 = delimiter1.replace("[^-|]".toRegex(), "-")

        builder.appendLine("| = $tag = |")
        builder.appendLine(delimiter)
        when (json) {
            is JSONObject -> handleObject(json)
            is JSONArray -> {
                builder.appendLine("|  total count: ${json.length()}")
                handleArray(json, "  ")
            }
            is String -> json + "\n"
            else -> ""
        }?.let {
            builder.append(it)
        } ?: run {
            return null
        }

        builder.append(delimiter)
        return builder.toString()
    }

    private fun handleObject(obj: JSONObject?, blank: String = ""): String? {
        if (obj == null || obj.length() == 0) {
            return null
        }
        val builder = StringBuilder()

        obj.keys().forEach { key ->
            obj[key].let { value->
                when (value) {
                    is JSONObject -> {
                        if (value.length() == 0) {
                            builder.appendLine("| $blank \"$key\": {}")
                        } else {
                            builder.appendLine("| $blank $key: {")
                            builder.append(handleObject(value, "$blank  "))
                            builder.appendLine("| $blank }")
                        }
                    }
                    is JSONArray -> {
                        if (value.length() == 0) {
                            builder.appendLine("| $blank \"$key\": []")
                        } else {
                            builder.appendLine("| $blank \"$key\": total (${value.length()})")
                            builder.append(handleArray(value, "$blank  "))
                        }
                    }
                    is String -> {
                        builder.appendLine("| $blank \"$key\": \"${value.replace("\n", "\\n")}\"")
                    }
                    else -> {
                        builder.appendLine("| $blank \"$key\": $value")
                    }
                }
            }
        }
        return builder.toString()
    }

    private fun handleArray(array: JSONArray, blank: String = ""): String {
        val builder = StringBuilder()

        if (getMaxArray() == 0)
            return builder.toString()

        fun handleOneLine(blank: String = "") {
            builder.append("| $blank - [")
            repeat(array.length()) { i ->
                builder.append("\"${array.get(i)}\"")
                if (i != array.length() - 1) {
                    builder.append(", ")
                }
            }
            builder.appendLine("]")
        }

        fun handleSeparate(blank: String = "") {
            //builder.appendLine("|  $blank[")
            repeat(min(array.length(), getMaxArray())) { i ->
                when (val item = array.get(i)) {
                    is JSONObject -> {
                        builder.appendLine("|  $blank{")
                        builder.append(handleObject(item, "$blank   "))
                        builder.appendLine("|  $blank},")
                    }
                    else -> {
                        builder.appendLine("|  $blank \"$item\"")
                    }
                }
            }
            //builder.appendLine("|  $blank]")
        }

        builder.appendLine("|$blank[")
        if (array.length() > 0) {
            when (val sample = array.get(0)) {
                is Int, is Float -> {
                    handleOneLine("$blank  ")
                }
                is String -> {
                    if (sample.length < 20) {
                        handleOneLine("$blank  ")
                    } else {
                        handleSeparate("$blank  ")
                    }
                }
                else -> {
                    handleSeparate("$blank  ")
                }
            }
        }
        builder.appendLine("|$blank]")
        return builder.toString()
    }
}