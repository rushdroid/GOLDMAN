package com.rushdroid.goldmanpractice.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View.GONE
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rushdroid.goldmanpractice.R
import com.rushdroid.goldmanpractice.databinding.ActivityMainBinding
import com.rushdroid.goldmanpractice.model.NasaModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var detailViewModel: DetailViewModel

    var cal = Calendar.getInstance()

    private var nasaModel: NasaModel? = null

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

    override fun onResume() {
        super.onResume()
        detailViewModel.fetchNasaData(getDate())
        if (intent != null) {
            if (intent.hasExtra("data")) {
                binding.btnFav.visibility = GONE
                binding.btnPickDate.visibility = GONE
                binding.imgFav.visibility = GONE
                val string = intent.getStringExtra("data")
                val result = detailViewModel.getDataFromDate(string.toString())
                nasaModel = result.get(0)
                updateUI(nasaModel!!)
            }
        }

    }



    private fun initUI() {
        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.getNasaModel().observe(this, {
            nasaModel = it
            updateUI(it)
        });

        binding.btnFav.setOnClickListener {
            startActivity(Intent(this, FavListActivity::class.java))
        }
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

        binding.imgFav.setOnClickListener {
            if (nasaModel!!.isFavourite) {
                nasaModel!!.isFavourite = false
                binding.imgFav.setImageResource(android.R.drawable.btn_star_big_off)
            } else {
                nasaModel!!.isFavourite = true
                binding.imgFav.setImageResource(android.R.drawable.btn_star_big_on)
            }
            detailViewModel.update(nasaModel!!)
        }
        binding.btnPickDate.text = getDate()

    }

    private fun updateDateInView() {
        binding.btnPickDate.text = getDate()
        detailViewModel.fetchNasaData(getDate())
    }

    private fun updateUI(model: NasaModel) {
        binding.txtTitle.text = model.title
        binding.txtDate.text = model.date
        binding.txtExplanation.text = model.explanation

        if (model.isFavourite) {
            binding.imgFav.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            binding.imgFav.setImageResource(android.R.drawable.btn_star_big_off)
        }

        if (model.media_type == "image") {
            Glide
                .with(this)
                .load(model.url)
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
