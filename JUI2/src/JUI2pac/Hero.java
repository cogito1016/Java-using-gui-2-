package JUI2pac;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//우주선의 정보를 담은 클래스 생성합니다.
public class Hero extends Sprite{


	
	//우주선을구현하는 클래스의생성자를 구현합니다.
	public Hero()
	{
		
		//생성자에서는 우주선의 이미지를 설정하고 예외처리로 종료하게끔 만듭니다.
	     try {
	        image = ImageIO.read(new File("SpaceShip.png"));
	     }catch (IOException e) {
	        e.printStackTrace();
	      }
	    //이것은 초기 우주선의 위치값을 설정한 것 입니다.
		x=500;
		y=400;
		
	}
	//움직임을 표현한 메소드 move입니다.
	public void move()
	{
		x+=dx;
		y+=dy;
	}

	//키를눌렀을때 발생하는 이벤트를 위한 클래스 KeyPressed입니다. 
	public void keyPressed(KeyEvent e)
	{
		//키를입력받으면 그 값을 keycode값에 정수형으로 저장합니다.
		int keycode=e.getKeyCode();

		/*그리고 스위치문으로 keycode를 분석하여 상하좌우, 그리고 Spacevbar를 판단합니다.
		 *상하좌우는 그방향으로 움직이기위함이고, SpaceBar는 미사일발사메소드 fire()의 값을
		 *호출하는것이 목적입니다.*/
		switch(keycode)
		{
		case KeyEvent.VK_LEFT: dx=-1; break;		
		case KeyEvent.VK_RIGHT: dx=1; break;			
		case KeyEvent.VK_UP: dy=-1; break;			
		case KeyEvent.VK_DOWN: dy=1; break;		
		//case KeyEvent.VK_SPACE: fire(); break;
		}
	
	}
	
	//키를눌렀다가 떼었을때 발생하는 이벤트를 위한 클래스 KeyReleased입니다. 
	public void keyReleased(KeyEvent e)
	{
		//키를입력받으면 그 값을 keycode값에 정수형으로 저장합니다.
		int keycode=e.getKeyCode();
		
		/*그리고 스위치문으로 keycode를 분석하여 상하좌우를  판단합니다.
		 *눌렀다가 떼었을 시 상하좌우 어느방향이든 그대로 멈추게하는 동작입니다. .*/
		switch(keycode)
		{
		case KeyEvent.VK_LEFT: dx=0; break;		
		case KeyEvent.VK_RIGHT: dx=0; break;			
		case KeyEvent.VK_UP: dy=0; break;			
		case KeyEvent.VK_DOWN: dy=0; break;
		}
	} 
}