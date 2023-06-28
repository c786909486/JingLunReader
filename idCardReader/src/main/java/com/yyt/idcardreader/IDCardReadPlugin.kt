package com.yyt.idcardreader

import android.content.Context
import android.util.Log
import com.routon.plsy.reader.sdk.common.Info
import com.yyt.idcardreader.model.DeviceParamBean
import com.yyt.idcardreader.model.ReportReadIDCardEvent
import com.yyt.idcardreader.presenter.ReaderPresenter
import com.yyt.idcardreader.view.IReaderView
import java.lang.ref.WeakReference

/**
 *@packageName com.yyt.idcardreader
 *@author kzcai
 *@date 2023/6/27
 */
class IDCardReadPlugin : IReaderView {

    private val TAG = "IDCardReadPlugin"
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            IDCardReadPlugin()
        }
        private var context: WeakReference<Context>? = null

        fun initContext(context: Context){
            this.context = WeakReference(context)
        }
    }


    private var presenter: ReaderPresenter? = ReaderPresenter(this)
    private val defaultOption: DeviceParamBean
        get() {
            val bean = DeviceParamBean()
            bean.isRead_a = true
            bean.isRead_b = true
            bean.isRead_b_cid = true
            bean.device_type = DeviceParamBean.DEV_TYPE_INNER_OR_USB
            bean.isUse_cache = false
            bean.isDual_channel = false
            bean.isRead_b_cid = true
            bean.isRead_fp = true
            return bean
        }

    var onReadListener: OnReadResultListener? = null



    fun startRead(param: DeviceParamBean? = null) {
        presenter?.startReadcard(param ?: defaultOption)
    }

    fun stopRead() {
        presenter?.stopReadcard()
    }

    override fun getContext(): Context {
        return IDCardReadPlugin.context!!.get()!!
    }


    override fun onReadIDCardSuccessed(event: ReportReadIDCardEvent?) {
        if (event != null) {
            Log.d(TAG, "onReadIDCardSuccessed: ${event.getiDCardInfo()}")
            onReadListener?.onGetIDCardInfo(event.getiDCardInfo(),event.cid?:"")
        }
    }

    override fun onReadIDCardFailed() {
        onReadListener?.onFail("读卡失败")
    }

    override fun onCardRemoved() {
        onReadListener?.onCardRemove()
    }

    override fun appendLog(code: Int, log: String?) {

    }

    override fun onGotStartReadcardResult(error_code: Int, has_inner_reader: Boolean) {

    }

    override fun onReadACardSuccessed(card_sn: ByteArray?) {

        if (card_sn!=null){
            //cardANo=String.format("%02X%02X%02X%02X", data[0], data[1], data[2], data[3]);
            var cardANo = ""
            for (i in card_sn.indices) {
                cardANo += String.format("%02X", card_sn[i])
            }
            onReadListener?.onGetCardId(cardANo)
        }
    }

    override fun onReaderStopped() {

    }

    override fun updateParasUI(paras: Info.ReaderParas?) {

    }

    fun release() {
        // 退出前要安全释放资源
        if (presenter != null) {
            presenter?.stopReadcard()
            presenter?.release()
            presenter = null
        }
        IDCardReadPlugin.context = null
    }


    interface OnReadResultListener {
        fun onGetIDCardInfo(cardInfo: Info.IDCardInfo, cid: String)

        fun onGetCardId(cardNumber: String)

        fun onCardRemove()

        fun onFail(error: String)
    }
}