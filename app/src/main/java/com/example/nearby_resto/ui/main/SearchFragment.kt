package com.example.nearby_resto.ui.main

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.adapter.DataRestoSearchAdapter
import kotlinx.android.synthetic.main.search_fragment.*


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private val adapter: DataRestoSearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.search_fragment, container, false)


        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.data.observe({ lifecycle }, {
            val adapter = DataRestoSearchAdapter(it)
            val recyclerView = recyclerViewRestoSearch

            if (it.isNotEmpty()){
                recyclerView.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)
                recyclerView.adapter = adapter

            }
            else{
                Toast.makeText(activity ,"Data Kosong", Toast.LENGTH_LONG).show()

            }

            searchView.imeOptions = EditorInfo.IME_ACTION_DONE
            searchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })

        })

        viewModel.response.observe({ lifecycle }, {
            if (it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })
    }
}
