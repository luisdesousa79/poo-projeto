package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Anchor extends HeavyObject {
	
	private boolean alreadyPushed = false;
	
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
	
	public boolean setPushed() {
		alreadyPushed = true;
	}
	
	@Override
	public boolean push(Vector2D dir) {

		// Não pode ser movida na vertical
	    if (dir.getY() != 0) {
	        return false;
	    }
	    
	    // Na horizontal, só se pode mover uma posição. 
	    
	    if (!alreadyPushed) {
	    	
	    	// vai começar por ver se há apenas água na posição para onde está a ser
			// empurrado
			Point2D newPos = getPosition().plus(dir);

			// se houver apenas água no destino, move-se para essa posição
			if (getRoom().isOnlyWaterAt(newPos)) {
				setPosition(newPos);
				// devolve verdadeiro caso seja empurrado
				return true;
			}

			// se houver um objecto na direção para onde está a ser empurrado
			// vai ver se esse objeto é móvel
			// se o objeto for móvel, vai tentar empurrá-lo
			if (getRoom().hasMovableAt(newPos)) {

				MovableObject nextObject = (MovableObject) getRoom().getElementAt(newPos);

				// se  houver mais do que objeto na horizontal na
				// direção do empurrão, empurra-os em cadeia

				boolean pushed = nextObject.push(dir);

				if (pushed) {
					setPosition(newPos);
					setPushed();
					return true;
				}
				return false;
			}

	    
		
	    

	    else return;
	    

	}

}
