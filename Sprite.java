
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends ImageView{
	
	
	final int HEIGHT = 80;
	final int WIDTH = 50;
	private int imageIndex = 0 ;
	private final int frameTime = 100 ; // milliseconds
	ImageView imageView = new ImageView();
	private ArrayList<Image> images = new ArrayList<Image>();
	public Timeline idel;
	
	int imageIndexJump =0;
	private ArrayList<Image> jumpImgs = new ArrayList<Image>();
	public Timeline jump;
	
	int imageIndexWalk =0;
	private ArrayList<Image> walkImgs = new ArrayList<Image>();
	public Timeline walk;
	boolean canWalk = true;
	
	String[] imgPath = {
	"./resources/character/Chara - BlueIdle00000.png",
	"./resources/character/Chara - BlueIdle00001.png",
	"./resources/character/Chara - BlueIdle00002.png",
	"./resources/character/Chara - BlueIdle00003.png",
	"./resources/character/Chara - BlueIdle00004.png",
	"./resources/character/Chara - BlueIdle00005.png",
	"./resources/character/Chara - BlueIdle00006.png",
	"./resources/character/Chara - BlueIdle00007.png",
	"./resources/character/Chara - BlueIdle00008.png",
	"./resources/character/Chara - BlueIdle00009.png",
	"./resources/character/Chara - BlueIdle00010.png",
	"./resources/character/Chara - BlueIdle00011.png",
	"./resources/character/Chara - BlueIdle00012.png",
	"./resources/character/Chara - BlueIdle00013.png",
	"./resources/character/Chara - BlueIdle00014.png",
	"./resources/character/Chara - BlueIdle00015.png",
	"./resources/character/Chara - BlueIdle00016.png",
	"./resources/character/Chara - BlueIdle00017.png",
	"./resources/character/Chara - BlueIdle00018.png",
	"./resources/character/Chara - BlueIdle00019.png"};
	
	String[] jumpPath = {
			"./resources/character/jump/CharaWizardJump_00000.png",
			"./resources/character/jump/CharaWizardJump_00001.png",
			"./resources/character/jump/CharaWizardJump_00002.png",
			"./resources/character/jump/CharaWizardJump_00003.png",
			"./resources/character/jump/CharaWizardJump_00004.png",
			"./resources/character/jump/CharaWizardJump_00005.png",
			"./resources/character/jump/CharaWizardJump_00006.png",
			"./resources/character/jump/CharaWizardJump_00007.png"
	};
	
	String[] walkPath = {
			"./resources/character/walk/Chara_BlueWalk00000.png",
			"./resources/character/walk/Chara_BlueWalk00001.png",
			"./resources/character/walk/Chara_BlueWalk00002.png",
			"./resources/character/walk/Chara_BlueWalk00003.png",
			"./resources/character/walk/Chara_BlueWalk00004.png",
			"./resources/character/walk/Chara_BlueWalk00005.png",
			"./resources/character/walk/Chara_BlueWalk00006.png",
			"./resources/character/walk/Chara_BlueWalk00007.png",
			"./resources/character/walk/Chara_BlueWalk00008.png",
			"./resources/character/walk/Chara_BlueWalk00009.png",
			"./resources/character/walk/Chara_BlueWalk00010.png",
			"./resources/character/walk/Chara_BlueWalk00011.png",
			"./resources/character/walk/Chara_BlueWalk00012.png",
			"./resources/character/walk/Chara_BlueWalk00013.png",
			"./resources/character/walk/Chara_BlueWalk00014.png",
			"./resources/character/walk/Chara_BlueWalk00015.png",
			"./resources/character/walk/Chara_BlueWalk00016.png",
			"./resources/character/walk/Chara_BlueWalk00017.png",
			"./resources/character/walk/Chara_BlueWalk00018.png",
			"./resources/character/walk/Chara_BlueWalk00019.png"
	};
	public Sprite() {
		init();
	}
	
	private void loadImages() 
	{
		for(String path : imgPath) {
			Image image = new Image(path);
			images.add(image);
		}
		
		for(String path : jumpPath) {
			Image image = new Image(path);
			jumpImgs.add(image);
		}
		
		for(String path : walkPath) {
			Image image = new Image(path);
			walkImgs.add(image);
		}
	}
	
	private void init() 
	{
		loadImages();
		setCharacterSize();	
		initBreathe();
		initWalk();
		initJump();
		breatheAnimation();
	}
	
	private void setCharacterSize()
	{
		setFitHeight(HEIGHT);
		setFitWidth(WIDTH);
	}
	
	private void initBreathe() {
		idel = new Timeline(new KeyFrame(Duration.millis(frameTime), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				setImage(images.get(imageIndex++));
			}
		}));
		idel.setCycleCount(images.size());
	}
	public void breatheAnimation() 
	{
		idel.play();
		idel.setOnFinished(e->{
			imageIndex = 0;
		});
	}
	
	private void initJump() 
	{
		jump = new Timeline(new KeyFrame(Duration.millis(frameTime/2), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				setImage(jumpImgs.get(imageIndexJump++));
			}
		}));
		jump.setCycleCount(jumpImgs.size());
	}
	
	public void jump() 
	{
		jump.play();
		jump.setOnFinished(e->{
			imageIndexJump= 0;
		});
	}
	
	private void initWalk() {
		walk = new Timeline(new KeyFrame(Duration.millis(frameTime/4), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				setImage(walkImgs.get(imageIndexWalk++));
			}
		}));
		walk.setCycleCount(walkImgs.size());
	}
	
	public void walk() {
		if (canWalk) {
			canWalk = false;
			walk.play();
			walk.setOnFinished(e -> {
				imageIndexWalk = 0;
				canWalk = true;
			});
		}
	}
	
	
}
