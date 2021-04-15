package com.example.kursresponsvalyuta.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.example.kursresponsvalyuta.Interface.Data
import com.example.kursresponsvalyuta.network.ApiCliant
import com.example.kursresponsvalyuta.network.ApiInterface


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T


abstract class BaseFragment<VB : ViewBinding>(var inflate: Inflate<VB>) : Fragment() {

    //view binding
    private var _binding: VB? = null
    val binding get() = _binding!!

    //navigation component
    var navController: NavController? = null

    //retrofit
    lateinit var apiInterface: ApiInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        apiInterface = ApiCliant.retrofit!!.create(ApiInterface::class.java)
        _binding = inflate.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreate()
    }

    abstract fun onViewCreate()

}