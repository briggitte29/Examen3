package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.Network.Navegation

class DetalleActivity : AppCompatActivity(),Navegation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.contenedor,PerfilFragment())
                .commit()
        }

    }

    override fun navegateTo(fragment: Fragment, addTobackStack: Boolean) {
        val transaccion=supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenedor,fragment)
        if(addTobackStack){
            transaccion.addToBackStack(null)
        }
        transaccion.commit()
    }
}