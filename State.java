// Stefan Miklosovic
// smiklo11@student.aau.dk

// this class represents a state of a program in a 
// Ricart-Agrawala's mutex algorithm
public class State {
    StateEnum state;
 
    // a process is firstly initialized to RELEASED state so it is not in CS
    State() { 
	state = StateEnum.RELEASED;
    }
    
    // seting a state of a process
    public void setState(StateEnum s) {
	state = s;
    }
    
    // getting a state of a process
    public StateEnum getState() { 
	return state; 
    }
}