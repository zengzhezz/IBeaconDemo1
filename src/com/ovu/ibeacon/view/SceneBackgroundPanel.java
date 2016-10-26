package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SceneBackgroundPanel extends JPanel{
	
	public SceneBackgroundPanel(){
		setBackground(Color.GRAY);
		add(new IBeaconModelView());
		add(new IBeaconModelView());
		add(new IBeaconModelView());
		add(new IBeaconModelView());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
