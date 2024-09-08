package com.example.bibliomate_android

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliomate_android.api.GoogleBooksApiService
import com.example.bibliomate_android.models.BookItem
import com.example.bibliomate_android.models.BookResponse
import com.example.bibliomate_android.views.BookAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var savedRecyclerView: RecyclerView
    private lateinit var searchBookAdapter: BookAdapter
    private lateinit var savedBookAdapter: BookAdapter
    private lateinit var searchBookList: MutableList<BookItem>
    private lateinit var savedBookList: MutableList<BookItem>
    private val sharedPreferencesName = "SavedBooksPrefs"
    private val savedBooksKey = "savedBooks"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchInput = findViewById<EditText>(R.id.search_input)
        val searchButton = findViewById<Button>(R.id.search_button)

        searchBookList = mutableListOf()
        savedBookList = loadSavedBooks()

        searchRecyclerView = findViewById(R.id.searchResultsRecyclerView)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchBookAdapter = BookAdapter(searchBookList) { bookItem ->
            saveBook(bookItem)
        }
        searchRecyclerView.adapter = searchBookAdapter

        savedRecyclerView = findViewById(R.id.savedBooksRecyclerView)
        savedRecyclerView.layoutManager = LinearLayoutManager(this)
        savedBookAdapter = BookAdapter(savedBookList) {}
        savedRecyclerView.adapter = savedBookAdapter

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            if (query.isNotEmpty()) {
                searchBooks(query)
            }
        }
    }

    private fun searchBooks(query: String) {
        val service = GoogleBooksApiService.create().searchBooks(query)

        service.enqueue(object : Callback<BookResponse> {
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items
                    if (books != null) {
                        searchBookList.clear()
                        searchBookList.addAll(books)
                        searchBookAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                Log.e("API_ERROR", "Falha na requisição: ${t.message}")
            }
        })
    }

    private fun saveBook(bookItem: BookItem) {
        if (!savedBookList.contains(bookItem)) {
            savedBookList.add(bookItem)
            savedBookAdapter.notifyDataSetChanged()
            saveToAsyncStorage(savedBookList)
        }
    }

    private fun loadSavedBooks(): MutableList<BookItem> {
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)
        val savedBooksJson = sharedPreferences.getString(savedBooksKey, null)
        return if (savedBooksJson != null) {
            val type = object : TypeToken<List<BookItem>>() {}.type
            Gson().fromJson(savedBooksJson, type)
        } else {
            mutableListOf()
        }
    }

    private fun saveToAsyncStorage(savedBooks: List<BookItem>) {
        val sharedPreferences = getSharedPreferences(sharedPreferencesName, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val savedBooksJson = Gson().toJson(savedBooks)
        editor.putString(savedBooksKey, savedBooksJson)
        editor.apply()
    }
}
