package com.rushdroid.goldmanpractice.ui

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rushdroid.goldmanpractice.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var detailViewModel: DetailViewModel

    var cal = Calendar.getInstance()

    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateDateInView()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
    }

    private fun initUI() {
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getNasaModel().observe(this, {
            binding.txtTitle.text = it.title
            binding.txtDate.text = it.date
            binding.txtExplanation.text = it.explanation
        });
        binding.btnPickDate.setOnClickListener {
            val dialog = DatePickerDialog(
                this@MainActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.setMaxDate(Date().time)
            dialog.show()
        }
        binding.btnPickDate.text = getDate()
        detailViewModel.fetchNasaData(getDate())
    }

    private fun updateDateInView() {
        binding.btnPickDate.text = getDate()
        detailViewModel.fetchNasaData(getDate())
    }

    private fun getDate(): String{
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.getTime());
    }
}
