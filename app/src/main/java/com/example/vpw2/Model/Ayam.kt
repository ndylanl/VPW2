package com.example.vpw2.Model

class Ayam(name: String?, radiochecked: String?, usia: String?, species: String = "Chicken") : Hewan(name, radiochecked, usia, species) {

    override fun eat(biji: Biji): String {
        return "Muangan Buiji"
    }

    override fun sound(): String {
        return "Pok Pok Pok"
    }
}