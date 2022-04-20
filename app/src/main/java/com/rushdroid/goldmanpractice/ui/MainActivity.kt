package com.rushdroid.goldmanpractice.ui

import NasaModel
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rushdroid.goldmanpractice.R
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
            updateDateInView()
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
            updateUI(it)
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

    private fun updateUI(model: NasaModel) {
        binding.txtTitle.text = model.title
        binding.txtDate.text = model.date
        binding.txtExplanation.text = model.explanation
        if (model.media_type == "image") {
            Glide
                .with(this)
                .load(model.hdurl)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.imageView2);
        }
    }

    private fun getDate(): String {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.getTime());
    }
}
