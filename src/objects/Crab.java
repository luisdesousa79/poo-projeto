package objects;

import pt.iscte.poo.game.Room;

public class Crab extends GameObject implements Sinkable {

	public Crab(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "crab"; 
	}

	@Override
	public int getLayer() {
		return 2; // investigar qual o layer correto
	}
	
	@Override
	public void sinks() {
	}
	
	public void move() {
		
	}
	
}


