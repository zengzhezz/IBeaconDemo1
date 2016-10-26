package com.ovu.ibeacon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ovu.ibeacon.model.IBeaconModel;

public class SceneBackgroundPanel extends JPanel{
	
	private int panelWidth;
	private int panelHeight;
	
	private IBeaconModel model1 = new IBeaconModel();
	private IBeaconModel model2 = new IBeaconModel();
	private IBeaconModel model3 = new IBeaconModel();
	private IBeaconModel model4 = new IBeaconModel();
	
	private IBeaconModelView modelView1;
	private IBeaconModelView modelView2;
	private IBeaconModelView modelView3;
	private IBeaconModelView modelView4;
	
	private Boolean btnPressFlag = false;
	
	public void init(){
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		panelWidth = screenSize.width;
		panelHeight = screenSize.height;
	}
	
	public void initModels(){
		model1.setName("SN1");
		model2.setName("SN2");
		model3.setName("SN3");
		model4.setName("SN4");
		modelView1 = new IBeaconModelView(model1);
		modelView2 = new IBeaconModelView(model2);
		modelView3 = new IBeaconModelView(model3);
		modelView4 = new IBeaconModelView(model4);
		modelView1.setBounds(100, 60, modelView1.getWidth(), modelView1.getHeight());
		modelView2.setBounds(panelWidth - 100 - modelView2.getWidth(), 60,
				modelView2.getWidth(), modelView2.getHeight());
		modelView3.setBounds(100, panelHeight - 120 - modelView3.getHeight(),
				modelView3.getWidth(), modelView3.getHeight());
		modelView4.setBounds(panelWidth - 100 - modelView4.getWidth(), panelHeight
				- 120 - modelView4.getHeight(), modelView4.getWidth(),
				modelView4.getHeight());
		add(modelView1);
		add(modelView2);
		add(modelView3);
		add(modelView4);
	}
	
	public SceneBackgroundPanel(){
		init();
//		setBackground(Color.GRAY);
		setLayout(null);
		setSize(panelWidth, panelHeight);
		initModels();
		JButton btn1 = new JButton("Change Color");
		btn1.setSize(200,100);
		btn1.setBounds(500,500,btn1.getWidth(), btn1.getHeight());
		add(btn1);
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton bt = (JButton) e.getSource();
				btnPressFlag = !btnPressFlag;
				if(btnPressFlag)
					modelView1.setColor(Color.RED);
				else
					modelView1.setColor(Color.WHITE);
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
}
