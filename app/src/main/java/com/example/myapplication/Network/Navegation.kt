package com.example.myapplication.Network

import androidx.fragment.app.Fragment

interface Navegation {

    fun navegateTo(fragment: Fragment, addTobackStack:Boolean)

}