// Stefan Miklosovic
// smiklo11@student.aau.dk

import java.net.DatagramSocket;

// interface for message handling, here are action we use while
// handling a message from a network. Process class implements it for example
public interface MsgHandler {
	public void handleMessage(Msg message);
	public Msg recieveMessage(DatagramSocket aSocket);
}
