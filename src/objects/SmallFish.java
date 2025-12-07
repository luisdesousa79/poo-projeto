package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class SmallFish extends GameCharacter {

	private static SmallFish sf = new SmallFish(null);
	
	private SmallFish(Room room) {
		super(room);
	}

	public static SmallFish getInstance() {
		return sf;
	}
	
	@Override
	public String getName() {
		return "smallFishLeft";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	// função que diz se o peixe consegue ou não empurrar o objeto
	@Override
	public boolean canPush(Pushable obj, Vector2D dir) {
		
		// no caso de um empurrão na vertical, devolve falso
		if (dir.getY() != 0) {
	        return false;
	    }

	    // se não for um objeto leve, retorna falso
	    if (!(obj instanceof LightObject)) {
	        return false;
	    }
	    
	    // se não houver apenas água na direção do empurrão, o peixe pequeno não consegue empurrar
	    
	    Point2D objectPosition = ((GameObject) obj).getPosition();
	    Point2D nextToObject = objectPosition.plus(dir);
	    
	    // empede que o peixe empurre 2 coisas ao msm tempo
	    if (getRoom().hasMovableAt(nextToObject)) {
			return false;
		}
	    
	    if(!getRoom().isOnlyWaterAt(nextToObject)) {
	    	if(getRoom().getElementAt(objectPosition) instanceof Cup && getRoom().hasHoledWallAt(nextToObject)) {
	    		return true;
	    	}
	    	
	    	return false;
	    }
	    
	
	    // em qualquer outro caso, retorna verdadeiro
	    return true;
	}

}
