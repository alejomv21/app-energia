package com.equitel.pruebaequitel.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.SerialPlanta

@Dao
interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFInal(almacenamiento: Almacenamiento )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFInal1(almacenamiento: Almacenamiento )
    //@Update

    //@Query("SELECT * FROM EquitelPruebas2")
    //fun getEquitel(): LiveData<SerialPlanta>

    @Query("SELECT * FROM AlmacenamientoFinal ORDER  BY idEquipo DESC LIMIT 1")
    fun getAlmacenamientoHoja1(): Almacenamiento

    @Query("SELECT * FROM AlmacenamientoFinal ORDER  BY idEquipo DESC LIMIT 1")
    fun getAlmacenamientoHoja2(): Almacenamiento

    //@Query("SELECT * FROM AlmacenamientoFinal")
    //fun getAlmacenamientoListas(): MutableList<Almacenamiento>

    @Update
    suspend fun update(almacenamiento: Almacenamiento)

}