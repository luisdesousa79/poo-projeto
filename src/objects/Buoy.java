package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buoy extends LightObject implements Sinkable, Buoyable {
	
	public Buoy(Room room) {
		super(room);
		
	}
	
	@Override
	public String getName() {
		return "buoy";
	}
	
	@Override
	public int getLayer() {
		return 2; //qual é a Layer do Buoy?
	}
	
	@Override
	public void buoys() {
		//deve boiar exceto se estiver a suportar qualquer objeto móvel
		Point2D above = this.getPosition().plus(Direction.UP.asVector());
		if (getRoom().isOnlyWaterAt(above)) {
			this.setPosition(above);
		}
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
