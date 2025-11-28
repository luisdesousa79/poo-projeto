package objects;

import pt.iscte.poo.game.Room;

public class Stone extends HeavyObject {
	
	public Stone(Room room) {
		super(room);
	}
	
	@Override
	public String getName() {
		return "stone";
	}

	@Override
	public int getLayer() {
		return 1; //verificar se está correcto
	}
	
}
