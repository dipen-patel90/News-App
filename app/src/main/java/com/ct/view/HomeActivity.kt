package com.ct.view

import android.os.Bundle
import com.ct.base.BaseActivity
import com.ct.databinding.ActivityHomeBinding
import com.ct.model.ToolbarConfig
import com.mandi.extention.hide
import com.mandi.extention.show

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

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