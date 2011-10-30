// Stefan Miklsovic
// smiklo11@student.aau.dk

import java.net.DatagramSocket;
import java.net.InetAddress;

// this class represents a listener for a process
// it is a thread which just listens for incoming messages from a network
public class ListenerThread extends Thread {

    Process process;
    DatagramSocket aSocket = null;

    public ListenerThread(Process process) throws Exception {
	this.process = process;
	    // a process is bind to the 127.0.0.1 address 
	    // to a specified port
	    aSocket = new DatagramSocket(process.port, 
		    InetAddress.getByAddress(
		    new byte[] {(byte)127, (byte)0, (byte)0 ,(byte) 1}));
    }
    
    // a thread has to close a socket if it was open
    @Override
    protected void finalize() throws Throwable {
	if (aSocket != null) {
	    aSocket.close();
	}
    }

    // wait for a message and handle a message upon recieving it
    // recieveMessage() method is blocking, it waits for a message 
    // until it gets some 
    @Override
    public void run() {
	while (true) {
	    process.handleMessage(process.recieveMessage(aSocket));
	}
    }
}
