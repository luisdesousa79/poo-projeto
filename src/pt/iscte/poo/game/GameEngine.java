package pt.iscte.poo.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import objects.BigFish;
import objects.GameCharacter;
import objects.SmallFish;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

	private Map<String, Room> rooms;
	private Room currentRoom;
	private int lastTickProcessed = 0;

	private boolean smallFishExited = false;
	private boolean bigFishExited = false;

	public GameEngine() {
		rooms = new HashMap<String, Room>();
		loadGame();
		currentRoom = rooms.get("room1.txt");
		updateGUI();
		SmallFish.getInstance().setRoom(currentRoom);
		BigFish.getInstance().setRoom(currentRoom);
	}
	
	

	private void loadGame() {
		File[] files = new File("./rooms").listFiles();
		for (File f : files) {
			rooms.put(f.getName(), Room.readRoom(f, this));
		}
	}

	// carrega o nível seguinte
	private void loadNextLevel() {
		/*
		 * limpar todos os tiles (clearImages)
		 * 
		 * limpar listas internas (objectList.clear())
		 * 
		 * ler room(N+1).txt
		 * 
		 * int level;
		 * 
		 * currentRoom = rooms.get("room" + level + ".txt");
		 * 
		 * repor contadores de movimentos
		 * 
		 * reposicionar gui
		 * updateGUI();
		 * 
		 * atualizar nível no status message
		 * 
		 */
	}

	// este é o método que trata das teclas e dos ticks
	@Override
	public void update(Observed source) {
		// pergunta à ImageGUI se alguma tecla foi pressionada
		if (ImageGUI.getInstance().wasKeyPressed()) {
			// lê a tecla carregada
			int k = ImageGUI.getInstance().keyPressed();

			// se carregarmos na tecla "espaço" (código 32), muda-se de peixe
			if (k == 32) {
				currentRoom.nextFish();
			}

			// se se carregar numa tecla de direcção, movimenta-se o peixe actual
			if (Direction.isDirection(k)) {
				GameCharacter fish = currentRoom.getActiveFish();
				fish.move(Direction.directionFor(k).asVector());
			}

			// salva quantos tiques ja teve
			int t = ImageGUI.getInstance().getTicks();

			// processa tick ate o tick atual, t
			while (lastTickProcessed < t) {
				processTick();

			}

			// chama o update da interface gráfica
			ImageGUI.getInstance().update();
		}
	}

	// incrementa o contador de ticks e aplica a gravidade aos objetos móveis
	private void processTick() {
		lastTickProcessed++;

		// implementa a gravidade nos objectos da Room

		currentRoom.applyGravity();
	}

	// reinicializa os objectos da interface gráfica
	public void updateGUI() {
		if (currentRoom != null) {
			ImageGUI.getInstance().clearImages();
			ImageGUI.getInstance().addImages(currentRoom.getObjects());
		}
	}

}
