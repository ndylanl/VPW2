package com.example.vpw2.Database

import com.example.vpw2.Model.Hewan

class GlobalVar {

    companion object{
        val STORAGE_PERMISSION_CODE: Int = 100
        val listDataHewan = ArrayList<Hewan>()
        val listFiltered = ArrayList<Hewan>()
    }

}
