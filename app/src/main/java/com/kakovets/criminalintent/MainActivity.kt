package com.kakovets.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onCrimeSelected(crimeID: UUID) {
//        Log.d(TAG, "MainActivity.onCrimeSelected: $crimeID")
        val fragment = CrimeFragment.newInstance(crimeID)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}