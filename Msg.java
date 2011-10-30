// Stefan Miklosovic
// smiklo11@student.aau.dk

import java.net.DatagramPacket;

// class which represents a message sent among processes
public class Msg {
	private int pid;
	private int time;
	private String message;
	
	Msg(DatagramPacket message) {
	    // a message is in a format ID#TIME#MESSAGE#
	    // so we need to parse it and fill in class variables accordingly
	    String[] parsed = new String(message.getData()).split("#");
	    pid = Integer.parseInt(parsed[0]);
	    time = Integer.parseInt(parsed[1]);
	    this.message = parsed[2];
	}

	Msg(int pid, int requestTime, StateEnum state) {
	    this.pid = pid;
	    this.time = requestTime;
	    this.message = state.toString();
	}
	
	// converting a message to the byte array 
	// (e.g. for sending it via DatagramPacket)
	public byte[] toBytes() {
	    StringBuilder sb = new StringBuilder();
	    return sb.
		    append(getPid()).
		    append("#").
		    append(getTime()).
		    append("#").
		    append(getMessage()).
		    append("#").
		    toString().
		    getBytes();
	}
	
	// setters and getters
	public void setPid(int pid) { this.pid = pid; }
	public void setTime(int time) { this.time = time; }
	public void setMessage(String message) { this.message = message; } 
	public int getPid() { return pid; }
	public int getTime() { return time; }
	public String getMessage() { return message; }
}