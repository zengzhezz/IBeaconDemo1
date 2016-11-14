package com.ovu.ibeacon.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.ovu.ibeacon.dao.HttpRequestDao2;
import com.ovu.ibeacon.model.IBeaconModel;
import com.ovu.ibeacon.utils.Utils;

/**
 * 以节点SN为参照点不动，测每个Beacon相对于节点的距离
 * @author zz
 *
 */
public class SceneBackgroundPanel2 extends JPanel {

	private int panelWidth;
	private int panelHeight;

	private IBeaconModelView2 modelView6C;
	private IBeaconModelView2 modelView6D;
	private IBeaconModelView2 modelView6E;
	private IBeaconModelView2 modelView6F;
	private IBeaconModelView2 modelView70;
	private IBeaconModelView2 modelView71;
	private IBeaconModelView2 modelView72;
	private IBeaconModelView2 modelView74;
	private JPopupMenu popup;
	
	private Image backgroundImage;
	
	private Boolean btnPressFlag = false;
	
	private Point previousPoint;

	public void init() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		panelWidth = screenSize.width;
		panelHeight = screenSize.height;
		//设置初始化背景
		backgroundImage = new ImageIcon("images/background.jpg").getImage();
	}

	/**
	 * 初始化节点
	 */
	public void initModels() {
//		modelView6C = new IBeaconModelView2("SN6C"); modelView6C.setBounds(400, 50, modelView6C.getWidth(), modelView6C.getHeight());
//		modelView6D = new IBeaconModelView2("SN6D"); modelView6D.setBounds(700, 50, modelView6D.getWidth(), modelView6D.getHeight());
//		modelView6E = new IBeaconModelView2("SN6E"); modelView6E.setBounds(500, 50, modelView6E.getWidth(), modelView6E.getHeight());
//		modelView6F = new IBeaconModelView2("SN6F"); modelView6F.setBounds(1300, 50, modelView6F.getWidth(), modelView6F.getHeight());
//		modelView70 = new IBeaconModelView2("SN70"); modelView70.setBounds(550, 620, modelView70.getWidth(), modelView70.getHeight());
		modelView74 = new IBeaconModelView2("SN74"); modelView74.setBounds(550, 620, modelView74.getWidth(), modelView74.getHeight());
			
	}

	public SceneBackgroundPanel2() {
		
		init();
		setLayout(null);
		setSize(panelWidth, panelHeight);
		initModels();
		initMemu();
//		registerSN(modelView6C, Utils.URL, Utils.PARAM6C);
//		registerSN(modelView6D, Utils.URL, Utils.PARAM6D);
//		registerSN(modelView6E, Utils.URL, Utils.PARAM6E);
//		registerSN(modelView6F, Utils.URL, Utils.PARAM6F);
//		registerSN(modelView70, Utils.URL, Utils.PARAM70);
		registerSN(modelView74, Utils.URL, Utils.PARAM74);
		
		registerBackGroundToMenu("研发区",new ImageIcon("images/background.jpg").getImage());
		registerBackGroundToMenu("公司",new ImageIcon("images/background2.jpg").getImage());
	}
	
	/**
	 * 初始化Menu函数
	 */
	public void initMemu(){
		popup = new JPopupMenu();
		setComponentPopupMenu(popup);
		
	}
	
	/**
	 * 注册SN到右键菜单
	 */
	public void registerModelToMenu(final IBeaconModelView2 modelView){
		JMenuItem popItem = new JMenuItem(modelView.getName());
		popItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modelView.isVisible())
					hideSN(modelView);
				else
					showSN(modelView);
			}
		});
		popup.add(popItem);
	}
	
	/**
	 * 注册背景到界面
	 * @param name
	 * @param image
	 */
	public void registerBackGroundToMenu(String name, final Image image){
		JMenuItem popItem = new JMenuItem(name);
		popItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backgroundImage = image;
				repaint();
			}
		});
		popup.add(popItem);
	}
	
	/**
	 * 注册SN，从服务器获取数据并设置监听事件
	 * @param modelView
	 * @param url
	 * @param param
	 */
	private void registerSN(final IBeaconModelView2 modelView, String url, String param){
		
		HttpRequestDao2 httpDao = new HttpRequestDao2(url, param);
		httpDao.setDataOutOfRangeListener(new HttpRequestDao2.DataOutOfRangeListener() {
			@Override
			public void getFar() {
				modelView.showDistanceLabel(null);
			}
			@Override
			public void getClose(List<IBeaconModel> ib) {
				modelView.showDistanceLabel(ib);
			}
			@Override
			public void timeOut(boolean flag, String lastUpdateTime) {
				modelView.setTimeOut(flag, lastUpdateTime);
				modelView.showDistanceLabel(null);
			}
		});
		
		modelView.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				previousPoint = e.getPoint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		
		modelView.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				Point newPoint = e.getPoint();
				Point p = modelView.getLocation();
				modelView.setLocation(p.x + newPoint.x - previousPoint.x, p.y + newPoint.y - previousPoint.y);
			}
		});
		
		registerModelToMenu(modelView);
		add(modelView);
		
	}
	
	/**
	 * 显示sn节点
	 * @param sn
	 */
	public void showSN(IBeaconModelView2 sn){
		sn.setVisible(true);
		sn.setLocation(500, 500);
	}
	
	/**
	 * 隐藏sn节点
	 * @param sn
	 */
	public void hideSN(IBeaconModelView2 sn){
		sn.setVisible(false);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, null);
	}

}
