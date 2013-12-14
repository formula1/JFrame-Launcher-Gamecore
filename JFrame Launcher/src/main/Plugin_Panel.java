package main;

import javax.swing.JPanel;

import abstracts.Game;

public abstract class Plugin_Panel extends JPanel{

	public abstract JPanel getPanel();
	
	public abstract void dontuse();
	public abstract void close(Game game);
}
