package JUI2pac;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//미사일의 정보가있는 클래스입니다.
public class Cheese extends Sprite{
	//미사일스피드를 정하는 필드를 선언합니다 초기값은 2입니다.
	private final int MISSILE_SPEED = 2;
	
	/*Application ArrayList Part
	 * 객체가 화면을벗어나면 삭제되느것을 구현하기위한 spaceship메소드를사용하기위해 spaceship변수 ship을 선언합니다.*/
	
	//미사일클래스의 생성자를 선언합니다 미사일클래스는 매개변수 x와y를 받습니다.
	public Cheese(int x, int y)
	{
		//생성자에서는 미사일 이미지를 설정하고 예외처리로 종료하게끔 만듭니다.
	    try {
	    	 image =ImageIO.read(new File("cheese.png"));
	    }catch (IOException e) {
	     e.printStackTrace();
	    }
	    
	    //입력받은 매개변수 x,y의 값을 이 현재 객체 x,y에 할당합니다.
		this.x=x;
		this.y=y;
	}

	//움직이는것을 구현한 메소등비니다.
	public void move()
	{
		/*y의값을 미사일의스피드만큼 점점 감소시킵니다. 점점위로올라간다는 얘기이겠습니다.
		 *쭉쭉올라가서 이제 미사일이 화면끝까지 올라가면 더이상 그릴필요가없습니다.
		 *그러면 조건문에 따라서 더이상 y의값을 수정해주지않겠습니다.*/
		if(y>0)
			x-=MISSILE_SPEED;	
	}
	
}
