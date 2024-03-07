package com.example.myapplication.entity

import android.os.Parcel
import android.os.Parcelable

class Estudiante(

    val id: Int?,
    var nombre: String?,
    var area: String?,
    var habilidad: String?,
    var gpa: Double?,
    val proyectoId: Int?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return nombre!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(nombre)
        parcel.writeString(area)
        parcel.writeString(habilidad)
        parcel.writeString(gpa.toString())
        parcel.writeString(proyectoId.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }
}