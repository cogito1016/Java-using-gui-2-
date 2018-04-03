package JUI2pac;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

/*20144675김재형
 * 
 * 가로로 진행하는 게임을 만들어보는 과제이다. 
 * 
 * 주인공이 있고, 배경이있고, 코인과 점수판, 그리고 주인공이 밟을 수 있는 땅을
 * 그려내어 가로로 진행하는 게임을 만들 예정입니다.
*/

//이제 프레임을 그려주는 클래스를 구현합니다.
public class MainFrame extends JFrame
{
	/*
	class MyThread extends Thread{

		private JLabel label;
		private int x,y;
		public MyThread(String fname, int x, int y)
		{
			this.x=x;
			this.y=y;
			label = new JLabel();
			label.setIcon(new ImageIcon(fname));
			label.setBounds(x,y,100,100);
			add(label);
		}
		public void run()
		{
			for(int i =0; i<200; i++)
			{
				x+=10*Math.random();
				label.setBounds(x,y,300,300);
				repaint();
				try {
					Thread.sleep(100);
				}catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	*/
	
	//프레임의생성자입니다.
	public MainFrame()
	{
		/*우주배경화면의 객체를 추가시킵니다.
		 *제목을 설정하고 X를누를시 종료하는것을 설정해줍니다.
		 *Pack은 압축하여 올려주는것을 의미합니다.
		 *SetVisible(True)로서 화면에 이를 표시합니다.*/
		add(new BackGround());
		setTitle("Catch The Coin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		(new MyThread("missile.png",100,0)).start();
		(new MyThread("missile.png",100,50)).start();
		(new MyThread("missile.png",100,100)).start();
		*/
		
		pack();
		
		setVisible(true);
		
	}
	
	//메인메소드에서는 프레임객체를 만들어줌으로서 프로그램을 실행시킵니다.
	public static void main(String[] args) {
		MainFrame ex = new MainFrame();
	}
}

