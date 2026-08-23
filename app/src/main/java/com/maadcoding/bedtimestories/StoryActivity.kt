package com.maadcoding.bedtimestories

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.maadcoding.bedtimestories.Constant.DETAILS
import com.maadcoding.bedtimestories.Constant.NAME
import com.maadcoding.bedtimestories.Constant.PICTURE
import com.maadcoding.bedtimestories.Constant.SOUND
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_story)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titleTv: TextView = findViewById(R.id.title_tv)
        val detailsTv: TextView = findViewById(R.id.details_tv)
        val storyIV: ImageView = findViewById(R.id.story_iv)

        titleTv.text = getString(intent.getIntExtra(NAME, 0))
        detailsTv.text = getString(intent.getIntExtra(DETAILS, -1))
        storyIV.setImageResource(intent.getIntExtra(PICTURE, -2))

        lifecycleScope.launch {
          val size = dataStore.data.first()[Keys.SIZE_KEY]
            detailsTv.textSize = size?.toFloat()  ?: 16.0f
        }
    }

    override fun onStart() {
        super.onStart()
        val soundResId = intent.getIntExtra(SOUND, -3)
        if (soundResId != -3) {
            val player = MediaPlayer.create(this, soundResId)
            if (player != null) {
                mediaPlayer = player
                mediaPlayer.start()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (::mediaPlayer.isInitialized) {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        }
    }

}