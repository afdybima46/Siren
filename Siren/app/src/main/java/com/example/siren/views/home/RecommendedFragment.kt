package com.example.siren.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siren.R
import com.example.siren.adapter.ArticleAdapter
import com.example.siren.databinding.FragmentRecommendedBinding
import com.example.siren.utils.stopLoading
import com.example.siren.views.detail.DetailActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RecommendedFragment : Fragment(), ArticleAdapter.OnArticleSelectedListener {

    private lateinit var firestore: FirebaseFirestore
    private var query: Query? = null
    private var adapter: ArticleAdapter? = null

    private lateinit var binding: FragmentRecommendedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecommendedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firestore = FirebaseFirestore.getInstance()
        query = firestore.collection("article").orderBy("id")
        query?.let {
            adapter = object : ArticleAdapter(it, this@RecommendedFragment) {
                // TODO:

                override fun onDataChanged() {
                    if (itemCount == 0) {
                        binding.rvArticle.visibility = View.GONE
                    } else {
                        binding.rvArticle.visibility = View.VISIBLE
                    }

                }
            }
            binding.rvArticle.adapter = adapter
        }

        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onArticleSelected(article: DocumentSnapshot) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ARTICLE, article.id)
        startActivity(intent)
    }
}