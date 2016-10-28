package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
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
		// 设置文本中心对齐
		distanceLabel.setHorizontalAlignment(JLabel.CENTER);
		// 设置位置
		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
				20);
		// 设置字体
		distanceLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		// 设置背景白色
		distanceLabel.setBackground(Color.WHITE);
		// 设置边框黑色
		distanceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// 重要，设置为true白色背景才能显示
		distanceLabel.setOpaque(true);
		// 初始状态不显示
		distanceLabel.setVisible(false);
		add(distanceLabel);
		// 背景透明
		setOpaque(false);
	}

	/**
	 * 设置距离标志值
	 * @param distance
	 */
	public void setDistanceLabel(double distance) {
		ibeaconmodel.setDistance(distance);
		distanceLabel.setText("Distance: "
				+ String.valueOf(ibeaconmodel.getDistance()));
	}

	/**
	 * 设置距离标签是否可见，true可见，false不可见
	 * @param flag
	 */
	public void setDistanceLabelVisible(boolean flag) {
		distanceLabel.setVisible(flag);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * 设置该IBeaconView里的IBeacon对象的name
	 * @param name
	 */
	public void setIBeaconModelName(String name) {
		ibeaconmodel.setName(name);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 220);
	}

	/**
	 * 打开靠近时的动画效果
	 */
	public void setTrigger() {
		traiView.setTriggerFlag(true);
	}

	/**
	 * 关闭靠近时的动画效果
	 */
	public void clearTrigger() {
		traiView.setTriggerFlag(false);
	}

}
