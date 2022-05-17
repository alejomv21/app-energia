package com.equitel.pruebaequitel

import androidx.room.Entity
import androidx.room.PrimaryKey



data class SerialPlanta (
    var tipoUbicacion: String = "", var tipoEquipo : String = "", var cliente: String= "", var dir : String? = "", var MarcaMotor : String? = "", var marcaGen : String? = "", var marcaPlnata : String? = "",
    var ciudad : String= "", var modMotor : String? = "", var modGen :String? = "", var modPlanta :String? = "", var snMotor: String? = "", var snGen : String? = "",
    var snPlanta: String? = "", var cpl : String? = "", var kw : String? = "", var spec : String? = "", var tipoControl : String? = "",
    var tecnicosCargo : String? = "", var promotionID : String? = "", var motivoVisita: String = "")

