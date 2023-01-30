package com.equitel.pruebaequitel

data class AplicacionOrdenTrabajo (
    var nit: String = "", var cliente : String = "", var direccion: String= "", var marcaPlanta : String? = "", var potencia : String? = "",
    var modeloPlanta : String? = "", var serialPlanta : String? = "", var serialMotor : String= "", var modeloMotor : String? = "", var marcaMotor :String? = "",
    var cpl :String? = "", var ubicFisica: String? = "", var combustible : String? = "", var ciudad: String? = "", var serialGenerador : String? = "",
    var modeloGenerador : String? = "", var marcaGenerador : String? = "", var ot : String? = "", var coordinador : String? = "", var contrato : String? = "",
    var maqHorometro: String = "", var compHorometro: String = "", var ortHorometro: String = "", var ortHorometroreal: String = "", var nombrePlanta: String = "",)