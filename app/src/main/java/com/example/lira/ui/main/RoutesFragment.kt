package com.example.lira.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lira.R
import com.example.lira.data.Route
import com.example.lira.viewmodel.DriverViewModel
import com.example.lira.viewmodel.DriverViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoutesFragment: Fragment() {

    companion object {
        fun newInstance(id: String,name: String) = RoutesFragment().apply {
            this.arguments = Bundle().apply {
                this.putString("id",id)
                this.putString("name",name)
            }
        }
    }

    private lateinit var viewModel : DriverViewModel
    private lateinit var viewModelFactory: DriverViewModelFactory
    private lateinit var driverId: String
    private lateinit var driverName: String
    private lateinit var tvDriver: TextView
    private lateinit var tvName: TextView
    private lateinit var tvType: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = DriverViewModelFactory(activity?.application!!)
        viewModel = ViewModelProvider(this,viewModelFactory)[DriverViewModel::class.java]
        driverId = arguments?.getString("id")?:""
        driverName = arguments?.getString("name")?:""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDriver = view.findViewById(R.id.route_driver)
        tvName = view.findViewById(R.id.route_name)
        tvType = view.findViewById(R.id.route_type)
        tvDriver.text = driverName
        getDriverRoute()
    }

    private fun getDriverRoute() {
        lifecycleScope.launch(Dispatchers.IO) {
            val routes = viewModel.getAllRoutes()
            var targetRoute: Route? = getRouteByDriverID(routes)
            if (targetRoute == null) targetRoute = getRouteBy2Divisible(routes)
            if (targetRoute == null) targetRoute = getRouteBy5Divisible(routes)
            if (targetRoute == null) targetRoute = getRouteByLatLType(routes)
            withContext(Dispatchers.Main){
                tvName.text = String.format("Name: %s",targetRoute?.name?:"")
                tvType.text = String.format("Type: %s",targetRoute?.type?:"")
            }
        }
    }

    private fun getRouteByDriverID(routes: List<Route>): Route? {
        return routes.firstOrNull {
            it.id.toString() == driverId
        }
    }

    private fun getRouteBy2Divisible(routes: List<Route>): Route? {
        if (driverId.toInt() % 2 != 0)  return null
        return routes.firstOrNull {
            it.type == "R"
        }
    }

    private fun getRouteBy5Divisible(routes: List<Route>): Route? {
        if (driverId.toInt() % 5 != 0)  return null
        var isSecond = false
        routes.forEach {
            if(it.type == "C") {
                if (isSecond) return it
                isSecond =true
            }
        }
        return null
    }

    private fun getRouteByLatLType(routes: List<Route>): Route? {
        var route: Route? = null
        routes.forEach {
            if(it.type == "I") route = it
        }
        return route
    }

}