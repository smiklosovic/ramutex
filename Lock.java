// Stefan Miklosovic
// smiklo11@student.aau.dk

// this interface describe a methods for mutual exclusion
public interface Lock extends MsgHandler {
	public void requestCS();
	public void releaseCS();
}
