package com.yyt.jinglunreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.routon.plsy.reader.sdk.common.Info
import com.yyt.idcardreader.IDCardReadPlugin
import com.yyt.jinglunreader.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),IDCardReadPlugin.OnReadResultListener {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
//        setContentView(R.layout.activity_main)
        IDCardReadPlugin.initContext(this)
        binding.btnStartRead.setOnClickListener {
            IDCardReadPlugin.instance.startRead()
        }

        IDCardReadPlugin.instance.startRead()

        binding.btnStop.setOnClickListener {
            IDCardReadPlugin.instance.stopRead()
        }

        IDCardReadPlugin.instance.addListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        IDCardReadPlugin.instance.removeListener(this)
        IDCardReadPlugin.instance.release()
    }

    override fun onGetIDCardInfo(cardInfo: Info.IDCardInfo, cid: String) {
        binding.tvContent.text = cardInfo.id
    }

    override fun onGetCardId(cardNumber: String) {
        binding.tvContent.text =  cardNumber.toBigInteger(16).toString()

    }

    override fun onCardRemove() {
        binding.tvContent.text = "卡已离开"

    }

    override fun onFail(error: String) {
        binding.tvContent.text = "错误==>${error}"

    }
}