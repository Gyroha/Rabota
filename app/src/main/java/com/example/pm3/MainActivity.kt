package com.example.pm3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewAdapter(context: Context, resource:Int, array: List<Jobs>):
    ArrayAdapter<Jobs>(context,resource,array) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val Jobs = getItem(position)
        if (Jobs == null) {
            throw Exception()
        }
        val view = if (convertView!=null) {
            convertView
        } else {
            LayoutInflater.from(context).inflate(R.layout.intlist,null)
        }
        val uuidView = view.findViewById<TextView>(R.id.uuid)
        val titleView = view.findViewById<TextView>(R.id.title)
        val job_title_view = view.findViewById<TextView>(R.id.normalized_job_tittle)
        val parent_uuid_view = view.findViewById<TextView>(R.id.parent_uuid)
        uuidView.text = Jobs.uuid
        titleView.text = Jobs.title
        job_title_view.text = Jobs.normalized_job_tittle
        parent_uuid_view.text = Jobs.parent_uuid
        return view
    }
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder().
            baseUrl("http://api.dataatwork.org/v1/").
            addConverterFactory(GsonConverterFactory.create()).build()
        val list = list_jobs
        val api = retrofit.create(API::class.java)
        Thread(Runnable {
            api.jobs().enqueue(object : Callback<List<Jobs>> {
                override fun onFailure(call: Call<List<Jobs>>, t: Throwable) {
                }
                override fun onResponse(call: Call<List<Jobs>>, response: Response<List<Jobs>>) {
                    list.post {
                        list.adapter = ViewAdapter(this@MainActivity, R.layout.intlist, response.body()!!)
                    }
                }
            })
        }).start()
    }
}
