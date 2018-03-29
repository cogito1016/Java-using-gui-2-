package JUI2pac;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//ActionListener와 KeyListener을 구현하고 JPanel을 상속받는 배경클래스를 구현합니다.
public class BackGround extends JPanel implements ActionListener, KeyListener
{
	/*배경이미지를위한 이미지필드인 image를 선언하고 
	 *딜레이를위한 타이머 필드, 그리고 우주선필드, 그리고 딜레이의값을 넣은 DELAY필드를 선언해줍니다.*/
	private BufferedImage image;
	private Timer timer;
	private Hero ship;
	private final int DELAY = 10;
	private int x,y,dx,dy;
	
	Random random = new Random();
	
	//인덱스를선언합시다.
		private final int INDEX=0;
		
		
	/*Application ArrayList Part
	 * Missile타입의 ArrayList를 선언해줍니다.*/
	ArrayList <Coin> mlist = new ArrayList<Coin>();

	
	
	//배경이미지의 가로와 높이의길이를 넣어줄 변수를 생성. 
	private int WIDTH ,HEIGHT;
	
	//클래스의 생성자를 구현합니다.
	public BackGround()
	{
		//키리스너를이곳에 포함시킵니다.
		addKeyListener(this);
		setFocusable(true);

		//생성자에서는 우주배경 이미지를 설정하고 예외처리로 종료하게끔 만듭니다.
	      try {
	    	  	 image =ImageIO.read(new File("Space.jpg"));
	      }catch (IOException e) {
	         e.printStackTrace();
	      }

	    //WIDTh와 HEIGHT는 읽은 배경화면이미지의 가로 높이의 값을 받습니다.
	    WIDTH = image.getWidth(null);
	    HEIGHT = image.getHeight(null);
		
		/*선언해준 필드 ship에 우주선의 객체를 넣어줍니다.
		 *마찬가지로 Timer도 DELAY의 값을 넣은 객체를 넣어주고
		 *Timer을 시작합니다.*/
		ship = new Hero();
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	
	/*이미지 크기에 패널을 맞춘다
	 *getPreferredSize()는 컴포넌트가 원하는 크기를 배치 관리자에게 알리는 메소드이다
	 현재 읽은 이미지의 크기로 다시 설정한다 */
	public Dimension getPreferredSize() {
		if(image==null) {
			return new Dimension(100,100);
			}else {
				return new Dimension(WIDTH,HEIGHT);
				}
	}
	
	/*Application ArrayList Part
	 * 미사일 객체를 발사하는 메소드 fire()을 호출시 
	 * 미사일 객체 필드 m을 하나만들고 
	 * 현재 우주선의 x,y값을 받는 Missile객체를 m에 넣어줍니다..
	 * 그리고 그 객체 m 을 미사일타입의 어레이리스트인 mlist에 add시켜줍니다.
	 * 그러면 성공적으로 arraylist mlist에는 발사된 미사일객체m이 새롭게 추가됩니다.*/
	public void fire(){
		Coin m;
		x=WIDTH-100;
		y=HEIGHT-random.nextInt(300);
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
	

	
	
	//그림을 그리는 paintComponent메소드입니다.	
	public void paintComponent(Graphics g) {
		//부모클래스의 메소드를 호출하기위해 super을 사용합니다.
		super.paintComponent(g);
		//0,0위치에 image를 그립니다 여기서 image란 배경화면을 뜻합니다.
		g.drawImage(image, 0, 0, this);
		Graphics2D g2d = (Graphics2D) g;
		//우주선의 이미지를 우주선의현재위치(x,y)값에 그려줍니다.
		g2d.drawImage(ship.getImage(), ship.getX(),ship.getY(),this);
		
		/*Application ArrayList Part
		 *만약에 미사일의값이 null이아니면 => 이 뜻은 우주선에서 미사일을 Fire()시켰다는 의미이며
		 *따라서 getmisile()의 값은 null이 아닌, 우주선의 현재위치(x,y)값이있는 객체를 의미합니다
		 *또, ship.mlist.size()>0은 => 리스트가 0보다 크다는 의미이고, missile을 담을
		 *ArrayList에 미사일객체가 하나라도 있다는것을 의미합니다.
		 *조건 두개를 AND연산시켜 혹시모를 충돌을 최대한 피해갔습니다.*/
		if(getMissile() != null && mlist.size()>0)
		{
			/*Application ArrayList Part
			 *오류나는 for-each삭제하고 일반 for문으로 표현해보았습니다.
			 *Missile타입의 m필드를 선언하고 이곳에는 arraylist mlist에 들어있는 객체를 넣고,
			 *만약 그 미사일객체의 y값이 0보다 크다면 화면에 그려줍니다.
			 *y가 0보다 크다는것은 화면에 있다는 표시입나다. 더 올라갈 이유가 있는것이지요.*/
			for(int i=0; i<mlist.size();i++)
			{
				Coin m = mlist.get(i);
				if(m.x>0)
				{g2d.drawImage(m.getImage(),m.getX(),m.getY(),this);}
			}
		}
		Toolkit.getDefaultToolkit().sync();
		}
	
	//이는 액션이벤트를 처리하는 메소드입니다.
	public void actionPerformed(ActionEvent e) {  
		//우주선을움직이는 메소드를 호출합니다.
		ship.move();
		
		/*Application ArrayList Part
		 *만약에 미사일의값이 null이아니면 => 이 뜻은 우주선에서 미사일을 Fire()시켰다는 의미이며
		 *따라서 getmisile()의 값은 null이 아닌, 우주선의 현재위치(x,y)값이있는 객체를 의미합니다
		 *또, ship.mlist.size()>0은 => 리스트가 0보다 크다는 의미이고, missile을 담을
		 *ArrayList에 미사일객체가 하나라도 있다는것을 의미합니다.
		 *조건 두개를 AND연산시켜 혹시모를 충돌을 최대한 피해갔습니다.*/
		if(getMissile() != null && mlist.size()>0)
		{

			/*Application ArrayList Part
			 *오류나는 for-each삭제하고 일반 for문으로 표현해보았습니다.
			 *Missile타입의 m필드를 선언하고 이곳에는 arraylist mlist에 들어있는 객체를 넣고,
			 *만약 그 미사일객체의 y값이 0보다 크다면 그 미사일 객체 m을 움직이게해주는 move메소드를
			 *호출합니다. 만약에 그 객체의 y값이 0보다 크지않다면?, 즉 y<=0 이라면 
			 *이객체는 더이상 화면에 표시할 이유가없고 사라져야합니다.
			 *사라지는것은 mlist에서 이 객체를 삭제하는것으로 구현하였습니다.
			 *Arraylist mlist에서 이 객체를 삭제하는것으로 메모리도 절약하고 효과적으로
			 *객체도 지우는 프로그램이되었습니다.*/
			for(int i=0; i<mlist.size();i++)
			{
				Coin m = mlist.get(i);
				if(m.x>0)
				{m.move();}
				else
				{rmMisobj();}
			}
		}
		//계속하여 다시 그려줍니다.
		repaint();
	}
	
	/*키를받는 메소드들입니다.
	 *키를 입력받았을 때에 우주선에있는 메소드를 호출하여 우주선의 이동값을 변경시켜
	 *결과적으로 키를눌렀을 시 그 방향으로 움직이게하고, 떼었을 시 우주선을 멈추게하는
	 *그런동작을 하게끔 해주는 메소드들입니다.*/
	public void keyPressed(KeyEvent e) {
		ship.keyPressed(e);
		int keycode=e.getKeyCode();
		switch(keycode)
		{
		//case KeyEvent.VK_LEFT: dx=-1; break;		
		//case KeyEvent.VK_RIGHT: dx=1; break;			
		//case KeyEvent.VK_UP: dy=-1; break;			
		//case KeyEvent.VK_DOWN: dy=1; break;		
		case KeyEvent.VK_SPACE: fire(); break;
		}}
	public void keyReleased(KeyEvent e) {ship.keyReleased(e);}
	public void keyTyped(KeyEvent arg0){}
}