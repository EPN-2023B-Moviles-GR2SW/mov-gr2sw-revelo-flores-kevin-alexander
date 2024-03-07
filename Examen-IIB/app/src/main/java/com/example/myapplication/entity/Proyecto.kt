package com.example.myapplication.entity

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class Proyecto(

    val id: String?,
    val nombre: String?,
    val activo: Boolean?,
    val presupuesto: Double?,
    val ranking: Double?,
    val fechaInicio: Date?
): Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readBoolean(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readValue(Date::class.java.classLoader) as? Date,
        ) {
    }

    override fun toString(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        return "$nombre\nPara el ${format.format(fechaInicio)}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(activo.toString())
        parcel.writeString(fechaInicio.toString())
        parcel.writeString(presupuesto.toString())
        parcel.writeString(ranking.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Proyecto> {

        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Proyecto {
            return Proyecto(parcel)
        }

        override fun newArray(size: Int): Array<Proyecto?> {
            return arrayOfNulls(size)
        }
    }
}