package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.ovu.ibeacon.model.IBeaconModel;

public class IBeaconModelView2 extends JPanel {

	Color modelColor = Color.WHITE;
	TraiangleView traiView;
//	private List<IBeaconModel> ibeaconList = null;
	private JLabel distanceLabel = null;

	public IBeaconModelView2(String uuid) {
//		ibeaconList = new ArrayList<IBeaconModel>();
		setLayout(null);
		setSize(200, 400);
		// 添加三角形图片
		traiView = new TraiangleView();
		traiView.setBounds(0, 0, traiView.getWidth(), traiView.getHeight());
		add(traiView);
		// 添加名字标签
		JLabel nameLabel = new JLabel("SN1", JLabel.CENTER);
		nameLabel.setBounds(0, traiView.getHeight() - 65, traiView.getWidth(),
				20);
		nameLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		add(nameLabel);
		
		setBackground(Color.RED);
		
		// 添加距离标签
		distanceLabel = new JLabel();
		// 设置文本中心对齐
		distanceLabel.setHorizontalAlignment(JLabel.CENTER);
		// 设置位置
		distanceLabel.setBounds(0, traiView.getHeight(), traiView.getWidth(),
				120);
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
		//显示距离标签
		//showDistanceLabel();
	}

	
	/**
	 * 显示距离标签，如果ibeaconList为空则不显示距离和动画，否则显示ibeaconList里的相应信息并打开动画
	 * @param ibeaconList
	 */
	public void showDistanceLabel(List<IBeaconModel> ibeaconList) {
		if(null != ibeaconList && ibeaconList.size() != 0){
			StringBuilder s = new StringBuilder();
			s.append("<html>");
			for (IBeaconModel iBeaconModel : ibeaconList) {
				s.append(iBeaconModel.getUuid() + " ");
				s.append("Distance: " + String.format("%.2f", iBeaconModel.getDistance()) + "<br> ");
			}
			s.append("</html>");
			distanceLabel.setText(s.toString());
			distanceLabel.setVisible(true);
			setTrigger();
		}else{
			distanceLabel.setVisible(false);
			clearTrigger();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 400);
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
	
	public static void main(String[] args) {
		SceneBackgroudFrame sbg = new SceneBackgroudFrame();
		IBeaconModelView2 b2 = new IBeaconModelView2("momo");
		sbg.add(b2);
	}

}
