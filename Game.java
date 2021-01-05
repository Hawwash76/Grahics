import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Game extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	ArrayList<Node> platforms = new ArrayList<Node>(); // A list that consists of every block
	
	
	Levels levels=new Levels();
	Player player;
	Pane appRoot = new Pane();
	Pane gameRoot = new Pane();
	Pane uiRoot = new Pane();
	int levelWidth;
	boolean running = true;
	boolean dialogEvent = false;
	HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>(); // A hashmap lnking the keys pressed

	@Override
	public void start(Stage stage)throws Exception {

		buildLevel(); // Build the level
		Scene scene = new Scene(appRoot);
		scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
		stage.setScene(scene);
		stage.show();

		AnimationTimer timer = new AnimationTimer() { // The animation, calls the update function 
			@Override                                 // as long as the game is running
			public void handle(long now) {
				if (running) {
					update();
				}
				
				if (dialogEvent) {
                    dialogEvent = false;
                    keys.keySet().forEach(key -> keys.put(key, false));

                    GameQuestion dialog = new GameQuestion();
                    dialog.setOnCloseRequest(event -> {
                        running = true;
                    });
                    dialog.open();
                }

			}
		};
		timer.start();

	} 

	public void buildLevel() {

		Rectangle bg = new Rectangle(1280, 720);
		bg.setFill(Color.WHITE); // The BackGround
		levelWidth = levels.LEVEL1.length * 260; // Length of the stage, the bigger the longer the level will be

		for (int i = 0; i < levels.LEVEL1.length; i++) {     // For loop to iterate through the level string,
			String line = levels.LEVEL1[i];                  // for each '1' it will create a block using the createEntity
			for (int j = 0; j < line.length(); j++) { // method.
				switch (line.charAt(j)) {
				case '0':
					break;
				case '1':
					Node platform = createEntity(j * 60, i * 60, 60, 60, Color.GREEN, gameRoot);
					platforms.add(platform);
					break;

				}
			}
		}

		player = new Player();
		gameRoot.getChildren().add(player);
		
		player.translateXProperty().addListener((obs, old, newValue) -> { // Moving the stage when the player
			int offset = newValue.intValue();                             // reaches a certain x-coordinate on the map
			if (offset > 540 && offset < levelWidth - 540) {              // (540 in this case)
				gameRoot.setTranslateX(-(offset - 540));
			}
		});
		appRoot.getChildren().addAll(bg,gameRoot, uiRoot);

	}

	public Node createEntity(int x, int y, int w, int h, Color color, Pane pane) { 
		// Method to create entity and placing them on the level
		Rectangle entity = new Rectangle(w, h);
//		entity.setTranslateX(x);
//		entity.setTranslateY(y);
		entity.setTranslateX(x);
		entity.setTranslateY(y);
		entity.setFill(color);
		pane.getChildren().add(entity);
		return entity;
	}

	private void update() { // Moving the Player, will be used in the animation
		
		if ((isPressed(KeyCode.W) || isPressed(KeyCode.SPACE)) && player.getTranslateY() >= 5) {
			player.jumpPlayer();
		}

		if (isPressed(KeyCode.A) && player.getTranslateX() >= 5) {
			player.movePlayerX(-5, platforms);
			player.walk();

			if (isPressed(KeyCode.SHIFT) && player.getTranslateX() + 40 <= levelWidth - 5) {
				player.movePlayerX(-6, platforms);
			}
		}


		if (isPressed(KeyCode.D) && player.getTranslateX() + 40 <= levelWidth - 5) {
			player.movePlayerX(5, platforms);
			player.walk();
			if (isPressed(KeyCode.SHIFT) && player.getTranslateX() + 40 <= levelWidth - 5) {
				player.movePlayerX(6, platforms);
			}
		}

		if (player.getPlayerVelocity().getY() < 10) {
			player.setPlayerVelocity(player.getPlayerVelocity().add(0, 1));
		}
		player.movePlayerY((int) player.getPlayerVelocity().getY(), platforms);

	}

	public boolean isPressed(KeyCode key) { // A method to know if a key is pressed
		return keys.getOrDefault(key, false);
	}
}

