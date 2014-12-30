package com.jym.patpat;

interface IPatpatViewState {
	public void Draw(PatpatView viewContext);

	public void OnClickHead(PatpatView patpatView);
	public void OnClickNeck(PatpatView patpatView);
	public void OnClickBody(PatpatView patpatView);
	public void OnClickShoes(PatpatView patpatView);

	public boolean NeedRedraw();

	public void OnEvolve(PatpatView viewContext);
	public void OnOften(PatpatView coinBlockView);

	public void OnHeadsetConnected(PatpatView viewContext);
	public void OnHeadsetDisconnected(PatpatView viewContext);
}
