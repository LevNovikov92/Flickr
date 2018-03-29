package com.levnovikov.feature_image_search.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_image_search.R
import com.levnovikov.feature_image_search.di.ImageSearchComponent
import com.levnovikov.feature_image_search.di.ImageSearchDependencies

class SearchActivity : AppCompatActivity(), ImageSearchView {

    private lateinit var presenter: ImageSearchPresenter

    private val layoutManager = GridLayoutManager(this, 3)

    private lateinit var recycler: RecyclerView
    private lateinit var searchField: EditText
    private lateinit var progress: View
    private lateinit var searchHint: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        setupDI()
        setupUI()
    }

    private fun initViews() {
        recycler = findViewById(R.id.recycler_view)
        searchField = findViewById(R.id.search_field)
        progress = findViewById(R.id.progress)
        searchHint = findViewById(R.id.search_hint)
    }

    private fun setupDI() {
        application?.getComponent<ImageSearchDependencies>()?.let { dependencies ->
            ImageSearchComponent(this, dependencies).let {
                presenter = it.getPresenter()
            }
        }
    }

    private fun setupUI() {
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.onScrolled()
            }
        })
        recycler.adapter = presenter.getAdapter()

        findViewById<View>(R.id.search_button)
                .setOnClickListener { presenter.onSearchClick(searchField.text?.toString() ?: "") }
    }

    override fun getLastVisibleItemPosition(): Int =
            layoutManager.findLastVisibleItemPosition()

    override fun showProgress() {
        recycler.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
        searchHint.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        recycler.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
        searchHint.visibility = View.INVISIBLE
    }

    override fun showHintToast() {
        Toast.makeText(this, getString(R.string.search_input_hint), Toast.LENGTH_SHORT).show()
    }
}
