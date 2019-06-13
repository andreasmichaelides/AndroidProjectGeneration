package parser

import com.google.gson.Gson
import java.lang.reflect.Type

class ModelParserImpl(private val gson: Gson) : ModelParser {

    override fun <T> parseToModel(input: String, modelClass: Class<T>): T = gson.fromJson(input, modelClass)

    override fun <T> parseToModel(input: String, type: Type): T = gson.fromJson(input, type)

    override fun parseToString(model: Any): String =
            gson.toJson(model)
                    .toString()
}