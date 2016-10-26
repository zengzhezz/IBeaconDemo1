package com.ovu.ibeacon.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView extends JPanel{
	
	Color modelColor = Color.WHITE;
	
	public IBeaconModelView(IBeaconModel ibeaconmodel){
		setSize(200, 200);
		setLayout(new BorderLayout());
		JLabel label = new JLabel(ibeaconmodel.getName(),JLabel.RIGHT);
		label.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		add(label, BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D rect = new Rectangle2D.Double(20,20,160,160);
		Ellipse2D ellipse = new Ellipse2D.Double();
		ellipse.setFrame(rect);
		g2.setStroke(new BasicStroke(10f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
		g2.draw(ellipse);
		g2.setPaint(modelColor);
		g2.fill(ellipse);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
	
	public void setColor(Color color){
		modelColor = color;
		repaint();
	}

}
