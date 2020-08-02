package com.example.nearby_resto.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import kotlinx.android.synthetic.main.main_fragment.view.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = inflater.inflate(R.layout.main_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.data.observeForever {
            val adapter = DataRestoAdapter(it)
            val recyclerView = binding.recyclerViewResto
            val recyclerView2 = binding.recyclerViewResto2

            if (it.isNotEmpty()){
                recyclerView.layoutManager = LinearLayoutManager(context,
                    RecyclerView.HORIZONTAL,false)
                recyclerView.adapter = adapter

                recyclerView2.layoutManager = LinearLayoutManager(context,
                    RecyclerView.HORIZONTAL,false)
                recyclerView2.adapter = adapter

            } else{
                viewModel.response.observeForever{
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }

}