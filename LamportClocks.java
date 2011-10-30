public class LamportClocks {
    private int c;
    
    public LamportClocks() {
	c = 1;
    }
    
    public int getValue() {
	return c;
    }
    
    public void tick() {
	c = c + 1;
    }
    
    public void sendAction() {
	// include c in message
	c = c + 1;
    }
    
    public void recieveAction(int sentValue) {
	c = Math.max(c, sentValue) + 1;
    }
}
