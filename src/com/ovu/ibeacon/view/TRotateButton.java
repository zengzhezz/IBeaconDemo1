package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TRotateButton extends JPanel {
	
	private static double theta = 0;

	public TRotateButton() {
		setSize(200,200);
		setBackground(Color.BLUE);
		theta = Math.toRadians(theta);
	}
	
	public void createAnim(){
		Timer timer = new Timer();
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				setAngle(theta);
				theta = theta + 10;
			}		
		};
		timer.schedule(task, 0, 100);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth(), h = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		// Ïû³ý¾â³Ý
//		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//		g2d.setRenderingHints(renderingHints);
		g2d.rotate(theta, w / 2, h / 2);
	}
	
	public void setAngle(double theta){
		this.theta = theta;
		repaint();
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame("²âÊÔÐý×ª°´Å¥");
		jf.setSize(800, 800);
		TRotateButton btn = new TRotateButton();
		btn.createAnim();
		btn.setBounds(100,100,200,200);
		jf.setLayout(null);
		jf.add(btn);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
