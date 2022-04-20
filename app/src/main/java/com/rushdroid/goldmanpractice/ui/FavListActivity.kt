package com.rushdroid.goldmanpractice.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rushdroid.goldmanpractice.adapter.FavAdpter
import com.rushdroid.goldmanpractice.adapter.OnClickListener
import com.rushdroid.goldmanpractice.adapter.OnTextViewClick
import com.rushdroid.goldmanpractice.databinding.ActivityFavListBinding
import com.rushdroid.goldmanpractice.model.NasaModel

class FavListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavListBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        setRecyclerView(viewModel.getFav(true))
    }

    private fun setRecyclerView(list: List<NasaModel>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FavAdpter(list, OnClickListener {
            it.isFavourite = false
            viewModel.update(it)
            setRecyclerView(viewModel.getFav(true))
        }, OnTextViewClick {
            startActivity(Intent(this, MainActivity::class.java).putExtra("data", it.date))
            finish()
        })
        binding.recyclerView.adapter = adapter;
    }

}