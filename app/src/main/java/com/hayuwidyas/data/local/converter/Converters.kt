package com.hayuwidyas.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Room Type Converters
 * 
 * Converts complex data types to and from database-compatible formats.
 * Used for storing lists and maps in Room database.
 */
class Converters {
    
    private val gson = Gson()
    
    /**
     * Convert List<String> to JSON string
     */
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }
    
    /**
     * Convert JSON string to List<String>
     */
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
    
    /**
     * Convert Map<String, List<String>> to JSON string
     */
    @TypeConverter
    fun fromStringListMap(value: Map<String, List<String>>): String {
        return gson.toJson(value)
    }
    
    /**
     * Convert JSON string to Map<String, List<String>>
     */
    @TypeConverter
    fun toStringListMap(value: String): Map<String, List<String>> {
        val mapType = object : TypeToken<Map<String, List<String>>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }
    
    /**
     * Convert Map<String, String> to JSON string
     */
    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String {
        return gson.toJson(value)
    }
    
    /**
     * Convert JSON string to Map<String, String>
     */
    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }
    
    /**
     * Convert List<Int> to JSON string
     */
    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return gson.toJson(value)
    }
    
    /**
     * Convert JSON string to List<Int>
     */
    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}
