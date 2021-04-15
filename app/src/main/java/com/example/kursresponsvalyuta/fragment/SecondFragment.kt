package com.example.kursresponsvalyuta.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kursresponsvalyuta.Interface.Data
import com.example.kursresponsvalyuta.R
import com.example.kursresponsvalyuta.adapter.SecondAdapter
import com.example.kursresponsvalyuta.model.ValyutaRespons
import com.example.kursresponsvalyuta.model.ValyutaResponsItem
import com.example.kursresponsvalyuta.network.ApiCliant
import com.example.kursresponsvalyuta.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class SecondFragment : Fragment(R.layout.fragment_second) {

    lateinit var recyclerView: RecyclerView
    lateinit var api: ApiInterface

    lateinit var list: ArrayList<ValyutaResponsItem>
    lateinit var listss: ArrayList<ValyutaResponsItem>


    lateinit var rub: String
    lateinit var rubQiymat: String
    lateinit var rubNomi: String
    var tekshir: Boolean = false


    lateinit var EditTxt: EditText
    lateinit var TxtRub: TextView
    lateinit var TxtnaSum: TextView
    lateinit var TxtSum: TextView
    lateinit var ImageReplase: ImageView


    lateinit var toolbarCon: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arrayListOf()
        listss = arrayListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarCon = view.findViewById(R.id.secondToolBAR)
        api = ApiCliant.retrofit!!.create(ApiInterface::class.java)



        recyclerView = view.findViewById(R.id.secondRecycler)
        EditTxt = view.findViewById(R.id.edit_txt)
        TxtRub = view.findViewById(R.id.text_rub)
        TxtSum = view.findViewById(R.id.som_txt)
        TxtnaSum = view.findViewById(R.id.text)
        ImageReplase = view.findViewById(R.id.image)


        val dat = arguments?.getString("date")
        rub = arguments?.getString("rub").toString()
        rubQiymat = arguments?.getString("qiymat").toString()
        rubNomi = arguments?.getString("rubnomi").toString()
        val date = dat?.let { getDateFarmat(it) }

        toolbarCon.title = rubNomi

        toolbarCon.title = rubNomi

        EditTxt.setText("1")
        TxtSum.text = rubQiymat
        TxtRub.text = rubNomi
        if (tekshir) {
            TxtRub.text = getString(R.string.uzbeksom)
            TxtnaSum.text = rubNomi
        } else {
            TxtnaSum.text = getString(R.string.uzbeksom)
            TxtRub.text = rubNomi
        }
        ImageReplase.setOnClickListener {
            if (!tekshir) {
                TxtRub.text = getString(R.string.uzbeksom)
                TxtnaSum.text = rubNomi
            } else {
                TxtnaSum.text = getString(R.string.uzbeksom)
                TxtRub.text = rubNomi
            }
            EditTxt.setText("0")
            TxtSum.text = "0"
            tekshir = !tekshir
        }


        EditTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (tekshir.equals(false)) {
                    rubnasum(start + count, s)
                } else {
                    sumnarub(start + count, s)
                }
            }
        })


        date?.let { aa(it) }
        val data = SimpleDateFormat("dd.MM.yyyy")
        for (i in 0..list.size - 1) {
            for (j in 0..list.size - 1) {
                if (data.parse(list[i].Date).time > data.parse(list[j].Date).time) {
                    val temp = list[j]
                    list[j] = list[i]
                    list[i] = temp
                }
            }
        }
        setRecycler()
    }

    private fun sumnarub(start: Int, s: CharSequence) {
        if (!start.equals(0)) {
            val temp = s.toString()
            val temppp = temp.toDouble()
            val rub = temppp / (rubQiymat.toDouble())
            val natija = (rub * 100).toLong()
            val n = (natija.toDouble() / 100)


            val valueWithoutEpsilon = n.toString().toBigDecimal()
            TxtSum.text = valueWithoutEpsilon.toPlainString()
        } else {
            TxtSum.text = "0"
        }
    }

    private fun rubnasum(start: Int, s: CharSequence) {
        if (!start.equals(0)) {

            val temp = s.toString()
            val temppp = temp.toFloat()

            val rub = (temppp * ((rubQiymat.toFloat()) * 100.toLong())) / 100


            val valueWithoutEpsilon = rub.toString().toBigDecimal()

            TxtSum.text = valueWithoutEpsilon.toPlainString()


        } else {
            TxtSum.text = "0"
        }
    }

    fun ListShowAdd() {
        if (!list.get(list.size - 1).equals(null)) {
            recyclerView.adapter?.notifyItemInserted(list.size - 1)
        }
    }


    fun setRecycler() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = SecondAdapter(list, this@SecondFragment)

        }
    }


    fun aa(date: String) {
        api.getHomeRubOneDay(rub, date).enqueue(object : Callback<ValyutaRespons> {
            override fun onFailure(call: Call<ValyutaRespons>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ValyutaRespons>,
                response: Response<ValyutaRespons>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    val temp: ValyutaRespons = response.body()!!
                    val ttemp: ValyutaResponsItem = temp[0]

                    if (ttemp.Date.substring(6, 10).toInt() != 2009) {
                        list.add(ttemp)
                        ListShowAdd()
                    }
                    getDate(temp.get(0).Date)
                }
            }
        })
    }

    fun getDate(date: String) {
        //  2020-12-36 return

        //05.01.2021 date
        val day = date.substring(0..1).toInt()
        val moon = date.substring(3..4)

        val year = date.substring(6..9)

        if ((day - 1) >= 0 && year.toInt() != 2009) {
            val dayRet = year + "-" + moon + "-" + (day - 1)
            aa(dayRet)
        }
    }

    fun getDateFarmat(date: String): String {
        //  2020-12-36 return

        //05.01.2021 date
        val day = date.substring(0..1).toInt()
        val moon = date.substring(3..4)
        val year = date.substring(6..9)

        val dayRet = year + "-" + moon + "-" + day
        return dayRet
    }


}