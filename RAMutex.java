// Stefan Miklosovic
// smiklo11@student.aau.dk

import java.util.LinkedList;

// class which implements Lock interface and extends Process class
// this class represents the core of the Ricart-Agrawala's mutex algorithm
public class RAMutex extends Process implements Lock {
    // logical time of request
    int requestsTime;
    // number of recieved messages
    int numOfRecieved = 0;
    // logical clocks for process
    LamportClocks clocks = new LamportClocks();
    // queue of requests
    LinkedList<Integer> queue = new LinkedList<Integer>();

    // we call a super class Process in order to initialize it
    RAMutex(int id, int n, int port, LinkedList<Pair> ports) {
	super(id, n, port, ports);
    }

    // requesting of a critical section
    @Override
    public synchronized void requestCS() {
	// upon entry to the CS, we mark our process as WANTED
	state.setState(StateEnum.WANTED);
	// process ticks once
	clocks.tick();
	// process gets time of the request event
	requestsTime = clocks.getValue();
	// process multicasts a message to the all processes in a system
	multicastMessage(new Msg(pid, requestsTime, state.getState()));
	// a process sets a number of recieved confirmation messages 
	// from other processes to 0
	numOfRecieved = 0;
	// a process continuously waits for all confirmation messages 
	// in order go to the CS
	while (numOfRecieved < numOfProcesses - 1) {
	    processWait();
	}
	// after success to get all confimation messages, 
	// a process sets itself to the HELD state, process is in a CS
	state.setState(StateEnum.HELD);
    }

    // releasing of a critical section
    @Override
    public synchronized void releaseCS() {
	// process is not interested in a CS anymore
	requestsTime = -1;
	// while pending queue is not empty, process sends 
	// a confirmation message to all processes in a queue
	while (!queue.isEmpty()) {
	    int pid = queue.removeFirst();
	    sendMessage(ports.get(pid-1).getPort(), 
		    new Msg(pid, clocks.getValue(), StateEnum.OK));
	}
	// process sets itself to the RELEASED state
	state.setState(StateEnum.RELEASED);
    }

    // handling of a message a process gets
    @Override
    public synchronized void handleMessage(Msg message) {
	// what is a timestamp of a message a process got?
	int timeStamp = message.getTime();
	// process decides if it increase its logical clocks
	clocks.recieveAction(timeStamp);
	// what is an id of a process this process got a message from?
	int pid = message.getPid();
	// if a process got a WANTED message
	if (message.getMessage().equals(StateEnum.WANTED.toString())) {
	    // if a process is not interested in entering the critical section
	    if ((requestsTime == -1)
		    // or a timestamp of recieved message is lower that ours
		    || (timeStamp < requestsTime)
		    // or timestemp is equal and recived pid is lower that ours
		    || ((timeStamp == requestsTime) && (pid < this.pid))) {
		// a process sends a confirmation to such proces immediately
		sendMessage(ports.get(pid-1).getPort(), 
			new Msg(pid, clocks.getValue(), StateEnum.OK));
	    } else {
		// else a process add a requested process to the pending queue
		queue.add(pid);
	    }
	// else if a process got an confirmation message
	} else if (message.getMessage().equals(StateEnum.OK.toString())) {
	    // a process increases a number of so far recieved OK messages
	    numOfRecieved++;
	    // and notifies a thread so processWait() in 
	    // a requestCS() will enter another loop
	    if (numOfRecieved == numOfProcesses - 1) {
		notify();
	    }
	}
    }

    // single tick in a process provided by Lamport's clocks
    public void tick() {
	clocks.tick();
    }

    // print out usefull information about the process
    public void status() {
	System.out.print("pid         : " + pid + "\n"
		+ "state       : " + state.getState() + "\n"
		+ "time        : " + clocks.getValue() + "\n"
		+ "processes   : " + numOfProcesses + "\n"
		+ "port        : " + port + "\n"
		+ "requestsTime: " + requestsTime + "\n");
    }
}