// Stefan Miklosovic
// smiklo11@student.aau.dk

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

// this class represents a process in a system
public class Process implements MsgHandler {

    // process id for process identification
    protected int pid;
    // number of processes in a system in Ricart-Agrawala's algorithm
    protected int numOfProcesses;
    // state of critical section from processes view
    protected State state;
    // port of process it is listening for incomming messages at
    protected int port;
    // ports of other processes for udp communication
    LinkedList<Pair> ports;
    // length of a input buffer for message recieving
    protected final static int bufferLength = 100;
    
    Process(int id, int n, int p, LinkedList<Pair> ports) {
	// state is firstly set to the RELEASED, look at State's constructor
	state = new State();
	pid = id;
	numOfProcesses = n;
	port = p;
	this.ports = ports;
    }

    // waiting method for a process thread, typically it waits for a message
    public synchronized void processWait() {
	try {
	    wait();
	} catch (Exception e) {
	    System.err.println(e);
	}
    }

    // multicasting of messages to the other processes
    public void multicastMessage(Msg message) {
	for (int i = 1; i <= numOfProcesses; i++) {
	    if (i != this.pid) {
		sendMessage(ports.get(i-1).getPort(), message);
	    }
	}
    }
    
    // sending of a message to another process
    public void sendMessage(int port, Msg m) {
	DatagramSocket aSocket = null;
	try {
	    aSocket = new DatagramSocket();
	    InetAddress aHost = InetAddress.getByAddress(
		    new byte[] {(byte)127, (byte)0, (byte)0 ,(byte) 1});
	    DatagramPacket message = 
	    new DatagramPacket(m.toBytes(), m.toBytes().length, aHost, port);
	    // actual sending of a message
	    aSocket.send(message);
	}
	catch (IOException ex) { }
	finally { if(aSocket != null) aSocket.close(); }
    }
    
    // receiving of a message form a network 
    @Override
    public Msg recieveMessage(DatagramSocket aSocket) {
	DatagramPacket message = null;
	byte[] buffer = null;
	try {
	    buffer = new byte[bufferLength];
	    message = new DatagramPacket(buffer, buffer.length);
	    aSocket.receive(message);
	} 
	catch (IOException ex) {
	}
	return new Msg(message);
    }

    @Override
    public void handleMessage(Msg message) {
	// this method is empty since it is overriden in MutexProcess class
    }
}
