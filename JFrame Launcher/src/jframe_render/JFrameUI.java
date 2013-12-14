package jframe_render;

import graphic.RendererOut;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import physics.Loop;


import controller.Player;

import assets.WH;

/*
 * All the Jframe is for is the UI
 * So why am I bringing in the players, game or even physics render
 * I will grab the render from the JFrame UI so I can paint on it
 * 
 */


public class JFrameUI extends JFrame implements RendererOut{
	Canvas dis;
	BufferStrategy strategy;
	
	public JFrameUI(String name){
		super(name);
	}
		
	public void setupUI(){
		JPanel jp = (JPanel) getContentPane();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setPreferredSize(new Dimension(500,500));
		setResizable(false);
		pack();

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public void ready(){
 		dis = new Canvas();
		dis.setBounds(0,0,500,500);
		getContentPane().add(dis);
		dis.setIgnoreRepaint(true);
		
		dis.createBufferStrategy(2);
		strategy = dis.getBufferStrategy();
	}
	

	
	public WH getGraphicBounds(){
		return new WH(dis.getBounds().width,dis.getBounds().height);
	}
	
	@Override
	public Graphics2D getDrawGraphics() {
		return (Graphics2D)strategy.getDrawGraphics();
	}
	
	public void graphicDoneCallback(){
		strategy.show();
	}

}
