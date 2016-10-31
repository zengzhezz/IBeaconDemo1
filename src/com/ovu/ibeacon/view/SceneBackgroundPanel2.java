package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ovu.ibeacon.dao.HttpRequestDao;
import com.ovu.ibeacon.dao.HttpRequestDao2;
import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.Utils;

public class SceneBackgroundPanel2 extends JPanel {

	private int panelWidth;
	private int panelHeight;

	private IBeaconModelView2 modelView1;

	private Boolean btnPressFlag = false;

	public void init() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		panelWidth = screenSize.width;
		panelHeight = screenSize.height;
	}

	public void initModels() {
		modelView1 = new IBeaconModelView2("SN1");
		modelView1.setBounds(200, 30, modelView1.getWidth(),
				modelView1.getHeight());
		add(modelView1);
	}

	public SceneBackgroundPanel2() {
		
		init();
		setLayout(null);
		setSize(panelWidth, panelHeight);
		initModels();
		
		//初始化Http请求
		HttpRequestDao2 httpDao = new HttpRequestDao2(Utils.URL, Utils.PARAM2);
		//注册监听事件，超过范围采取相应的操作
		httpDao.setDataOutOfRangeListener(dataOutOfRangeListener);
	}
	
	//定义监听事件
	HttpRequestDao2.DataOutOfRangeListener dataOutOfRangeListener = new HttpRequestDao2.DataOutOfRangeListener() {

		@Override
		public void getClose(List<IBeaconModel> ib) {
			modelView1.showDistanceLabel(ib);
		}

		@Override
		public void getFar() {
			modelView1.showDistanceLabel(null);
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
