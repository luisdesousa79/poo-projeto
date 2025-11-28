package objects;

import pt.iscte.poo.game.Room;

public class Bomb extends LightObject {
	
	public Bomb(Room room) {
		super(room);
	}

	@Override
	public String getName() {
		return "bomb";
	}

	@Override
	public int getLayer() {
		return 1; //verificar
	}
	
	public void falls() {
		explode();
	}
	
	//quando chamado, a bomba explode
	public void explode() {
		//inspeciona tudo o que estiver nas quatro posições adjacentes e remove-o
		//se um dos elementos das quatro posições adjacentes for um peixe, chama dies()
	}
	
	

}
