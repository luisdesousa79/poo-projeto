package objects;

import pt.iscte.poo.game.Room;

public class Anchor extends MovableObject {
	
	public Anchor(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "anchor";
	}

	@Override
	public int getLayer() {
		return 1; //verificar se está correcto
	}

}
