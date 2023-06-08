package com.ct.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.ct.base.BaseActivity
import com.ct.databinding.ActivityHomeBinding
import com.ct.model.ToolbarConfig
import com.ct.viewmodel.HomeViewModel
import com.ct.extention.hide
import com.ct.extention.show

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun initViews() {
    }

    override fun collectFlow() {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setToolbar(toolbarConfig: ToolbarConfig) {
        when (toolbarConfig) {
            ToolbarConfig.Hide -> {
                binding.toolbar.hide()
            }

            is ToolbarConfig.Show -> {
                binding.toolbar.show()
                toolbarConfig.customTitle?.let {
                    supportActionBar?.title = it
                }
            }
        }
    }
}