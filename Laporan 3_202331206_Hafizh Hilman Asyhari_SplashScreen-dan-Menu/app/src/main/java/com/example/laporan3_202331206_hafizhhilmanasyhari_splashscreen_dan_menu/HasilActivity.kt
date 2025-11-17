package com.example.laporan3_202331206_hafizhhilmanasyhari_splashscreen_dan_menu

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HasilActivity : AppCompatActivity() {

    // Companion object untuk konstanta key intent
    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIM = "extra_nim"
        const val EXTRA_JENIS_KELAMIN = "extra_jenis_kelamin"
        const val EXTRA_HOBI = "extra_hobi"
    }

    // Deklarasi UI Components
    private lateinit var tvNamaResult: TextView
    private lateinit var tvNimResult: TextView
    private lateinit var tvJenisKelaminResult: TextView
    private lateinit var tvHobiResult: TextView
    private lateinit var btnKembali: MaterialButton
    private lateinit var btnShare: MaterialButton

    // Variabel untuk menyimpan data
    private var nama: String = ""
    private var nim: String = ""
    private var jenisKelamin: String = ""
    private var hobi: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil)

        // Inisialisasi views
        initializeViews()

        // Ambil data dari intent
        retrieveDataFromIntent()

        // Tampilkan data ke UI
        displayData()

        // Setup button listeners
        setupButtonListeners()
    }

    /**
     * Method untuk inisialisasi semua views
     */
    private fun initializeViews() {
        tvNamaResult = findViewById(R.id.tvNamaResult)
        tvNimResult = findViewById(R.id.tvNimResult)
        tvJenisKelaminResult = findViewById(R.id.tvJenisKelaminResult)
        tvHobiResult = findViewById(R.id.tvHobiResult)
        btnKembali = findViewById(R.id.btnKembali)
        btnShare = findViewById(R.id.btnShare)
    }

    /**
     * Method untuk mengambil data dari intent
     */
    private fun retrieveDataFromIntent() {
        // Ambil data dengan pengecekan null safety
        nama = intent.getStringExtra(EXTRA_NAMA) ?: ""
        nim = intent.getStringExtra(EXTRA_NIM) ?: ""
        jenisKelamin = intent.getStringExtra(EXTRA_JENIS_KELAMIN) ?: ""
        hobi = intent.getStringExtra(EXTRA_HOBI) ?: ""

        // Log data untuk debugging (optional)
        android.util.Log.d("HasilActivity", "Data received - Nama: $nama, NIM: $nim")
    }

    /**
     * Method untuk menampilkan data ke UI
     */
    private fun displayData() {
        tvNamaResult.text = nama
        tvNimResult.text = nim
        tvJenisKelaminResult.text = jenisKelamin
        tvHobiResult.text = hobi
    }

    /**
     * Method untuk setup button listeners
     */
    private fun setupButtonListeners() {
        // Button Kembali - kembali ke MenuActivity
        btnKembali.setOnClickListener {
            finish() // Menutup HasilActivity dan kembali ke MenuActivity
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        // Button Share - share data melalui Intent
        btnShare.setOnClickListener {
            shareData()
        }
    }

    /**
     * Method untuk share data menggunakan Intent
     */
    private fun shareData() {
        val shareText = buildShareText()
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Data Mahasiswa")
        }
        startActivity(Intent.createChooser(shareIntent, "Bagikan data melalui:"))
    }

    /**
     * Method untuk membuat text yang akan di-share
     * @return String formatted text untuk di-share
     */
    private fun buildShareText(): String {
        return """
            DATA MAHASISWA
            ====================
            Nama: $nama
            NIM: $nim
            Jenis Kelamin: $jenisKelamin
            Hobi: $hobi
            ====================
            
            Dibuat dengan FormInputApp
        """.trimIndent()
    }

    /**
     * Override onBackPressed untuk memberikan animasi transisi
     */
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}