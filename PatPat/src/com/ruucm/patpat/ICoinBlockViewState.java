package com.ruucm.patpat;

import android.graphics.Bitmap;

interface ICoinBlockViewState {
	public void Draw(CoinBlockView viewContext, Bitmap canvas);

	public void OnClick(CoinBlockView viewContext);

	public boolean NeedRedraw();

	public void OnEvolve(CoinBlockView viewContext);

	public void OnOften(CoinBlockView coinBlockView);


	public void OnHeadsetConnected(CoinBlockView viewContext);
	public void OnHeadsetDisconnected(CoinBlockView viewContext);
	
}
