package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

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
	
	@Override
	public boolean push(Vector2D dir) {
	    Point2D dest = getPosition().plus(dir);

	    // se o destino é holed wall, passa
	    if (getRoom().hasHoledWallAt(dest)) {
	        setPosition(dest);
	        return true;
	    }

	    // o resto igual ao push de LightObject
	    return super.push(dir);
	}

}
