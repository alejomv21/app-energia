package com.equitel.pruebaequitel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.equitel.pruebaequitel.Almacenamiento
import com.equitel.pruebaequitel.SerialPlanta

@Database(entities = [Almacenamiento::class], version = 23)
abstract class EqDataBase : RoomDatabase(){
    abstract val eqDao : EqDao
}

private lateinit var INSTANCE: EqDataBase

fun getDataBase(context: Context): EqDataBase{
    synchronized(EqDataBase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EqDataBase::class.java,
                "EquitelPruebas5_db"
            )   .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE
    }
}