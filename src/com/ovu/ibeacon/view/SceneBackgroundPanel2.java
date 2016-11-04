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

	private IBeaconModelView2 modelView14;
	private IBeaconModelView2 modelView15;
	private IBeaconModelView2 modelView16;
	private IBeaconModelView2 modelView17;
	private IBeaconModelView2 modelView18;
	private IBeaconModelView2 modelView19;
	private IBeaconModelView2 modelView1A;
	private JPopupMenu popup;

	private Boolean btnPressFlag = false;
	
	private Point previousPoint;

	public void init() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		panelWidth = screenSize.width;
		panelHeight = screenSize.height;
	}

	/**
	 * 初始化节点
	 */
	public void initModels() {
//		modelView14 = new IBeaconModelView2("SN14");
//		modelView15 = new IBeaconModelView2("SN15");
//		modelView16 = new IBeaconModelView2("SN16");
		modelView17 = new IBeaconModelView2("SN17");
		modelView18 = new IBeaconModelView2("SN18");
		modelView19 = new IBeaconModelView2("SN19");
		modelView1A = new IBeaconModelView2("SN1A");
//		modelView1.setBounds(400, 50, modelView1.getWidth(), modelView1.getHeight());
//		modelView2.setBounds(700, 50, modelView2.getWidth(), modelView2.getHeight());
//		modelView3.setBounds(500, 50, modelView3.getWidth(), modelView3.getHeight());
		modelView17.setBounds(1300, 50, modelView17.getWidth(), modelView17.getHeight());
		modelView18.setBounds(550, 620, modelView18.getWidth(), modelView18.getHeight());
		modelView19.setBounds(550, 620, modelView19.getWidth(), modelView19.getHeight());
		modelView1A.setBounds(550, 620, modelView1A.getWidth(), modelView1A.getHeight());
//		modelView6.setBounds(550, 620, modelView6.getWidth(), modelView6.getHeight());
//		modelView7.setBounds(1150, 620, modelView7.getWidth(), modelView7.getHeight());
//		modelView8.setBounds(800, 650, modelView8.getWidth(), modelView8.getHeight());
//		modelView9.setBounds(1100, 650, modelView9.getWidth(), modelView9.getHeight());
//		modelView10.setBounds(1400, 650, modelView10.getWidth(), modelView10.getHeight());
	}

	public SceneBackgroundPanel2() {
		
		init();
		setLayout(null);
		setSize(panelWidth, panelHeight);
		initModels();
		initMemu();
		registerSN(modelView17, Utils.URL, Utils.PARAM17);
//		registerSN(modelView18, Utils.URL, Utils.PARAM18);
//		registerSN(modelView19, Utils.URL, Utils.PARAM19);
//		registerSN(modelView1A, Utils.URL, Utils.PARAM1A);
//		registerSN(modelView8, Utils.URL, Utils.PARAM);
//		registerSN(modelView9, Utils.URL, Utils.PARAM);
//		registerSN(modelView10, Utils.URL, Utils.PARAM);
		
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
	public void registerMenu(final IBeaconModelView2 modelView){
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
		
		registerMenu(modelView);
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
		ImageIcon imageIcon = new ImageIcon("images/background2.jpg");
		Image image = imageIcon.getImage();
		g.drawImage(image, 0, 0, panelWidth, panelHeight, null);
	}

}
