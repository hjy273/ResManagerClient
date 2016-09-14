package com.resmanager.client.user.order.delivery;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.alibaba.fastjson.JSON;
import com.resmanager.client.model.ResultModel;
import com.resmanager.client.utils.CommonView;
import com.resmanager.client.utils.ContactsUtils;
import com.resmanager.client.utils.WebServiceUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 
 * @ClassName: DeleteDeliveryPhotoAsyncTask
 * @Description: 删除所有发货已经上传的照片
 * @author ShenYang
 * @date 2015-12-10 下午4:11:55
 * 
 */
public class DeleteAllDeliveryPhotoAsyncTask extends AsyncTask<Void, Void, String> {

	private Context context;
	private String workId;
	private Dialog loadingDilog;
	private DelAllDeliveryListener delAllDeliveryListener;

	public DeleteAllDeliveryPhotoAsyncTask(Context context, String workId) {
		this.context = context;
		this.workId = workId;
	}

	@Override
	protected String doInBackground(Void... arg0) {
		WebServiceUtil ws = new WebServiceUtil(false, ContactsUtils.WS_URL, ContactsUtils.Delivery_DelPicAll);
		ws.addProperty("UserKey", ContactsUtils.USER_KEY);
		ws.addProperty("WorkID", workId);
		try {
			String json = ws.start();
			return json;
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String rv) {
		super.onPostExecute(rv);
		if (this.loadingDilog.isShowing()) {
			this.loadingDilog.cancel();
			this.loadingDilog = null;
		}
		if (rv != null) {
			try {
				ResultModel resultModel = JSON.parseObject(rv, ResultModel.class);
				getDelAllDeliveryListener().delAllDeliveryResult(resultModel);
			} catch (Exception e) {
				e.printStackTrace();
				getDelAllDeliveryListener().delAllDeliveryResult(null);
			}
		} else {
			getDelAllDeliveryListener().delAllDeliveryResult(null);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (this.loadingDilog == null) {
			this.loadingDilog = CommonView.LoadingDialog(context);
		}
		if (this.loadingDilog.isShowing() == false) {
			this.loadingDilog.show();
		}
	}

	public DelAllDeliveryListener getDelAllDeliveryListener() {
		return delAllDeliveryListener;
	}

	public void setDelAllDeliveryListener(DelAllDeliveryListener delAllDeliveryListener) {
		this.delAllDeliveryListener = delAllDeliveryListener;
	}

	public interface DelAllDeliveryListener {
		public void delAllDeliveryResult(ResultModel resultModel);
	}
}
