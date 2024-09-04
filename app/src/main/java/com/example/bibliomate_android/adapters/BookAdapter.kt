package com.example.bibliomate_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliomate_android.R
import com.example.bibliomate_android.models.BookItem

class BookAdapter(private val bookList: List<BookItem>) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val bookItem = bookList[position]
        holder.bind(bookItem)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}
