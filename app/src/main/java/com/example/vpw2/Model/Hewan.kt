package com.example.vpw2.Model

import android.os.Parcel
import android.os.Parcelable

open class Hewan(var name: String?, var radiochecked: String?, var usia: String?, var species: String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    var imageUri: String = ""


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(radiochecked)
        parcel.writeString(usia)
        parcel.writeString(species)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hewan> {
        override fun createFromParcel(parcel: Parcel): Hewan {
            return Hewan(parcel)
        }

        override fun newArray(size: Int): Array<Hewan?> {
            return arrayOfNulls(size)
        }
    }

    open fun eat(rumput: Rumput): String {
        return ""
    }

    open fun eat(biji: Biji): String {
        return ""
    }

    open fun sound(): String {
        return ""
    }
}

