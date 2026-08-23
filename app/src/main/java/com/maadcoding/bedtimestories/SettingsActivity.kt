package com.maadcoding.bedtimestories

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val group : RadioGroup =findViewById(R.id.radio_group)
        var isInitialSetup = true

        lifecycleScope.launch {
            val savedSize = dataStore.data.first()[Keys.SIZE_KEY]
            val rbi = when(savedSize) {
                48 -> R.id.large_rb_48
                24 -> R.id.medium_rb_24
                else -> R.id.small_rb_16
            }
            group.check(rbi)
            isInitialSetup = false
        }

        group.setOnCheckedChangeListener { _, id ->
            if (isInitialSetup) return@setOnCheckedChangeListener

            val size = when(id){
                R.id.small_rb_16 -> 16
                R.id.medium_rb_24 -> 24
                else  -> 48
            }

            lifecycleScope.launch {
                dataStore.edit {
                    it[Keys.SIZE_KEY] = size
                }
            }
            Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show()
        }

    }

}