package com.example.laporan3_202331206_hafizhhilmanasyhari_splashscreen_dan_menu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    // Deklarasi variabel untuk UI components
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLoading: TextView

    // Konstanta untuk durasi splash screen (dalam milidetik)
    private val SPLASH_DURATION = 3000L

    // Konstanta untuk interval update progress bar
    private val PROGRESS_UPDATE_INTERVAL = 30L

    // Handler untuk menjalankan operasi di UI thread
    private val handler = Handler(Looper.getMainLooper())

    // Variabel untuk tracking progress
    private var progressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi UI components
        initializeViews()

        // Mulai animasi progress bar
        startProgressBarAnimation()

        // Navigasi ke MenuActivity setelah delay
        scheduleNavigationToMenu()
    }

    /**
     * Method untuk inisialisasi views
     */
    private fun initializeViews() {
        progressBar = findViewById(R.id.progressBar)
        tvLoading = findViewById(R.id.tvLoading)

        // Set initial progress
        progressBar.progress = 0
    }

    /**
     * Method untuk memulai animasi progress bar
     */
    private fun startProgressBarAnimation() {
        // Thread untuk update progress bar
        Thread {
            while (progressStatus < 100) {
                // Update progress status
                progressStatus += 1

                // Update UI di main thread
                handler.post {
                    progressBar.progress = progressStatus
                    updateLoadingText(progressStatus)
                }

                try {
                    // Delay untuk smooth animation
                    Thread.sleep(PROGRESS_UPDATE_INTERVAL)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    /**
     * Method untuk update text loading berdasarkan progress
     */
    private fun updateLoadingText(progress: Int) {
        val loadingText = when {
            progress < 30 -> "Initializing..."
            progress < 60 -> "Loading resources..."
            progress < 90 -> "Almost there..."
            else -> "Ready!"
        }
        tvLoading.text = loadingText
    }

    /**
     * Method untuk schedule navigasi ke MenuActivity
     */
    private fun scheduleNavigationToMenu() {
        handler.postDelayed({
            navigateToMenu()
        }, SPLASH_DURATION)
    }

    /**
     * Method untuk navigasi ke MenuActivity
     */
    private fun navigateToMenu() {
        val intent = Intent(this@SplashScreenActivity, MenuActivity::class.java)
        startActivity(intent)

        // Tambahkan transisi animasi
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        // Finish activity agar tidak bisa kembali ke splash screen dengan back button
        finish()
    }

    /**
     * Override onBackPressed untuk mencegah user kembali ke splash screen
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Tidak melakukan apa-apa, agar user tidak bisa back dari splash screen
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove semua callback dari handler untuk mencegah memory leak
        handler.removeCallbacksAndMessages(null)
    }
}