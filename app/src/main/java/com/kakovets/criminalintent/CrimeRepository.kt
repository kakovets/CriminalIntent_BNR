package com.kakovets.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.kakovets.criminalintent.database.CrimeDatabase
import com.kakovets.criminalintent.database.migration_1_2
import java.util.UUID
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context){

    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(migration_1_2)
        .build()

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun updateCrime(crime: Crime) = executor.execute { crimeDao.updateCrime(crime) }

    fun addCrime(crime: Crime) = executor.execute { crimeDao.addCrime(crime) }


    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE?: throw IllegalStateException("CrimeRepo must be initialized")
        }
    }
}