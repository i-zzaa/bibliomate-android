package com.example.bibliomate_android.views

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliomate_android.R
import com.example.bibliomate_android.models.BookItem

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val bookTitle: TextView = itemView.findViewById(R.id.book_title)
    private val bookAuthor: TextView = itemView.findViewById(R.id.book_author)
    private val bookYear: TextView = itemView.findViewById(R.id.book_year)

    fun bind(bookItem: BookItem) {
        bookTitle.text = bookItem.volumeInfo.title
        bookAuthor.text = bookItem.volumeInfo.authors.joinToString(", ")
        bookYear.text = bookItem.volumeInfo.publishedDate
    }
}
