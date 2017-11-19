package com.ncatz.yeray697.managepc.socket;

public interface IServerStatus {
	void onUserDisconnected();
	void onUserConnected();
	void onError(String error);
	void onStatusChanged(Status status);
}
