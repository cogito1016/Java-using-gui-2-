package JUI2pac;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//우주선의 정보를 담은 클래스 생성합니다.
public class Hero extends Sprite{

	/*Application ArrayList Part
	 * Missile타입의 ArrayList를 선언해줍니다.*/
	ArrayList <Coin> mlist = new ArrayList<Coin>();

	//인덱스를선언합시다.
	private final int INDEX=0;
	
	
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
	
	/*Application ArrayList Part
	 * 미사일 객체를 발사하는 메소드 fire()을 호출시 
	 * 미사일 객체 필드 m을 하나만들고 
	 * 현재 우주선의 x,y값을 받는 Missile객체를 m에 넣어줍니다..
	 * 그리고 그 객체 m 을 미사일타입의 어레이리스트인 mlist에 add시켜줍니다.
	 * 그러면 성공적으로 arraylist mlist에는 발사된 미사일객체m이 새롭게 추가됩니다.*/
	public void fire(){
		Coin m;
		m=new Coin(x,y);
		mlist.add(m);
		
		/*원활한코드작성에 객체의정보를참조하기위해 리스트의크기와 객체정보를 출력하는 구문
		System.out.println("현재 리스트의크기 "+mlist.size());
		for(int j=0;j<mlist.size();j++)
			System.out.println("객체"+mlist.get(j)+"해당 객체의 위치좌표 = "+mlist.get(j).x+" "+mlist.get(j).y);
		*/
		}
	
	/*Application ArrayList Part
	 * 미사일의 정보를 반환하는 getMissile()메소드입니다.
	 * 평소에는 객체하나 'm'밖에없어서 그것만 반환하였었지만
	 * 이제는 ArrayList이니 리스트안에있는 최신 요소의 값을반환하는걸로 메소드를 수정해줄 것 입니다.
	 * If문에대한설명
	 * 		If문은 mlist에 m이라는객체가 있는지 없는지를 size()메소드를 통해 식별합니다.
	 * 		mlist에 객체m이 없는상태인데 객체를 반환할수는없기때문입니다.*/
	public Coin getMissile() {
		if(mlist.size()>0)
				return mlist.get(mlist.size()-1);
		else
			return null;
		}
	
	/*Application ArrayList Part
	 *미사일객체가 y값을넘어가면없어져야합니다.
	 *이는 미사일타입의 어레이리스트인 mlist에서 객체를 삭제해주는것으로 동작을구현합니다.
	 *삭제는 arraylist의 노드삭제메소드인 remove()를 사용합니다.
	 * If문에대한설명
	 * 		If문은 mlist에 m이라는객체가 있는지 없는지를 size()메소드를 통해 식별합니다.
	 * 		mlist에 객체m이 없는상태인데 객체를 반환할수는없기때문입니다.*/
	public void rmMisobj() {
		if(mlist.size()>0){mlist.remove(INDEX);}
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
		case KeyEvent.VK_SPACE: fire(); break;
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