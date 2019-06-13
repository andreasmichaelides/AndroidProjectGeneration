package parser

import java.lang.reflect.Type

interface ModelParser {

    fun <T> parseToModel(input: String, modelClass: Class<T>): T

    fun <T> parseToModel(input: String, type: Type): T

    fun parseToString(model: Any): String

}