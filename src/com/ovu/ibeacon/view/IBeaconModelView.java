package com.ovu.ibeacon.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView extends JPanel {

	Color modelColor = Color.WHITE;
	TraiangleView traiView;
	private IBeaconModel ibeaconmodel = null;
	private JLabel distanceLabel = null;

	public IBeaconModelView(String modelName) {
		ibeaconmodel = new IBeaconModel();
		setIBeaconModelName(modelName);
		setLayout(null);
		setSize(200, 220);
		// 添加三角形图片
		traiView = new TraiangleView();
		traiView.setBounds(0, 0, traiView.getWidth(), traiView.getHeight());
		add(traiView);
		// 添加名字标签
		JLabel nameLabel = new JLabel(ibeaconmodel.getName(), JLabel.CENTER);
		nameLabel.setBounds(0, traiView.getHeight() - 65, traiView.getWidth(),
				20);
		nameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		add(nameLabel);
		// 添加距离标签
		distanceLabel = new JLabel();
		//设置文本中心对齐
		distanceLabel.setHorizontalAlignment(JLabel.CENTER);
		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
				20);
		distanceLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
//		distanceLabel.setVisible(false);
		add(distanceLabel);
		// 背景透明
		 setBackground(Color.RED);
		 setOpaque(false);
	}

	public void setDistanceLabel(double distance) {
		ibeaconmodel.setDistance(distance);
		distanceLabel.setText("Distance: "
				+ String.valueOf(ibeaconmodel.getDistance()));
	}

	public void setDistanceLabelVisible(boolean flag) {
		distanceLabel.setVisible(flag);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setIBeaconModelName(String name) {
		ibeaconmodel.setName(name);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 220);
	}

	public void setTrigger() {
		traiView.setTriggerFlag(true);
	}

	public void clearTrigger() {
		traiView.setTriggerFlag(false);
	}

}
