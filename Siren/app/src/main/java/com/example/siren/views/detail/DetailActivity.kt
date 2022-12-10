package com.example.siren.views.detail

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.example.siren.R
import com.example.siren.data.Article
import com.example.siren.databinding.ActivityDetailBinding
import com.github.barteksc.pdfviewer.PDFView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DetailActivity : AppCompatActivity(), EventListener<DocumentSnapshot> {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var articleRef: DocumentReference

    private var articleRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleId = intent.getStringExtra(EXTRA_ARTICLE)
        firestore = FirebaseFirestore.getInstance()

        if (articleId != null) {
            articleRef = firestore.collection("article").document(articleId)
        }
    }

    override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
        if (error != null) {
            return
        }

        value?.let {
            val article = value.toObject<Article>()
            if (article != null) {
                onArticleLoaded(article)
            }
        }
    }

    private fun onArticleLoaded(article: Article) {
        binding.tvTitle.text = article.title
//        RetrievePDFFromURL(binding.idPDFView).execute(article.contentUrl)
        Glide.with(this)
            .load(article.content)
            .into(binding.webView)
        Glide.with(this)
            .load(article.image)
            .into(binding.ivArticle)
    }

    override fun onStart() {
        super.onStart()
        articleRegistration = articleRef.addSnapshotListener(this@DetailActivity)
    }

    override fun onStop() {
        super.onStop()
        articleRegistration?.remove()
        articleRegistration = null
    }

    companion object {
        const val EXTRA_ARTICLE = "extra_article"
    }
}

class RetrievePDFFromURL(pdfView: PDFView) :
    AsyncTask<String, Void, InputStream>() {

    // on below line we are creating a variable for our pdf view.
    private val mypdfView: PDFView = pdfView

    // on below line we are calling our do in background method.
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): InputStream? {
        // on below line we are creating a variable for our input stream.
        var inputStream: InputStream? = null
        try {
            // on below line we are creating an url
            // for our url which we are passing as a string.
            val url = URL(params[0])

            // on below line we are creating our http url connection.
            val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

            // on below line we are checking if the response
            // is successful with the help of response code
            // 200 response code means response is successful
            if (urlConnection.responseCode == 200) {
                // on below line we are initializing our input stream
                // if the response is successful.
                inputStream = BufferedInputStream(urlConnection.inputStream)
            }
        }
        // on below line we are adding catch block to handle exception
        catch (e: Exception) {
            // on below line we are simply printing
            // our exception and returning null
            e.printStackTrace()
            return null
        }
        // on below line we are returning input stream.
        return inputStream
    }

    // on below line we are calling on post execute
    // method to load the url in our pdf view.
    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: InputStream?) {
        // on below line we are loading url within our
        // pdf view on below line using input stream.
        mypdfView.fromStream(result).load()

    }
}