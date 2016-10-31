package com.ovu.ibeacon.test;

import java.awt.EventQueue;

import com.ovu.ibeacon.view.SceneBackgroudFrame;
import com.ovu.ibeacon.view.SceneBackgroundPanel;
import com.ovu.ibeacon.view.SceneBackgroundPanel2;

public class Test {

	public static void main(String[] args) {
//		String s = HttpRequest.sendGet(
//				"http://120.55.95.87/api//v1/application/data",
//				"mac=404c4968060001df&token=70B3D5FFFE88B03A&appeui=0102030405060708");
//		System.out.println(s);
		
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				SceneBackgroudFrame sbg = new SceneBackgroudFrame();
				sbg.setContentPane(new SceneBackgroundPanel2());
//				sbg.setContentPane(new JButton("Change Color"));
			}
			
		});
		
	}

}
