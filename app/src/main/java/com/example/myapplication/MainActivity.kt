package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myapplication.Network.ApiService
import com.example.myapplication.Network.Mascota
import com.example.myapplication.Network.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var imagen:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val img=findViewById<ImageView>(R.id.imageView)
        val btn=findViewById<Button>(R.id.btnObtenerMascota)
        val redirigir=findViewById<Button>(R.id.redirigir)


        btn.setOnClickListener {
            val service= retrofit.create(ApiService::class.java).getMascota()
            //Log.e("datos",data.request().toString())
            service.enqueue(object:Callback<Mascota>{
                override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                    var mascota:Mascota
                    if(response.isSuccessful){
                        mascota=response.body()!!
                        Log.e("mascota",mascota.message)
                        imagen=mascota.message.toString()
                        Glide.with(img.context).load(imagen).into(img)

                    }
                }

                override fun onFailure(call: Call<Mascota>, t: Throwable) {
                    Log.e("incorrecto", t.message.toString())
                }
            })
        }
        
        redirigir.setOnClickListener { 
            navegarActivity()
        }

    }

    private fun navegarActivity() {
        val intent=Intent(this,DetalleActivity::class.java)
        startActivity(intent)        
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

}