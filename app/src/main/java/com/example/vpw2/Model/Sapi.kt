package com.example.vpw2.Model

class Sapi(name: String?, radiochecked: String?, usia: String?, species: String = "Sapi") : Hewan(name, radiochecked, usia, species) {

    override fun eat(rumput: Rumput): String {
        return "Muangan rumput"
    }

    override fun sound(): String {
        return "MooMooMoo"
    }
}