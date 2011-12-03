// Stefan Miklosovic
// smiklo11@student.aau.dk

/* used literature: 
 * Concurrent and Distributed Computing in Java by Vijay Kumar Garg
 * core mutex implementation ideas were taken from this book
 * 
 * Since I have not been able to simulate full networking environment,
 * I just make all processes bound to address 127.0.0.1 and only udp ports
 * throught they communicate differ.
 * 
 * how to use it:
 * 1) put all *.java files to one directory
 * 2) $ javac MutexProcess.java
 * 3) $ java -Djava.net.preferIPv4Stack=true MutexProcess 1 3 5000 5001 5002
 * where 1 is a pid of a process
 *       3 is a total number of processes in a system
 *       5000 is a port on which this process listents to incoming messages
 *       5001 is a port on which 2nd process listens to
 *       5002 is a port on which 3nd process listens to
 * 
 * the second process is then executed like:
 * 4) $ java -Djava.net.preferIPv4Stack=true MutexProcess 2 3 5000 5001 5002
 * and the third like
 * 5) $ java -Djava.net.preferIPv4Stack=true MutexProcess 3 3 5000 5001 5002
 * 
 * You have to execute these processes concurently
 * 
 * after launching a program, user prompt appears in every process
 * prompt> 
 * You can now use it, command "help" tells you more.
 * Simple output:
 * prompt> status
 * pid         : 1
 * state       : RELEASED
 * time        : 1
 * processes   : 2
 * port        : 5000
 * prompt> request
 * prompt> status
 * pid         : 1
 * state       : HELD
 * time        : 4
 * processes   : 2
 * port        : 5000
 * prompt> release
 * 
 * you get all possible commands on command prompt by entering "help" command
 */

import java.io.IOException;
import java.util.LinkedList;

// entry class of the program
public class MutexProcess {

    public static void main(String[] args) {
	// Linked list for saving pairs pid - port
	LinkedList<Pair> pidPort = new LinkedList<Pair>();
	// parsing of command line, in case of bad format, 
	// we print out help and quit
	if ((args.length - 2) != (Integer.parseInt(args[1]))) {
	    System.out.println("<program> <myId> <numProc> <port> " + 
		    "<other port2> [<other port3> ... <other port<numProc>>]");
	    System.exit(1);
	}
	
	// parsing of command line ports to the pidPort pair
	int j = 0;
	for (int i = 1; i <= Integer.parseInt(args[1]); i++) {
		Pair pair = new Pair(i, Integer.parseInt(args[2+(j++)]));
		pidPort.add(pair);
	}
	
	// initialization of process
	RAMutex process = new RAMutex(
		Integer.parseInt(args[0]), // id of process
		Integer.parseInt(args[1]), // number of processes in total
	    	pidPort.get(Integer.parseInt(args[0])-1).getPort(), // port of p
		pidPort);		   // array of ports of other processes

	try {
	    // we start to listen to incoming communication in a separate thread
	    (new ListenerThread(process)).start();
	} catch (Exception ex) {
	    System.out.println(ex);
	    System.exit(1);
	}

	// user prompt where user specify what he/she wants to do
	while (true) {
	    User.printPrompt();
	    try {
		switch (UserChoice.valueOf(User.getChoice())) {
		    case tick:
			process.tick();
			break;
		    case request:
			process.requestCS();
			break;
		    case release:
			process.releaseCS();
			break;
		    case status:
			process.status();
			break;
		    case help:
			User.printHelp();
			break;
		    case exit:
			System.exit(0);
		    case quit:
			System.exit(0);
		    default:
			User.printHelp();
		}
	    } catch (IllegalArgumentException e) {
		User.printHelp();
	    } catch (IOException e) {
		System.out.println(e);
		System.exit(1);
	    }
	}
    }
}