package main;

import game.BS_to_Game;
import game.CurrentDefaultRenderer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import physics.Loop;




import websocket_manager.WebSocketPanel;

import com.google.gson.JsonObject;




import controller.Player;
import controller.PlayerManager;
import controller.Player_Panel;

import jframe_render.JFrameUI;
import jinput_manager.JinputPanel;

/*
 * I need to add callbacks to the input situation
 * 
 * So, Jinput checks if presaved devices exsist
 * -If they do use those devices and register inputs
 * -If not, go to add input device screen
 * 
 * Choose go online or play locally
 * 
 * If play online, 
 * -choose a game host
 * -choose a game room or create a game room
 * -start
 * 
 * On start
 * 
 * 
 * Read Json
 * -load in the appropiate classes
 * --Input, Graphic, Sound
 * 
 * 
 * 
 * 
 * 
 */

public class TheMain{
	private static JsonObject j;
	static PlayerManager plyman;
	public static JFrameUI frame;
	public static JButton cont = new JButton("Continue");
	
	

	public static void main(String[] args){
		j = new JsonObject();
		j.addProperty("graphic", "jframe_render");
		j.addProperty("sound", "");
		j.addProperty("input", "jinput_plugin");

		

		/*
		 * 
		 * Check if it has a panel, 
		 * -lets do this seperately
		 * 
		 * Game chooser
		 * -choose Game location
		 * 
		 * 
		 */
		
		
		frame = new JFrameUI("baby steps");
		frame.setupUI();
		
		
		cont.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					nextPanel();
				}catch(Exception e){
					e.printStackTrace();
					System.exit(0);
				}
			}
		});
		frame.getContentPane().add(cont);
		
		plyman = new PlayerManager(new Player_Panel[]{new JinputPanel()});
//		panels.add(new WebSocketPanel());

		/*
		 * Give people the choice of controller
		 * Have the person setup the buttons
		 * -disable continue until its all been completed
		 * After completed, allow person to press continue
		 * On continue, everything starts
		 * 
		 * 
		 * 
		 */
		try{
			firstPanel();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
		
	}//End Main Method
	
	private static void done(){
		frame.getContentPane().remove(cont);
		frame.ready();
//		game.players = 
		BS_to_Game game = new BS_to_Game();
		Loop loop = new Loop(game, frame, game.getRenderer());
		loop.setPlayers(plyman.getPlayers());
		Thread t = new Thread(loop);
		t.start();
	}

	private static void firstPanel() throws Exception{
		Player_Panel current = plyman.next();
		frame.getContentPane().add(current);
		current.setVisible(true);
		frame.pack();
	}
	
	private static void nextPanel() throws Exception{
		frame.getContentPane().remove(plyman.getCurrent());
		Player_Panel current = plyman.next();
		if(current == null){
			done();
		}else{
			frame.getContentPane().add(current);
			current.setVisible(true);
			frame.pack();
		}
	}
	
}//End Class


/*d

The way they have this setup is...
1) You have various menus and etc
2) You have


Renderer
-Finding current center
	-Get all Shapes within x*zoom width and y*zoom height
	-Use renderer of games choice to render it

-The Game
	-Menus
	-Videos/Animations
	-SpriteSheets
	-Sounds
	-Scenarios
		-Initialize Scenario
		-Player actions
		-End Scenario



The Way I want to set it up
-Graphic Manager-Sends Graphics to OS Manager
-Sound Manager
-Player Manager
-OS Manager-Displays everything appropiately

The Reality
-Creating a Graphic manager slows the system down by creating the picture before hand and then redrawing it
--Also makes system more resource intensive in that I will send the instance to another thread as a copy and not
	as "the" picture. I could make it static though right? still... Maybe I'm not sure
-Java sound is difficult to use
-Jinput is difficult to use, sockets aren't that simple
-OS Manager really is just graphic display at least in terms of if its windowed or not
--Though it might be useful in terms of knowing whether its Android right?... Not sure I know enough

Regardless...
Baby Steps....

Get Box 2d displayed
-Be able to manipulate box2d
--It will change live graphicly



*/