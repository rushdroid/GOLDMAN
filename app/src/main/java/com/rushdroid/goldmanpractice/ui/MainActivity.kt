package com.rushdroid.goldmanpractice.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rushdroid.goldmanpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var detailViewModel: DetailViewModel
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
            Log.d("TAG", "initUI: ----->")
        }
        detailViewModel.fetchNasaData("2022-02-02")
    }
}