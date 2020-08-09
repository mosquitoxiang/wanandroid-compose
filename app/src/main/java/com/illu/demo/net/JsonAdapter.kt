package main.java.com.zhangyu.network

import com.google.gson.*
import java.lang.Exception
import java.lang.NumberFormatException
import java.lang.reflect.Type

class IntegerDefaultAdapter : JsonSerializer<Int>, JsonDeserializer<Int>{

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        try {
            if (json?.asString.equals("") || json?.asString.equals("null")){
                return 0
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        try {
            return json!!.asInt
        }catch (e: NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }
}

class DoubleDefaultAdapter : JsonSerializer<Double>, JsonDeserializer<Double>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
        try {
            if (json?.asString.equals("") || json?.asString.equals("null")){
                return 0.00
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        try {
            return json!!.asDouble
        }catch (e: NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Double?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }
}

class LongDefaultAdapter : JsonSerializer<Long>, JsonDeserializer<Long>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        try {
            if (json?.asString.equals("") || json?.asString.equals("null")){
                return 0L
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        try {
            return json!!.asLong
        }catch (e: NumberFormatException){
            throw JsonSyntaxException(e)
        }
    }

    override fun serialize(src: Long?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

}