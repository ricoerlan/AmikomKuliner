package com.example.nearby_resto.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Filterable
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.adapter.DataRestoSearchAdapter
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.data.model.Value
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchFragment : Fragment() , SearchView.OnQueryTextListener {

    val TAG = SearchFragment::class.java.simpleName
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.search_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        loadData()

        layout.input_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                Log.d(TAG, "afterTextChanged: $editable")
                val recyclerView = recyclerViewRestoSearch
                var text = editable.toString()
                recyclerView.setVisibility(View.GONE)

                viewModel.searchResto(text)
                viewModel.searchData.observeForever {
                    Log.d(TAG, "onResponse: isi SearchData = $it")
                    val viewAdapter = DataRestoSearchAdapter(it)
                    recyclerView.layoutManager = LinearLayoutManager(
                        context,
                        RecyclerView.VERTICAL, false
                    )
                    recyclerView.adapter = viewAdapter
                    recyclerView.setVisibility(View.VISIBLE)
                }
            }
        })
        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

    }

    fun loadData(){
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

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        MenuInflater(context).inflate(R.menu.menu_search,menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionProvider(item) as SearchView
        searchView.queryHint = "Cari Nama Mahasiswa"
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

    }

//    private fun filter(text: String) {
//        //new array list that will hold the filtered data
//        val filterdNames: ArrayList<DataResto> = ArrayList()
//
//        viewModel.data.observe({ lifecycle }, {
//            //looping through existing elements
//            val adapter = DataRestoSearchAdapter(it)
//
//            for (s in it) {
//                //if the existing elements contains the search input
//                if (s.nama.toLowerCase().contains(text.toLowerCase())) {
//                    //adding the element to filtered list
//                    filterdNames.add(s)
//                }
//            }
//
//            //calling a method of the adapter class and passing the filtered list
//            adapter.filterList(filterdNames)
//        })
//    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val recyclerView = recyclerViewRestoSearch
        recyclerView.setVisibility(View.GONE)

        Log.d(TAG, "afterTextChanged: $newText")
        var text = newText.toString()

        viewModel.searchResto(text)
        viewModel.searchData.observeForever {
            Log.d(TAG, "onResponse: isi SearchData = $it")
            val viewAdapter = DataRestoSearchAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL, false
            )
            recyclerView.adapter = viewAdapter
            recyclerView.setVisibility(View.VISIBLE)
        }

        return true
    }

}
