package com.example.newsrecycler

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    val SHARED_PREFERENCES = "shared_preferences"
    val shared_position = "shared_position"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)


        view.countrSpinner.setSelection(getData(sharedPreferences))
        view.countrSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                pos = position
                getCountry(adapterView?.getItemAtPosition(position).toString())
                saveData(sharedPreferences)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        view.reset.setOnClickListener {
            pos = 0
            saveData(sharedPreferences)
            view.countrSpinner.setSelection(pos,true)
        }
        return view
    }


    private fun getCountry(countryName: String) {
        when (countryName) {
            "India" -> nat_code = "in"
            "United Arab Emirates" -> nat_code = "ae"
            "Argentina" -> nat_code = "ar"
            "Austria" -> nat_code = "at"
            "Australia" -> nat_code = "au"
            "Belguim" -> nat_code = "be"
            "Bulgaria" -> nat_code = "bg"
            "Brazil" -> nat_code = "br"
            "Canada" -> nat_code = "ca"
            "Switzerland" -> nat_code = "ch"
            "China" -> nat_code = "cn"
            "Colombia" -> nat_code = "co"
            "Cuba" -> nat_code = "cu"
            "Czechia" -> nat_code = "cz"
            "Germany" -> nat_code = "de"
            "Egypt" -> nat_code = "eg"
            "France" -> nat_code = "fr"
            "United Kingdom" -> nat_code = "gb"
            "Greece" -> nat_code = "gr"
            "Hong Kong" -> nat_code = "hk"
            "Hungary" -> nat_code = "hu"
            "Indonesia" -> nat_code = "id"
            "Ireland" -> nat_code = "ie"
            "Israel" -> nat_code = "il"
            "Italy" -> nat_code = "it"
            "Japan" -> nat_code = "jp"
            "Korea" -> nat_code = "kr"
            "Lithuania" -> nat_code = "lt"
            "Latvia" -> nat_code = "lv"
            "Morocco" -> nat_code = "ma"
            "Mexico" -> nat_code = "mx"
            "Malaysia" -> nat_code = "my"
            "Nigeria" -> nat_code = "ng"
            "Netherlands" -> nat_code = "nl"
            "Norway" -> nat_code = "no"
            "New Zealand" -> nat_code = "nz"
            "Philippines" -> nat_code = "ph"
            "Poland" -> nat_code = "pl"
            "Portugal" -> nat_code = "pt"
            "Romania" -> nat_code = "ro"
            "Serbia" -> nat_code = "rs"
            "Russia" -> nat_code = "ru"
            "Saudi Arabia" -> nat_code = "sa"
            "Sweden" -> nat_code = "se"
            "Singapore" -> nat_code = "sg"
            "Slovenia" -> nat_code = "si"
            "Slovakia" -> nat_code = "sk"
            "Thailand" -> nat_code = "th"
            "Turkey" -> nat_code = "tr"
            "Taiwan" -> nat_code = "tw"
            "Ukraine" -> nat_code = "ua"
            "United States" -> nat_code = "us"
            "Venezuela" -> nat_code = "ve"
            "South Africa" -> nat_code = "za"
        }
    }

    private fun getLanguage(language: String) {
        when (language) {
            "English - en" -> lang_code = "en"
            "Spanish - es" -> lang_code = "es"
            "French - fr" -> lang_code = "fr"
            "Italian - it" -> lang_code = "it"
            "Russian - ru" -> lang_code = "ru"
            "Arabic - ar" -> lang_code = "ar"
        }
    }

    fun saveData(sharedPreferences: SharedPreferences?) {
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putInt(shared_position, pos)
            putString("sharedCountry", nat_code)
            apply()
        }
    }

    fun getData(sharedPreferences: SharedPreferences?): Int {
        val position = sharedPreferences?.getInt(shared_position, 0)
        if (position == null) {
            return pos
        } else
            return position
    }



    companion object {
        var nat_code: String = "in"
        var lang_code: String = "en"
        var pos : Int = 0
    }

}