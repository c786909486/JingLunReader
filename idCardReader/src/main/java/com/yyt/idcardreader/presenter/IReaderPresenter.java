package com.yyt.idcardreader.presenter;

import com.routon.plsy.reader.sdk.intf.IReader;
import com.yyt.idcardreader.model.DeviceParamBean;

/**
 * 读卡表示层接口
 * @author lihuili
 *
 */
public interface IReaderPresenter {
	void setReader(IReader reader);
	void startReadcard(DeviceParamBean devParamBean);
	void setDeviceParam(DeviceParamBean devParamBean);
	void stopReadcard();
	void release();
}

