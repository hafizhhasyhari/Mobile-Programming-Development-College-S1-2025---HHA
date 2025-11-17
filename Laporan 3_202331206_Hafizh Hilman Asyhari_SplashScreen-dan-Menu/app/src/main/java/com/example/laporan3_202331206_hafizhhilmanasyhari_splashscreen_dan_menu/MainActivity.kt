package com.example.laporan3_202331206_hafizhhilmanasyhari_splashscreen_dan_menu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MenuActivity : AppCompatActivity() {

    // Deklarasi UI Components
    private lateinit var etNama: TextInputEditText
    private lateinit var etNim: TextInputEditText
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var rbLakiLaki: RadioButton
    private lateinit var rbPerempuan: RadioButton
    private lateinit var spinnerHobi: Spinner
    private lateinit var cbKonfirmasi: CheckBox
    private lateinit var btnKirim: MaterialButton

    // Array untuk data hobi
    private val hobiList = arrayOf(
        "Pilih Hobi",
        "Membaca",
        "Olahraga",
        "Musik",
        "Traveling",
        "Gaming",
        "Memasak",
        "Fotografi",
        "Menulis"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Inisialisasi views
        initializeViews()

        // Setup spinner hobi
        setupSpinnerHobi()

        // Setup button click listener
        setupButtonListener()
    }

    /**
     * Method untuk inisialisasi semua views
     */
    private fun initializeViews() {
        etNama = findViewById(R.id.etNama)
        etNim = findViewById(R.id.etNim)
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin)
        rbLakiLaki = findViewById(R.id.rbLakiLaki)
        rbPerempuan = findViewById(R.id.rbPerempuan)
        spinnerHobi = findViewById(R.id.spinnerHobi)
        cbKonfirmasi = findViewById(R.id.cbKonfirmasi)
        btnKirim = findViewById(R.id.btnKirim)
    }

    /**
     * Method untuk setup Spinner Hobi dengan ArrayAdapter
     */
    private fun setupSpinnerHobi() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            hobiList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerHobi.adapter = adapter

        // Set listener untuk spinner
        spinnerHobi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    // User memilih hobi (bukan "Pilih Hobi")
                    val selectedHobi = hobiList[position]
                    // Optional: bisa tambahkan feedback
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tidak perlu implementasi khusus
            }
        }
    }

    /**
     * Method untuk setup button click listener
     */
    private fun setupButtonListener() {
        btnKirim.setOnClickListener {
            processFormSubmission()
        }
    }

    /**
     * Method untuk memproses pengiriman form
     */
    private fun processFormSubmission() {
        // Validasi input
        if (!validateInput()) {
            return
        }

        // Ambil data dari form
        val nama = etNama.text.toString().trim()
        val nim = etNim.text.toString().trim()
        val jenisKelamin = getSelectedJenisKelamin()
        val hobi = spinnerHobi.selectedItem.toString()

        // Tampilkan Toast selamat datang
        showWelcomeToast(nama)

        // Kirim data ke HasilActivity
        navigateToHasil(nama, nim, jenisKelamin, hobi)
    }

    /**
     * Method untuk validasi input form
     * @return true jika semua input valid, false jika ada yang tidak valid
     */
    private fun validateInput(): Boolean {
        val nama = etNama.text.toString().trim()
        val nim = etNim.text.toString().trim()
        val selectedRadioId = rgJenisKelamin.checkedRadioButtonId
        val selectedSpinnerPosition = spinnerHobi.selectedItemPosition

        // Validasi nama
        if (nama.isEmpty()) {
            showToast("Nama harus diisi!")
            etNama.requestFocus()
            return false
        }

        // Validasi panjang nama minimal 3 karakter
        if (nama.length < 3) {
            showToast("Nama minimal 3 karakter!")
            etNama.requestFocus()
            return false
        }

        // Validasi NIM
        if (nim.isEmpty()) {
            showToast("NIM harus diisi!")
            etNim.requestFocus()
            return false
        }

        // Validasi panjang NIM
        if (nim.length < 8) {
            showToast("NIM minimal 8 digit!")
            etNim.requestFocus()
            return false
        }

        // Validasi jenis kelamin
        if (selectedRadioId == -1) {
            showToast("Pilih jenis kelamin terlebih dahulu!")
            return false
        }

        // Validasi hobi
        if (selectedSpinnerPosition == 0) {
            showToast("Pilih hobi terlebih dahulu!")
            return false
        }

        // Validasi checkbox konfirmasi
        if (!cbKonfirmasi.isChecked) {
            showToast("Harap centang konfirmasi data!")
            return false
        }

        return true
    }

    /**
     * Method untuk mendapatkan jenis kelamin yang dipilih
     * @return String jenis kelamin
     */
    private fun getSelectedJenisKelamin(): String {
        val selectedId = rgJenisKelamin.checkedRadioButtonId
        return when (selectedId) {
            R.id.rbLakiLaki -> "Laki-laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> ""
        }
    }

    /**
     * Method untuk menampilkan Toast
     * @param message Pesan yang akan ditampilkan
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Method untuk menampilkan Toast selamat datang
     * @param nama Nama user
     */
    private fun showWelcomeToast(nama: String) {
        Toast.makeText(
            this,
            "Selamat datang, $nama",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Method untuk navigasi ke HasilActivity dengan membawa data
     */
    private fun navigateToHasil(nama: String, nim: String, jenisKelamin: String, hobi: String) {
        val intent = Intent(this@MenuActivity, HasilActivity::class.java).apply {
            putExtra(HasilActivity.EXTRA_NAMA, nama)
            putExtra(HasilActivity.EXTRA_NIM, nim)
            putExtra(HasilActivity.EXTRA_JENIS_KELAMIN, jenisKelamin)
            putExtra(HasilActivity.EXTRA_HOBI, hobi)
        }
        startActivity(intent)

        // Tambahkan transisi animasi
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    /**
     * Method untuk clear form (optional, bisa digunakan untuk reset)
     */
    private fun clearForm() {
        etNama.text?.clear()
        etNim.text?.clear()
        rgJenisKelamin.clearCheck()
        spinnerHobi.setSelection(0)
        cbKonfirmasi.isChecked = false
    }
}