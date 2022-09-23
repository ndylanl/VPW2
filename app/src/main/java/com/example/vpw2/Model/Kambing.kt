package com.example.vpw2.Model

class Kambing(name: String?, radiochecked: String?, usia: String?, species: String = "Kambing") : Hewan(name, radiochecked, usia, species) {

    override fun eat(rumput: Rumput): String {
        return "Muangan rumput"
    }

    override fun sound(): String {
        return "MbekMbekMbek"
    }
}