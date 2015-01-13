package com.jym.patpat;

interface IPatpatViewState {
	public void Draw(PatpatView viewContext);

	public void OnClick(PatpatView viewContext);
	public void OnClickBody(PatpatView patpatView);
	public void OnClickLeg(PatpatView patpatView);

	public boolean NeedRedraw();

	public void OnEvolve(PatpatView viewContext);
	public void OnOften(PatpatView viewContext);
	public void OnHeadsetConnected(PatpatView viewContext);
	public void OnHeadsetDisconnected(PatpatView viewContext);
}
