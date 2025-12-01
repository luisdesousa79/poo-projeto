package objects;

import pt.iscte.poo.game.Room;

public class Door extends ImmovableObject {

	public Door(Room room) {
		super(room);
	}
		
	@Override
	public String getName() {
		return "water"; 
	}
	
	@Override
	public int getLayer() {
		return 0; 
	}
	
}
