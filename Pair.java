// Stefan Miklosovic
// smiklo11@student.aau.dk

// this class is for storing pair of numbers, 
// more concretely pair of id of process and its 
// listening port
public class Pair {
    private final int port;
    private final int id;
    
    public Pair(int id, int port) {
	this.port = port;
	this.id = id;
    }

    public int getId() {
	return id;
    }

    public int getPort() {
	return port;
    }
}