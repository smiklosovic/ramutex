// Stefan Miklosovic
// smiklo11@student.aau.dk

public enum StateEnum {
    
    // enumeration of possible states of a process
    RELEASED ("RELEASED"),
    WANTED   ("WANTED"),
    HELD     ("HELD"),
    OK	     ("OK");
    
    // string which describes a state
    private final String state;
    
    StateEnum(String state) {
	this.state = state;
    }
    
    public String getState() {
	return this.state;
    }
}