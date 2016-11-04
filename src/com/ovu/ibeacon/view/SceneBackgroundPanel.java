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

import com.ovu.ibeacon.dao.HttpRequestDao;
import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.Utils;

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
		modelView1 = new IBeaconModelView("00000001");
		modelView2 = new IBeaconModelView("00000002");
		modelView3 = new IBeaconModelView("00000003");
		modelView4 = new IBeaconModelView("00000004");
		modelView1.setBounds(200, 30, modelView1.getWidth(),
				modelView1.getHeight());
		modelView2.setBounds(panelWidth - 250 - modelView2.getWidth(), 180,
				modelView2.getWidth(), modelView2.getHeight());
		modelView3.setBounds(panelWidth/2-50, 320,
				modelView3.getWidth(), modelView3.getHeight());
		modelView4.setBounds(panelWidth - 400 - modelView4.getWidth(),
				panelHeight - 80 - modelView4.getHeight(),
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
					modelView2.setTrigger();
					//打开距离显示
					modelView2.setDistanceLabel(value);
					modelView2.setDistanceLabelVisible(true);
				}else{
					modelView2.clearTrigger();
					modelView2.setDistanceLabelVisible(false);
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
		
		//初始化Http请求
		HttpRequestDao httpDao = new HttpRequestDao(Utils.URL, Utils.PARAM6C);
		//注册监听事件，超过范围采取相应的操作
		httpDao.setDataOutOfRangeListener(dataOutOfRangeListener);
		
	}
	
	HttpRequestDao.DataOutOfRangeListener dataOutOfRangeListener = new HttpRequestDao.DataOutOfRangeListener() {
		
		@Override
		public void getFar(IBeaconModel ib) {
			if(ib.getUuid().equals(modelView1.getIBeaconModelUuid())){
				modelView1.clearTrigger();
				modelView1.setDistanceLabelVisible(false);
			}else if(ib.getUuid().equals(modelView2.getIBeaconModelUuid())){
				modelView2.clearTrigger();
				modelView2.setDistanceLabelVisible(false);
			}else if(ib.getUuid().equals(modelView3.getIBeaconModelUuid())){
				modelView3.clearTrigger();
				modelView3.setDistanceLabelVisible(false);
			}
			else if(ib.getUuid().equals(modelView4.getIBeaconModelUuid())){
				modelView4.clearTrigger();
				modelView4.setDistanceLabelVisible(false);
			}
		}
		
		@Override
		public void getClose(IBeaconModel ib) {
			if(ib.getUuid().equals(modelView1.getIBeaconModelUuid())){
				//打开动画效果
				modelView1.setTrigger();
				//打开距离显示
				modelView1.setDistanceLabel(ib.getDistance());
				modelView1.setDistanceLabelVisible(true);
			}else if(ib.getUuid().equals(modelView2.getIBeaconModelUuid())){
				//打开动画效果
				modelView2.setTrigger();
				//打开距离显示
				modelView2.setDistanceLabel(ib.getDistance());
				modelView2.setDistanceLabelVisible(true);
			}else if(ib.getUuid().equals(modelView3.getIBeaconModelUuid())){
				//打开动画效果
				modelView3.setTrigger();
				//打开距离显示
				modelView3.setDistanceLabel(ib.getDistance());
				modelView3.setDistanceLabelVisible(true);
			}
			else if(ib.getUuid().equals(modelView4.getIBeaconModelUuid())){
				//打开动画效果
				modelView4.setTrigger();
				//打开距离显示
				modelView4.setDistanceLabel(ib.getDistance());
				modelView4.setDistanceLabelVisible(true);
			}
		}
	};

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon imageIcon = new ImageIcon("images/background.jpg");
		Image image = imageIcon.getImage();
		g.drawImage(image, 0, 0, panelWidth, panelHeight, null);
	}

}
