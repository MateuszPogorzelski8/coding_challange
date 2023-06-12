package com.example.mateusz_pogorzelski

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

class GetJsonFromMock(private val context: Context) {

    public fun getJSONfromMock(): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val file = context.assets.open("Json_Mock.json")
            val size = file.available()
            val buffer = ByteArray(size)
            file.read(buffer)
            file.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}