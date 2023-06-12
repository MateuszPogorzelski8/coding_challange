package com.example.mateusz_pogorzelski

import org.json.JSONObject

class JsonParser(query: String?) {
    private val query_ = query
    public fun Parse(): ArrayList<ItemsInterface>{

        val imagesList: ArrayList<ItemsInterface> = ArrayList()
        val obj = JSONObject(query_)
        val imagesarray = obj.getJSONArray("hits")
        for (i in 0..imagesarray.length() - 1) {
            val singleimage = imagesarray.getJSONObject(i)
            val username = singleimage.getString("user")
            val thumbnail = singleimage.getString("previewURL")
            val taglist = singleimage.getString("tags")
            val allinfo = ItemsInterface(username, thumbnail, taglist)
            imagesList.add(allinfo)
        }
        return imagesList
    }
}