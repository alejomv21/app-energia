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

    @Query("SELECT * FROM AlmacenamientoFinal ORDER  BY id DESC LIMIT 1")
    fun getAlmacenamientoHoja1(): Almacenamiento

    @Query("SELECT * FROM AlmacenamientoFinal ORDER  BY id DESC LIMIT 1")
    fun getAlmacenamientoHoja2(): Almacenamiento

    @Query("SELECT * FROM AlmacenamientoFinal")
    fun getAlmacenamientoListas(): LiveData<MutableList<Almacenamiento>>

    @Query("SELECT * FROM AlmacenamientoFinal ORDER  BY id DESC LIMIT 1")
    fun getAlmacenamientoPictures(): LiveData<Almacenamiento>

    @Query("SELECT * FROM ALMACENAMIENTOFINAL WHERE ALMACENAMIENTOFINAL.id == :id1")
    fun getAlmacenamientoID(id1 : Int?) : Almacenamiento

    @Update
    suspend fun update(almacenamiento: Almacenamiento)

}