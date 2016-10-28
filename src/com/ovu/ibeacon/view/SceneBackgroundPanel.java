package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ovu.ibeacon.model.IBeaconModel;

public class SceneBackgroundPanel extends JPanel {

	private int panelWidth;
	private int panelHeight;

	private IBeaconModelView modelView1;
	private IBeaconModelView modelView2;
	private IBeaconModelView modelView3;
	private IBeaconModelView modelView4;

	private Boolean btnPressFlag = false;

	public void init() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		panelWidth = screenSize.width;
		panelHeight = screenSize.height;
	}

	public void initModels() {
		modelView1 = new IBeaconModelView("SN1");
		modelView2 = new IBeaconModelView("SN2");
		modelView3 = new IBeaconModelView("SN3");
		modelView4 = new IBeaconModelView("SN4");
		modelView1.setBounds(200, 30, modelView1.getWidth(),
				modelView1.getHeight());
		modelView2.setBounds(panelWidth - 250 - modelView2.getWidth(), 180,
				modelView2.getWidth(), modelView2.getHeight());
		modelView3.setBounds(panelWidth/2-50, 320,
				modelView3.getWidth(), modelView3.getHeight());
		modelView4.setBounds(panelWidth - 400 - modelView4.getWidth(),
				panelHeight - 50 - modelView4.getHeight(),
				modelView4.getWidth(), modelView4.getHeight());
		add(modelView1);
		add(modelView2);
		add(modelView3);
		add(modelView4);
	}

	public SceneBackgroundPanel() {
		
		init();
		setLayout(null);
		setSize(panelWidth, panelHeight);
		initModels();
			
		JSlider slider = new JSlider(0, 100, 50);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider innerSlider = (JSlider) e.getSource();	
				int value = innerSlider.getValue();
				if(value<20){
					//打开动画效果
					modelView1.setTrigger();
					//打开距离显示
					modelView1.setDistanceLabel(value);
					modelView1.setDistanceLabelVisible(true);
				}else{
					modelView1.clearTrigger();
					modelView1.setDistanceLabelVisible(false);
				}
			}
		});
		
		slider.setBounds(800,600,200,100);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setOpaque(false);
		add(slider);
		
//		//初始化Http请求
//		HttpRequestDao httpDao = new HttpRequestDao();
//		//注册监听事件，超过范围采取相应的操作
//		httpDao.setDataOutOfRangeListener(new HttpRequestDao.DataOutOfRangeListener() {
//			
//			@Override
//			public void getFar() {
//				modelView1.setTrigger();;
//			}
//			
//			@Override
//			public void getClose() {
//				modelView1.clearTrigger();;
//			}
//		});
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon imageIcon = new ImageIcon("images/background.jpg");
		Image image = imageIcon.getImage();
		g.drawImage(image, 0, 0, panelWidth, panelHeight, null);
	}

}
