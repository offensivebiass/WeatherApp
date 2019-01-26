package com.example.alex.wheaterapp

import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var DB: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DB = DataBase(this)

        convertBtn.setOnClickListener {
            if (!valueET.text.isEmpty()) {
                if (!isConnected()) {
                    Toast.makeText(this, "No hay conección a internet", Toast.LENGTH_SHORT).show()
                } else {
                    progress.visibility = View.VISIBLE
                    SoapAsync().execute(valueET.text.toString())
                }
            } else {
                valueET.error = "Ingrese un  valor a convertir"
            }
        }

    }

    inner class SoapAsync : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String {
            val response = WebServiceCall().callApi(params[0])
            Log.v("response", "response :" + response)
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.v("responsePost", "OnPostExecute: " + result)
            try {
                progress.visibility = View.INVISIBLE
                resultTV.text = result + " °F"
                resultTV.visibility = View.VISIBLE
                saveToDB(valueET.text.toString(), result!!)
            } catch (e: Exception) {
                progress.visibility = View.INVISIBLE
                e.printStackTrace()
            }

        }
    }

    private fun saveToDB(celcius: String, farenheit: String) {
        val current = Calendar.getInstance().time
        val record = Record(celcius, farenheit, current.toString())
        DB?.addRecord(record)
        Log.d("Records", DB?.getAllUsers())
    }

    fun isConnected(): Boolean {
        val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}
