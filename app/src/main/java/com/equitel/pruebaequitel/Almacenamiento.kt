package com.equitel.pruebaequitel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlmacenamientoFinal")
data class Almacenamiento (@PrimaryKey var idEquipo : String = "1", var ordenTrabajo: String? = null, var sede: String? = null, var tipoUbicacion : String? = null,
                           var tipoEquipo : String? = null, var tipoServicio : String? = null, var ciudad: String? = null, var fecha: String? = null, var cliente: String? = null,
                           var direccion : String? = null, var servicioSolicitadoPor: String? = null, var cargo : String? = null, var email: String? = null, var tel: String? = null,
                           var marcaMotor : String? = null, var marcaGenerador: String? = null, var modMotor : String? = null, var modGenerador: String? = null, var serialMotor : String? = null,
                           var clp : String? = null, var tipoBateria : String? = null, var kw: String? = null, var cantidad : String? = null, var nArranques: String? = null, var horasMotor : String? = null,
                           var horasControl :String? = null, var marcaPlanta : String? = null, var modPlanta : String? = null, var serialGenerador : String? = null,
                           var serialPlanta: String? = null, var spec : String? = null, var tecnicos : String? = null, var tipoControl: String?  = null, var promotionID: String? = null, var motivoVisita: String? = null,
                           var nivelAceite: String? = null, var nivelAceiteMedida: String? = null, var estadoRadiador: String? = null,
                           var nivelAguaRadiador: String? = null, var aspasVentilador : String? = null, var bornerBateria: String? = null, var voltajeBateria: String? = null,
                           var voltajeBateriaMedida: String? = null, var cargadorFuncional: String?= null, var cargadorFuncionaMedida: String? = null,
                           var correasTensionadas:String? = null, var estadoFiltroAire: String? = null, var estadoMangueras: String? = null, var estadoPrecalentador: String? = null,
                           var estadoRacores: String? = null, var fugas: String? = null, var estadoCombustible:String? = null, var estadoCombustibleMedida:String?= null,
                           var estadoTanque: String? = null, var estadoTanqueMedida: String? = null, var formaTanque: String? = null, var estadoGenerador: String? = null,
                           var aspasVentiladorGenerador: String? = null, var conexionPotencia: String? = null, var conexionCableadocontrol: String? = null, var objetsExtrasInterior: String? = null,
                           var puenteRectificadorGiratorio: String? = null, var estadoControl: String? = null, var estadoCuartoCabina: String? = null,
                            var caidaVOltaje : String? = null, var presionAceite: String? = null, var temperaturaAgua: String? = null, var voltajeAlternador: String? = null,  var instrumentoTablero: String? = null, var temperaturaAceite: String? = null,
                           var temperaturaGases: String? = null, var indicadorRestriccion: String? = null, var oscilacionGobernador: String? = null, var altaTemperaturaMotor: String? = null, var sobreRevoluciones: String? = null, var bajaPresionAceite: String? = null,
                           var bajoNivelRefrigerante: String? = null, var voltaje: String? = null, var corrienteAmperios: String? = null, var chequeoVolt: String? = null, var posicionInterruptor: String? = null, var switchCargador: String? = null, var switchControl: String? = null,
                           var frecuencia: String? = null, var factorPotencia: String? = null, var kilovatios: String? = null, var simularFalla: String? = null, var enVacio: String? = null, var conCargas: String? = null )