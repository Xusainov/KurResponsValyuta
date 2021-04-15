package com.example.kursresponsvalyuta.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.Interface.Data
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.adapter.FirthAdapter
import com.example.kursresponsvalyuta.model.ValyutaRespons
import com.example.kursresponsvalyuta.model.ValyutaResponsItem
import com.example.kursresponsvalyuta.network.ApiCliant
import com.example.kursresponsvalyuta.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirthFragment :  Fragment(R.layout.fragment_firth) ,Data{


    lateinit var navController: NavController
    lateinit var recyclerView: RecyclerView
    private lateinit var listret: ArrayList<ValyutaResponsItem>
    lateinit var toolbarHome: Toolbar


    lateinit var api: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        toolbarHome = view.findViewById(R.id.ToolBar)
        toolbarHome.title = getString(R.string.app_name)

        navController = Navigation.findNavController(context as Activity, R.id.frag_oyna)
        listret = ArrayList()

        api = ApiCliant.retrofit!!.create(ApiInterface::class.java)
        api.getAllData().enqueue(object : Callback<ValyutaRespons> {
            override fun onResponse(call: Call<ValyutaRespons>, response: Response<ValyutaRespons>) {
                if (response.code() == 200) {
                    listret.addAll(response.body()!!)
                    Log.d("SSSS", "onResponse: ${listret.size}")
                    recyclerView.adapter = FirthAdapter(listret, this@FirthFragment)
                }
            }

            override fun onFailure(call: Call<ValyutaRespons>, t: Throwable) {

                Toast.makeText(view.context, "\n" +
                        "you are not connected to the internet", Toast.LENGTH_SHORT).show()

            }
        })
        recyclerView = view.findViewById(R.id.firthRecycler)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

    }

    override fun getDataa(pos: ValyutaResponsItem) {
        val args = Bundle()
        args.putString("rub", pos.Ccy)
        args.putString("date", pos.Date)
        args.putString("qiymat", pos.Rate)
        args.putString("rubnomi", pos.CcyNm_UZ)
        arguments = args
        navController.navigate(R.id.secondFragment, arguments)
    }
}