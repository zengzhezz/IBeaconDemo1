package com.ovu.ibeacon.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

public class SceneBackgroudFrame extends JFrame{
	
	private int width;
	private int height; 
	
	/**
	 * 无参构造函数，默认宽高为显示器的宽高
	 */
	public SceneBackgroudFrame(){
		//得到显示器的宽高
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		width = screenSize.width;
		height = screenSize.height;
		initFrame();
	}
	
	/**
	 * 含参构造函数，可以设置窗口的宽高
	 * @param width
	 * @param height
	 */
	public SceneBackgroudFrame(int width, int height){
		this.width = width;
		this.height = height;
		initFrame();
	}
	
	/**
	 * Frame初始化操作
	 */
	private void initFrame(){
		setSize(width,height);
		setTitle("IbeaconDemo1");
		setBackground(Color.BLUE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		isResizable();
	}

}
