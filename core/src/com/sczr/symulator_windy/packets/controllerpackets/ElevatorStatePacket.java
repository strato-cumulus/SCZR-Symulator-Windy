package com.sczr.symulator_windy.packets.controllerpackets;

import java.util.List;

import com.sczr.symulator_windy.packets.Packet;

public class ElevatorStatePacket extends Packet
{
	private final int[] upDownButtons;
	private final List<Integer> passangersDestinations;
	
	public ElevatorStatePacket(final int[] upDownButtons, List<Integer> passengersDestinations)
	{
		this.upDownButtons = upDownButtons;
		this.passangersDestinations = passengersDestinations;
	}
	
	public int[] getButtons()
	{
		return upDownButtons;
	}
	
	public List<Integer> getDestinations()
	{
		return passangersDestinations;
	}
}
