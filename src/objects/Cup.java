package objects;

import pt.iscte.poo.game.Room;

public class Cup extends LightObject {
	
	public Cup(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "cup";
	}

	@Override
	public int getLayer() {
		return 1; // verificar se está correcto
	}

}
