package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buoy extends LightObject implements Sinkable {
	
	public Buoy(Room room) {
		super(room);
		
	}
	
	@Override
	public String getName() {
		return "buoy";
	}
	
	@Override
	public int getLayer() {
		return 1; //qual é a Layer do Buoy?
	}
	
	
	
	@Override
	public void sinks() {
		
		Point2D above = this.getPosition().plus(Direction.UP.asVector());
		Point2D below = this.getPosition().plus(Direction.DOWN.asVector());
		
		// se tiver um objeto móvel em cima, desce de posição(afunda)
		// será que tambémm tenho de mudar o applyGravity() para afundarem em bloco?
		if (getRoom().hasMovableAt(above)) {
			this.setPosition(below);
		}
		
	}
	
	
	
}
