package com.example.kursresponsvalyuta.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.adapter.AdaprerCalendar
import com.example.kursresponsvalyuta.model.ValyutaRespons
import com.example.kursresponsvalyuta.model.ValyutaResponsItem
import com.example.kursresponsvalyuta.network.ApiCliant
import com.example.kursresponsvalyuta.network.ApiInterface
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    lateinit var api: ApiInterface
    lateinit var recyclerView: RecyclerView

    lateinit var list: ArrayList<ValyutaResponsItem>
    lateinit var frameLayout: FrameLayout
    lateinit var calendar: Calendar
    lateinit var toolbarCalendar: Toolbar


    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        api = ApiCliant.retrofit!!.create(ApiInterface::class.java)
        progress = view.findViewById(R.id.progres_calendar)
        recyclerView = view.findViewById(R.id.RecyclerKalendar)
        toolbarCalendar = view.findViewById(R.id.toolbar_kalendar)


        frameLayout = view.findViewById<FrameLayout>(R.id.sheet)

        calendar = Calendar.getInstance()
        BottomSheetBehavior.from(frameLayout).apply {
            this.peekHeight = 170
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val date = SimpleDateFormat("dd-MM-yyyy").parse("05-01-2010")
        val calendarView = frameLayout.get(1) as CalendarView
        calendarView.maxDate = calendar.timeInMillis
        calendarView.minDate = date.time



        api.getAllData().enqueue(object : Callback<ValyutaRespons> {
            override fun onResponse(
                call: Call<ValyutaRespons>,
                response: Response<ValyutaRespons>
            ) {
                if (response.code() == 200) {
                    list = ArrayList()
                    list.addAll(response.body()!!)
                    Log.d("BBBB", "onResponse: $list")
                    recyclerView.adapter = AdaprerCalendar(list)
                    getListDav(list)
                }
            }

            override fun onFailure(call: Call<ValyutaRespons>, t: Throwable) {
                Toast.makeText(view.context, "\n" +
                        "you are not connected to the internet", Toast.LENGTH_SHORT).show()
            }
        })

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            recyclerView.visibility = View.GONE
            progress.visibility = View.VISIBLE


            val mon: String
            if (month < 10) {
                mon = "0" + (month + 1)
            } else {
                mon = (month + 1).toString()
            }
            if (NetworkOn()) {
                setApi("$year-$mon-$dayOfMonth")
            } else {
                Toast.makeText(view.context, "\n" +
                        "you are not connected to the internet", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun getListDav(list: List<ValyutaResponsItem>) {
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
        if (!list.size.equals(0)) {
            toolbarCalendar.title = list.get(0).Date
        }
    }

    fun setApi(date: String) {
        api.getAllDavlatListData(date).enqueue(object : Callback<ValyutaRespons> {
            override fun onResponse(
                call: Call<ValyutaRespons>,
                response: Response<ValyutaRespons>
            ) {
                if (response.isSuccessful && response.code() == 200) {

                    list = ArrayList()
                    list.addAll(response.body()!!)
                    Log.d("BBBB", "onResponse: $list")
                    recyclerView.adapter = AdaprerCalendar(list)

                    getListDav(list)
                    recyclerView.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ValyutaRespons>, t: Throwable) {
                Toast.makeText(view!!.context, "\n" +
                        "you are not connected to the internet", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun NetworkOn(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = cm!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

}