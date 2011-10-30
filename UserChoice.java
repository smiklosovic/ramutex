// Stefan Miklosovic
// smiklo11@student.aau.dk

public enum UserChoice {
    // enumeration of possible commands on a prompt
    tick    ("tick   : one tick of Lamport's clocks"),
    request ("request: process requests CS"),
    release ("release: process release a lock/CS"),
    status  ("status : prints status about process"),
    help    ("help   : prints this help"),
    exit    ("exit   : exits a program"),
    quit    ("quit   : alias to exit");
    
    // information related to a command
    private final String info;

    UserChoice(String information) {
	this.info = information;
    }
    
    // method returning an information about a command
    public String info() {
	return info;
    }
}