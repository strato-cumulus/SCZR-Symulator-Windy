package com.sczr.symulator_windy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sczr.symulator_windy.modules.Module;
import com.sczr.symulator_windy.packets.DispatchElevatorPacket;
import com.sczr.symulator_windy.packets.ElevatorCallPacket;
import com.sczr.symulator_windy.packets.Packet;
import com.sczr.symulator_windy.serialization.SerializationList;
import com.sczr.symulator_windy.ui.UIModule;

public class ElevatorController 
{
	private Server server;
	private HashMap<Class<? extends Module>, ArrayList<Integer>> tcpByClass;
	private HashMap<Integer, Client> clientByTcp;
	
	public final int controllerTcp;
	
	public ElevatorController(int controllerTcp) throws IOException
	{
		this.tcpByClass = new HashMap<>();
		this.clientByTcp = new HashMap<>();
		this.server = new Server();
		this.server.start();
		this.server.bind(controllerTcp);
		SerializationList.register(this.server.getKryo());
		this.server.addListener(new ElevatorCallListener());
		this.server.addListener(new DispatchElevatorListener());
		
		this.controllerTcp = controllerTcp;
		Module.CONTROLLER_TCP = controllerTcp;
	}
	
	// wyœlij do konkretnego modu³u
	public void unicast(Packet packet, int tcpPort)
	{
		this.clientByTcp.get(tcpPort).sendTCP(packet);
	}
	
	// wyœlij do konkretnego modu³u
	public void unicast(Packet packet, Module module)
	{
		this.unicast(packet, module.tcpPort);
	}
	
	// wyœlij do wszystkich modu³ów jednej klasy
	public void multicast(Packet packet, Class<? extends Module> targetClass)
	{
		ArrayList<Integer> recipients = this.tcpByClass.get(targetClass);
		if(recipients != null) {
			for(int tcpPort: recipients) {
				this.unicast(packet, tcpPort);
			}
		}
	}
	
	// wyœlij do wszystkich modu³ów którejkolwiek spoœród klas
	public void multicast(Packet packet, Class<? extends Module>[] targetClasses)
	{
		for(Class<? extends Module> targetClass: targetClasses) {
			multicast(packet, targetClass);
		}
	}
	
	// wyœlij do wszystkich zarejestrowanych modu³ów
	public void broadcast(Packet packet)
	{
		for(Class<? extends Module> targetClass: this.tcpByClass.keySet()) {
			multicast(packet, targetClass);
		}
	}
	
	public void register(Module m) throws IOException
	{
		this.register(m.tcpPort, m.getClass());
	}
	
	public void register(int tcpPort, Class<? extends Module> moduleClass) throws IOException
	{
		if(!this.tcpByClass.containsKey(moduleClass)) {
			this.tcpByClass.put(moduleClass, new ArrayList<Integer>());
		}
		this.tcpByClass.get(moduleClass).add(tcpPort);
		this.clientByTcp.put(tcpPort, new Client());
		Client c = this.clientByTcp.get(tcpPort);
		c.start();
		c.connect(5000, "127.0.0.1", tcpPort);
		SerializationList.register(c.getKryo());
	}
	
	class ElevatorCallListener extends Listener
	{
		@Override
		public void received(Connection c, Object o)
		{
			if(o instanceof ElevatorCallPacket) {
				System.out.println("Sending ElevatorCallPacket");
				//multicast((ElevatorCallPacket)o, Model.class);
			}
		}
	}
	
	class DispatchElevatorListener extends Listener
	{
		@Override
		public void received(Connection c, Object o)
		{
			if(o instanceof DispatchElevatorPacket) {
				multicast((DispatchElevatorPacket)o, UIModule.class);
			}
		}
	}
}
