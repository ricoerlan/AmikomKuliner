package com.example.nearby_resto.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import com.example.nearby_resto.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.view.*

class CallFragment : Fragment() {

    companion object {
        fun newInstance() = CallFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var restoRepo: RestoRepository
    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = inflater.inflate(R.layout.main_fragment, container, false)

        restoRepo = ViewModelProviders.of(this).get(RestoRepository::class.java)

        restoRepo.data.observe({ lifecycle }, {
            val adapter = DataRestoAdapter(it)
            val recyclerView = layout.recyclerViewResto

            if (it.isNotEmpty()){

                recyclerView.layoutManager = LinearLayoutManager(context,
                    RecyclerView.HORIZONTAL,false)
                    recyclerView.adapter = adapter

            }
                else{
                Toast.makeText(activity ,"Data Kosong",Toast.LENGTH_LONG).show()

            }


        })

        restoRepo.response.observe({ lifecycle }, {
            if (it.isNotEmpty()) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    }

}