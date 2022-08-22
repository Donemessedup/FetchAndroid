/**
 * This program handles the activity of activty_main.xml with a completed recycler view that fills its data
 * from the "hiring.json" website via a GET request using a HTTP URL Connection inside an Async Task
 *
 * Fetch Android Assignment
 * No sources to cite.
 *
 * @author Aaron Miller
 * @version v1.0 08/22/22
 */
package com.example.fetchrewardslist

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    val urlString = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
    var itemList = mutableListOf<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var getData = GetData()
        getData.execute()
    }


    /**
     * Async Task class for getting JSON data from url provided, cleaning the dataset of all instances with null or
     * empty properties, sorting the data by ListID and then name, and finally pushing that data to the
     * recycler view to display to the user in an organized list
     */
    inner class GetData() : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg p0: String?): String {

            var current = ""

            try {
                var url: URL? = null
                var urlConnection: HttpURLConnection? = null

                // create urlConnection and InputStream variables to read JSON data from website
                try {
                    url = URL(urlString)
                    urlConnection = url.openConnection() as HttpURLConnection?

                    var inStream: InputStream? = urlConnection?.inputStream
                    var inStreamReader: InputStreamReader? = InputStreamReader(inStream)

                    var data: Int = inStreamReader?.read() ?: -1
                    while (data != -1) {
                        current += Char(data)
                        data = inStreamReader?.read() ?: -1
                    }
                    // returns String of JSON data
                    return current
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return current
        }

        // Creates JSON array from "current" JSON string above, then cleans dataset of all instances
        // with null or "" as their name, and then adds remaining instances to list of Item objects that house the data
        override fun onPostExecute(result: String?) {
            try {
               var jArray: JSONArray = JSONArray(result)

                for(i in 0 until jArray.length()) {
                    var jObject: JSONObject = jArray.getJSONObject(i)
                    val tempId = jObject.getInt("id")
                    val tempListId = jObject.getInt("listId")
                    val tempName = jObject.getString("name")
                    var newItem = Item(tempId, tempListId, tempName)
                    if(tempName != "" && tempName != "null") {
                        itemList.add(newItem)
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // sorts list by ListID and then ID for an organized list
            itemList.sortWith(compareBy<Item> {it.listId}.thenBy { it.id })
            // puts list data into recycler view UI
            var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.hasFixedSize()
            var adapter = ItemAdapter(this@MainActivity, itemList)
            recyclerView.adapter = adapter
        }

    }


}