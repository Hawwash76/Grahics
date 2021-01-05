import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Sprite{

	private Point2D playerVelocity; // Players Velocity
	private boolean canJump;

	public Player() { // Creating the Player

		this.playerVelocity = new Point2D(0, 0);
		this.getProperties().put("alive", true);
		this.canJump = false;
		this.setTranslateX(0);
		this.setTranslateY(0);
//		this.setFill(Color.BLUE);
//		this.setStroke(Color.BLACK);
//		this.setWidth(150);
//		this.setHeight(150);
//		Sprite sprite = new Sprite();
		
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public Point2D getPlayerVelocity() {
		return playerVelocity;
	}

	public void setPlayerVelocity(Point2D playerVelocity) {
		this.playerVelocity = playerVelocity;
	}

	// ************** Getters And Setters
	// ***************************************************

	public void movePlayerX(int value, ArrayList<Node> platforms) { // Changing the players x-coordinates
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) { //for every pixel moved
			for (Node platform : platforms) {    // for every platform in the level
				
				if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) { //dealing with collision
					if (movingRight) {
						if (this.getTranslateX() + this.WIDTH == platform.getTranslateX()) {
							return;
						}
					} else {
						if (this.getTranslateX() == platform.getTranslateX() + 60) {
							return;
						}
					}
				}
			}
			
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
		}
	}
	
	public void movePlayerY(int value, ArrayList<Node> platforms) {// Changing the players x-coordinates
		boolean movingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {//for every pixel moved
			for (Node platform : platforms) { // for every platform in the level
				if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
					if (movingDown) {
						if (this.getTranslateY() + this.HEIGHT == platform.getTranslateY()) {
							this.setTranslateY(this.getTranslateY() - 1);
							canJump = true;
							return;
						}
					} else {
						if (this.getTranslateY() == platform.getTranslateY() + 60) {
							return;
						}
					}
				}
			}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
		}
	}



	public void jumpPlayer() {
		if (canJump) {
			jump();
			playerVelocity = playerVelocity.add(0, -30);
			canJump = false; // making sure the player wont jump wile jumping
		}
	}

}
