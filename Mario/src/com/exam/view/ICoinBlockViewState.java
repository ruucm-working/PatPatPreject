package com.exam.view;

import android.graphics.Bitmap;

interface ICoinBlockViewState {
	public void Draw(CoinBlockView viewContext, Bitmap canvas);

	public void OnClick(CoinBlockView viewContext);

	public boolean NeedRedraw();

	public void OnEvolve(CoinBlockView viewContext);

	public void OnOften(CoinBlockView coinBlockView);

	public void OnInit(CoinBlockView coinBlockView);

	public void OnDblClick(CoinBlockView viewContext);
	
	public void OnWifiConnected(CoinBlockView viewContext);
	public void OnWifiDisconnected(CoinBlockView viewContext);
	
	public void OnPowerConnected(CoinBlockView viewContext);
	public void OnPowerDisconnected(CoinBlockView viewContext);
	
	public void OnUSBConnected(CoinBlockView viewContext);
	public void OnUSBDisconnected(CoinBlockView viewContext);
	
	public void OnHeadsetConnected(CoinBlockView viewContext);
	public void OnHeadsetDisconnected(CoinBlockView viewContext);
	
	public void OnPlaneModeOn(CoinBlockView viewContext);
	public void OnPlaneModeOff(CoinBlockView viewContext);	
	
	public void OnSMSReceived(CoinBlockView viewContext);
}
