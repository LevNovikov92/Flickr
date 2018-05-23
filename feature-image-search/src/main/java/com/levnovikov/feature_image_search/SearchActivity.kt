package com.levnovikov.feature_image_search

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_image_search.di.ImageSearchComponent
import com.levnovikov.feature_image_search.di.ImageSearchDependencies


private const val SEARCH_SCREEN_STATE = "SEARCH_SCREEN_STATE"

class SearchActivity : AppCompatActivity(), ImageSearchView {

    private lateinit var presenter: ImageSearchPresenter

    private val layoutManager = GridLayoutManager(this, 3)

    private lateinit var recycler: RecyclerView
    private lateinit var searchField: EditText
    private lateinit var progress: View
    private lateinit var searchHint: View
    private lateinit var inputManager: InputMethodManager
    private lateinit var adapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViews()
        setupDI(getSearchScreenState(savedInstanceState))
        setupUI()
        presenter.onGetActive()
    }

    private fun getSearchScreenState(savedInstanceState: Bundle?): SearchScreenState =
            savedInstanceState?.getParcelable(SEARCH_SCREEN_STATE) ?: SearchScreenState("")

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SEARCH_SCREEN_STATE, presenter.getState())
        super.onSaveInstanceState(outState)
    }

    private fun initViews() {
        recycler = findViewById(R.id.recycler_view)
        searchField = findViewById(R.id.search_field)
        progress = findViewById(R.id.progress)
        searchHint = findViewById(R.id.search_hint)

        inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun setupDI(state: SearchScreenState) {
        application?.getComponent<ImageSearchDependencies>()?.let { dependencies ->
            ImageSearchComponent(this, dependencies, state).let {
                presenter = it.getPresenter()
                adapter = it.getAdapter()
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
        recycler.adapter = adapter

        findViewById<View>(R.id.search_button)
                .setOnClickListener {
                    presenter.onSearchClick(searchField.text?.toString() ?: "")
                    inputManager.hideSoftInputFromWindow(searchField.windowToken, 0)
                }
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

    override fun showErrorToast(message: String?) {
        Toast.makeText(this, message ?: getString(R.string.loading_error), Toast.LENGTH_SHORT).show()
    }
}
