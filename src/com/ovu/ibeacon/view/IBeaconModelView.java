package com.ovu.ibeacon.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView extends JPanel{
	
	IBeaconModel ibeaconmodel;
	
	public IBeaconModelView(){
		setSize(200, 200);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D rect = new Rectangle2D.Double(20,20,160,160);
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.draw(ellipse);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

}
