package com.yyt.jinglunreader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yyt.jinglunreader.databinding.ActivityFirstBinding
import com.yyt.jinglunreader.databinding.ActivityMainBinding

/**
 *@packageName com.yyt.jinglunreader
 *@author kzcai
 *@date 2023/6/28
 */
class FirstActivity:AppCompatActivity() {

    lateinit var binding: ActivityFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_first)
        binding.tvToNext.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}