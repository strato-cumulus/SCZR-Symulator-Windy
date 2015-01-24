package com.sczr.symulator_windy.packets.GUIpackets;

public class InitializeGUIPacket {
	public Integer storeyNumber;
	public Integer storeyHeight;
	public Integer elevatorWidth;
	
	public InitializeGUIPacket() {}
	
	public InitializeGUIPacket(int storeyNumber, int storeyHeight, int elevatorWidth){
		this.storeyHeight = storeyHeight;
		this.storeyNumber = storeyNumber;
		this.elevatorWidth = elevatorWidth;
	}
	
}
