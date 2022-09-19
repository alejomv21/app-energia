package com.equitel.pruebaequitel

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "AlmacenamientoFinal")
data class Almacenamiento (@PrimaryKey (autoGenerate = true) var id : Int? = null, var idEquipo : String = "1", var ordenTrabajo: String? = null, var sede: String? = null, var tipoUbicacion : String? = null,
                           var tipoEquipo : String? = null, var tipoServicio : String? = null, var ciudad: String? = null, var fecha: String? = null, var cliente: String? = null,
                           var direccion : String? = null, var servicioSolicitadoPor: String? = null, var cargo : String? = null, var email: String? = null, var tel: String? = null,
                           var marcaMotor : String? = null, var marcaGenerador: String? = null, var modMotor : String? = null, var modGenerador: String? = null, var serialMotor : String? = null,
                           var clp : String? = null, var tipoBateria : String? = null, var kw: String? = null, var cantidad : String? = null, var nArranques: String? = null, var horasMotor : String? = null,
                           var horasControl :String? = null, var marcaPlanta : String? = null, var modPlanta : String? = null, var serialGenerador : String? = null,
                           var serialPlanta: String? = null, var spec : String? = null, var tecnicos : String? = null, var tipoControl: String?  = null, var promotionID: String? = null, var motivoVisita: String? = null,
                           var nivelAceite: String? = null, var nivelAceiteMedida: String? = null, var estadoRadiador: String? = null,
                           var nivelAguaRadiador: String? = null, var aspasVentilador : String? = null, var bornerBateria: String? = null, var nivelAguaBaterias: String? = null, var densidadElectrolitos: String? = null,
                           var voltajeBateria: String? = null,
                           var voltajeBateriaMedida: String? = null, var cargadorFuncional: String?= null, var cargadorFuncionaMedida: String? = null,
                           var correasTensionadas:String? = null, var estadoFiltroAire: String? = null, var estadoMangueras: String? = null,
                           var estadoTapaRadiador: String? = null, var presionTapaRadiador: String? = null, var dimensiones: String? = null,
                           var estadoRacores: String? = null, var fugas: String? = null, var estadoCombustible:String? = null, var estadoCombustibleMedida:String?= null,
                           var estadoTanque: String? = null, var estadoTanqueMedida: String? = null, var formaTanque: String? = null, var estadoGenerador: String? = null,
                           var aspasVentiladorGenerador: String? = null, var conexionPotencia: String? = null, var conexionCableadocontrol: String? = null, var objetsExtrasInterior: String? = null,
                           var puenteRectificadorGiratorio: String? = null, var estadoControl: String? = null, var estadoCuartoCabina: String? = null,
                           var marcaATS: String? = null, var modelosATS: String? = null, var tipoDisyuntores: String? = null, var funcionamientoATS: String? = null, var poseePrecalentador: String? = null, var modeloPrecalentador: String? = null,
                           var voltajeOperacion: String? = null, var estadoPrecalentadorATS: String? = null, var estadoManguerasATS: String? = null, var trabajoRealizado: String? = null,
                           var caidaVOltaje : String? = null, var caidaVoltajeMedida : String? = null, var presionAceite: String? = null, var presionAceiteMedida : String? = null, var temperaturaAgua: String? = null, var temperaturaAguaMedida : String? = null, var voltajeAlternador: String? = null, var voltajeAlternadorMedida : String? = null,
                           var temperaturaAceite: String? = null, var temperaturaAceiteMedida : String? = null,  var temperaturaGases: String? = null, var temperaturaGasesMedida : String? = null,var indicadorRestriccion: String? = null, var indicadoresRestriccionMedida : String? = null,
                           var oscilacionVelocidad: String? = null, var altaTemperaturaMotor: String? = null, var sobreRevoluciones: String? = null, var bajaPresionAceite: String? = null,
                           var bajoNivelRefrigerante: String? = null, var voltaje1: Boolean = false, var voltaje1Medida: String? = null,  var voltaje2: Boolean = false, var voltaje2Medida: String? = null,var voltaje3: Boolean = false, var voltaje3Medida: String? = null,
                           var corrienteAmperios1: Boolean = false, var corrienteAmperios1Medida: String? = null, var corrienteAmperios2: Boolean = false, var corrienteAmperios2Medida: String? = null, var corrienteAmperios3: Boolean = false, var corrienteAmperios3Medida: String? = null,
                           var corrienteAmperios4: Boolean = false, var corrienteAmperios4Medida: String? = null, var posicionInterruptor: String? = null, var switchCargador: String? = null, var switchControl: String? = null,
                           var frecuencia: String? = null, var frecuenciaMedida: String? = null, var factorPotencia: String? = null, var factorPotenciaMedida: String? = null, var kilovatios: String? = null, var kilovatiosMedida: String? = null, var enVacio: String? = null, var conCargas: String? = null, var conCargasMedida: String? = null,
                           var recomendaciones: String? = null,
                           var fechaSalidaTecnico : String? = null, var horaSalidaTecnico : String? = null, var fechaSalidaCliente : String? = null, var horaSalidaCliente : String? = null, var fechaLLegadaCliente : String? = null, var horaLlegadaCliente : String? = null,
                           var fechaAtencionCliente : String? = null, var horaAtencionCliente : String? = null,var calificacionClienteServicio : String? = null,
                           var calificacionClienteOrden : String? = null, var calificacionClienteElementos : String? = null, var estadoConexion : String? = null, var sensores : String? = null, var alarmas : String? = null, var observaciones : String? = null,
                           var muestraAceite : String? = null, var muestraCombustible : String? = null, var muestraRefrigerante : String? = null, var ultimoLavadoTanque : String? = null, var ultimoTanqueo : String? = null, var capacidadTanqueo : String? = null,
                           var casco : String? = null, var guantes : String? = null, var overol : String? = null, var gafas : String? = null, var otros : String? = null, var transporteATimepo : String? = null, var insumosCompletos : String? = null,
                           var pendienteRecogerInsumos : String? = null, var tiempoEsperaIngreso : String? = null, var tiempoEsperaSalida : String? = null, var serviciosCotizar : String? = null, var otrosCotizar : String? = null, var tipoServicioRealizado : String? = null,
                           var otrosServicios : String? = null, var atsTrabajosrealizados : String? = null, var atsTrabajosAlturas: String? = null, var atsTrabajosConfinados : String? = null, var atsTrabajosCalientes: String? = null, var imagen1 : String? = null, var imagen2 : String? = null, var imagen3 : String? = null,
                           var imagen4 : String? = null, var imagen5 : String? = null, @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var dataTecnico: ByteArray? = null, @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var data: ByteArray? = null) : Parcelable

