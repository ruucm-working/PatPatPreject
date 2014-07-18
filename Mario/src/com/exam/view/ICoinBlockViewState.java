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
	
	public void OnWifi(CoinBlockView viewContext);
	
	public void OnPowerConnected(CoinBlockView viewContext);
}
