package com.example.bibliomate_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bibliomate_android.R
import com.example.bibliomate_android.models.BookItem

class BookAdapter(
    private val bookList: List<BookItem>,
    private val onClick: (BookItem) -> Unit
) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)

        holder.itemView.setOnClickListener {
            onClick(book)
        }
    }

    override fun getItemCount(): Int = bookList.size
}
