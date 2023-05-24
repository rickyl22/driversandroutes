package com.example.lira.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lira.R
import com.example.lira.adapter.DriverAdapter
import com.example.lira.data.Driver
import com.example.lira.viewmodel.DriverViewModel
import com.example.lira.viewmodel.DriverViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel : DriverViewModel
    private lateinit var viewModelFactory: DriverViewModelFactory
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: DriverAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DriverViewModelFactory(activity?.application!!)
        viewModel = ViewModelProvider(this,viewModelFactory)[DriverViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview = view.findViewById(R.id.recyclerview)
        adapter = DriverAdapter(DriverAdapter.DriverOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, RoutesFragment.newInstance(it.id,it.name))
                ?.addToBackStack(null)
                ?.commit()
        }, ArrayList())
        val layoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter
        lifecycleScope.launch (Dispatchers.IO){
            if (viewModel.getAllDrivers().isEmpty()) getDriversFromServer()
            else {
                adapter.driverList = viewModel.getAllDrivers()
                withContext(Dispatchers.Main){
                    adapter.notifyDataSetChanged()
                }
            }
        }
        val button = view.findViewById<TextView>(R.id.bt_sort)
        button.setOnClickListener {
            val sortedList = adapter.driverList.sortedWith(object : Comparator <Driver> {
                override fun compare (d0: Driver, d1: Driver) : Int {
                    if (d0.name > d1.name) {
                        return 1
                    }
                    if (d0.name == d1.name) {
                        return 0
                    }
                    return -1
                }
            })
            adapter.driverList = sortedList
            adapter.notifyDataSetChanged()
        }
    }

    private fun getDriversFromServer() {
        viewModel.getList()
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.viewState.collectLatest{
                it.drivers.forEach { driver ->
                    viewModel.insert(driver)
                }
                it.routes.forEach { route ->
                    viewModel.insert(route)
                }
                adapter.driverList = viewModel.getAllDrivers()
                withContext(Dispatchers.Main){
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

}