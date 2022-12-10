package com.example.siren.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.siren.data.Article
import com.example.siren.databinding.ItemArticleBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

open class ArticleAdapter(query: Query, private val listener: OnArticleSelectedListener): FirestoreAdapter<ArticleAdapter.ViewHolder>(query) {

    class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(snapshot: DocumentSnapshot, listener: OnArticleSelectedListener?) {
            val article = snapshot.toObject<Article>() ?: return

            binding.tvTitle.text = article.title
            binding.tvCaption.text = article.caption
            binding.tvTag.text = article.tag
            Glide.with(itemView.context)
                .load(article.image)
                .into(binding.ivArticle)

            binding.root.setOnClickListener {
                listener?.onArticleSelected(snapshot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)

    }

    interface OnArticleSelectedListener {
        fun onArticleSelected(article: DocumentSnapshot)
    }
}