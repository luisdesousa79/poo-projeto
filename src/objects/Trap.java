package objects;

import pt.iscte.poo.game.Room;

public class Trap extends HeavyObject {
	
	public Trap(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "trap";
	}

	@Override
	public int getLayer() {
		return 2; 
	}

}
