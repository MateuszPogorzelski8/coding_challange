package com.example.mateusz_pogorzelski

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mateusz_pogorzelski.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    private lateinit var scrollview: RecyclerView
    private lateinit var image: ImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var button: Button
    private lateinit var search: EditText
    private lateinit var text: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button = findViewById(R.id.button)
        search = findViewById(R.id.Search)
        scrollview = findViewById(R.id.myRecyclerView)
        var base_key_word: String = "fruits"


        /* JSON FROM MOCK JSON
        val getjson = GetJsonFromMock(this)
        val getjsonstring = getjson.getJSONfromMock()
        val jsonparser = JsonParser(getjsonstring)
        val jsonparsing = jsonparser.Parse()
        */

        //  UNCOMMENT
        //MOCK     GetIntoRecyclerView(this, jsonparsing)
        //ONLINE
        htmlCall("https://pixabay.com/api/?key=37163054-96feb960bd030eaca1a0ad907&per_page=100&pretty=true&q=fruit")




        binding.button.setOnClickListener(View.OnClickListener {
            var myobject: String = "https://pixabay.com/api/?key=37163054-96feb960bd030eaca1a0ad907&per_page=100&pretty=true&q=fruit"
            myobject = searchUrlReplacer(myobject, search.text.toString(), base_key_word)
            base_key_word = saveKeyWord(search.text.toString())
            htmlCall(myobject)
        })
    }


    private fun GetIntoRecyclerView(context: Context, list: ArrayList<ItemsInterface>) {
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)
        val itemadapter = Adapter(this, list)
        binding.myRecyclerView.adapter = itemadapter
    }



    // MAY BE USEFUL FOR RECYCLER VIEW DEBUGGING
    private fun mockNumbers(): List<String> {
        var list: MutableList<String> = mutableListOf()
        for (i in 1..15) {
            list.add("Item number $i")
        }
        return list
    }

    // REPLACING OLD WORD WITH NEW
    // + REPLACING " " WITH "+"
    private fun searchUrlReplacer(url: String, new_key_word: String, old_key_word: String): String {
        var base = url.substringBefore("q=") + "q="
        val modified = base + new_key_word
        if(modified.contains(" ")){
            val replaced = modified.replace(" ", "+")
            return replaced
        }
        else{
            return modified
        }
    }
    // HTML CALL
    private fun htmlCall(myobject: String){
        if (myobject.isNotEmpty()) {
            val imagesFetch = OkHttpClient()
            val request = Request.Builder()
                .url(myobject)
                .build()
            imagesFetch.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    Log.i("Response", "Recieved Response from server")
                    response.use {
                        if (!response.isSuccessful) {
                            Log.e("HTTP Error", "Loading failed")
                        } else {
                            val body = response?.body?.string()
                            runOnUiThread {
                                handleSearchResponse(body)
                            }
                        }
                    }
                }
            })
        }

    }
    private fun handleSearchResponse(responseBody: String?) {
        if (responseBody != null) {
            val parsing = JsonParser(responseBody)
            val _parsing = parsing.Parse()
            GetIntoRecyclerView(applicationContext, _parsing)
        }
    }

    // SAVING KEY WORD + REPLACING " " WITH "+"
    private fun saveKeyWord(key_word: String): String{
        if(key_word.contains(" ")){
            val replaced = key_word.replace(" ", "+")
            return replaced
        }
        else{
            return key_word
        }
    }
}