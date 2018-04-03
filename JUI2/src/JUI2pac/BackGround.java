package JUI2pac;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

//ActionListener와 KeyListener을 구현하고 JPanel을 상속받는 배경클래스를 구현합니다.
public class BackGround extends JPanel implements ActionListener, KeyListener
{
	/*배경이미지를위한 이미지필드인 image를 선언하고 
	 *딜레이를위한 타이머 필드, 그리고 우주선필드, 그리고 딜레이의값을 넣은 DELAY필드를 선언해줍니다.*/
	private BufferedImage backimage,startimage,endimage;
	private Timer timer;
	private Hero hero;
	private final int DELAY = 20;
	private int x,y,dx,dy;
	//배경전환에쓰이는 필드입니다.
	private int startsign =0;
	//점프에쓰이는 필드입니다.
	private int jumpsign =0;
	//점프에쓰이는 y값저장소입니다.
	private int temp=0;
	//치즈를모아생긴 포인트입니다.
	private int point=0;
	//화면에[ 글자를 표시하고자 선언한 폰트필드입니다.
	private Font pointInfo, finalInfo, timeInfo;   // 현재 x값과 y값을 모니터링 하기 위한 폰트출력
	//Use Thread
	Thread thread1 = new Thread(new thread1());
	//Time의정보를가지고있는 str필드입니다.
	String str;
	//동전이 속도를 제어하는 필드입니다.
	private int coin_controller=0;
	//랜덤을사용하기위해 랜덤객채를 생성합니다.
	Random random = new Random();
	//인덱스를선언합시다.
	private final int INDEX=0;
	/*Application ArrayList Part
	 * Missile타입의 ArrayList를 선언해줍니다.*/
	ArrayList <Cheese> mlist = new ArrayList<Cheese>();
	//배경이미지의 가로와 높이의길이를 넣어줄 변수를 생성. 
	private int WIDTH ,HEIGHT;
	
	
	//클래스의 생성자를 구현합니다.
	public BackGround()
	{
		//키리스너를이곳에 포함시킵니다.
		addKeyListener(this);
		setFocusable(true);

		//생성자에서는 배경이미지, 게임이미지, 엔이미지를 설정하고 예외처리로 종료하게끔 만듭니다.
	      try {
	    	  	 backimage =ImageIO.read(new File("back.jpeg"));
	    	  	 startimage= ImageIO.read(new File("back_start.jpeg"));
	    	  	 endimage=ImageIO.read(new File("back_over.jpeg"));
	      }catch (IOException e) {
	         e.printStackTrace();
	      }

	    //WIDTh와 HEIGHT는 읽은 배경화면이미지의 가로 높이의 값을 받습니다.
	    WIDTH = backimage.getWidth(null);
	    HEIGHT = backimage.getHeight(null);
		
	    //폰트필드에 폰트객체를 설정합니다.
	    pointInfo = new Font("San Serif", Font.PLAIN, 20);
	    finalInfo = new Font("San Serif", Font.PLAIN, 20);
	    timeInfo = new Font("San Serif", Font.PLAIN, 20);
	    
		/*선언해준 필드 ship에 우주선의 객체를 넣어줍니다.
		 *마찬가지로 Timer도 DELAY의 값을 넣은 객체를 넣어주고
		 *Timer을 시작합니다.*/
		hero = new Hero();
		timer = new Timer(DELAY,this);
		timer.start();
		
		//스레드도 실행합니
		thread1.start();
	}
	
	
	/*이미지 크기에 패널을 맞춘다
	 *getPreferredSize()는 컴포넌트가 원하는 크기를 배치 관리자에게 알리는 메소드이다
	 현재 읽은 이미지의 크기로 다시 설정한다 */
	public Dimension getPreferredSize() {
		if(backimage==null) {
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
		//치즈객체를만들고 랜덤y값을 지정하여 발사합니다.
		Cheese m;
		x=WIDTH-100;
		y=HEIGHT-100-random.nextInt(100);
		m=new Cheese(x,y);
		mlist.add(m);
		}
	
	// 충돌검사입니다.
	   public boolean checkCollision(Cheese other)
	   {
		   //사각형에 각 객체 (주인공 치즈)를 할당하여 그 영역만큼 비교하여 충돌을 검사합니다.
	      Rectangle heroRec = new Rectangle();
	      Rectangle cheRec = new Rectangle();
	      heroRec.setBounds(hero.x, hero.y, hero.getWidth(), hero.getHeight());
	      cheRec.setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
	      //충돌검사를하여 충돌유무를 True or False반환합니다.
	      return heroRec.intersects(cheRec);
	   }
	   
	/*
	 * 스레드클래스를만들어줍니다.
	 * 이 스레드 내부run동작은 실시간 현재시간파악입니다.
	 * 스레드로 무얼할까 고민하다,
	 * 동시에 실행하는 스레드의 특징을 제일 잘 살리는 영역이라고 생각하여
	 * 화면에 실시간 시간을 표시하는 스레드를 만들어보았습니다.*/
	public class thread1 implements Runnable
	{
	@Override
	public void run() {
	    // TODO 자동 생성된 메소드 스텁
	    while(true)
	    {
	    		try {
	    			long time = System.currentTimeMillis(); 
	    			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	    			str = dayTime.format(new Date(time));
	             Thread.sleep(100);
	         } catch (Exception e) {
	         e.printStackTrace();
	         }     
	    	}
		}
	         
	}
	
	

	

	
	/*Application ArrayList Part
	 * 미사일의 정보를 반환하는 getMissile()메소드입니다.
	 * 평소에는 객체하나 'm'밖에없어서 그것만 반환하였었지만
	 * 이제는 ArrayList이니 리스트안에있는 최신 요소의 값을반환하는걸로 메소드를 수정해줄 것 입니다.
	 * If문에대한설명
	 * 		If문은 mlist에 m이라는객체가 있는지 없는지를 size()메소드를 통해 식별합니다.
	 * 		mlist에 객체m이 없는상태인데 객체를 반환할수는없기때문입니다.*/
	public Cheese getMissile() {
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
	public void rmMisobj(int index) {
		if(mlist.size()>0){
			mlist.remove(index);
		}
	}
	

	
	
	//그림을 그리는 paintComponent메소드입니다.	
	public void paintComponent(Graphics g) {
		//부모클래스의 메소드를 호출하기위해 super을 사용합니다.
		super.paintComponent(g);
		//첫시작은 startsign이 0이므로 시작화면을 출력합니다.
		if(startsign==0)
			g.drawImage(startimage,0,0,this);
		//사용자가 게임을 시작하면 게임화면을 띄웁니다.
		else if(startsign==1)
		{      
			//0,0위치에 image를 그립니다 여기서 image란 배경화면을 뜻합니다.
			g.drawImage(backimage, 0, 0, this);
			Graphics2D g2d = (Graphics2D) g;
			//우주선의 이미지를 우주선의현재위치(x,y)값에 그려줍니다.
			g2d.drawImage(hero.getImage(), hero.getX(),hero.getY(),this);
			
			//화면에 포인트와 문자열, 그리고 스레드를이용한 실시간 시각정보를 표시합니다.
			g.setFont(pointInfo);
	        g.drawString("포만감 "+point,800,50);
	        g.setFont(finalInfo);
	        g.drawString("100이되면 생쥐는 만족합니다! ",700, 80);
	        g.setFont(timeInfo);
	        g.drawString("현재"+str,700, 110);
	        
	        
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
					Cheese m = mlist.get(i);
					if(m.x>0)
					{g2d.drawImage(m.getImage(),m.getX(),m.getY(),this);}
				}
			}
		}
		//게임이끝나면 게임오버화면을 출력합니다.
		else if(startsign==2)
		{
			g.drawImage(endimage,0,0,this);
		}
		Toolkit.getDefaultToolkit().sync();
		}
	
	
	
	//이는 액션이벤트를 처리하는 메소드입니다.
	public void actionPerformed(ActionEvent e) {  
		//게임화면으로 전환하면 이 문장 이하 문장들이 실행됩니다.
		
		if(startsign==1)
		{
			//충돌검사관련 반복문입니다.
			for(int i=0; i<mlist.size();i++)
			{
				if(checkCollision(mlist.get(i))){
					point+=10;
					rmMisobj(i);
					//포인트가 100이되면 엔딩화면을 출력하고 포인트를0으로 초기화시킵니다.
					if(point==100)
					{
						startsign=2;
						point=0;
					}
				}
			}
			/*기존의 fire()메소드를 그냥 실행하면 미사일이 너무 많이 발사됨
			 * 따라서 coin_controller를 사용하여 발사해줄 필요가 있다 */
			coin_controller++;
			if(coin_controller%30==0)
				fire();
			
			//우주선을움직이는 메소드를 호출합니다.
			hero.move();
			
			//점프의 구현입니다.
			if(jumpsign==1 && temp<10)
			{
				hero.y-=10;
				temp++;
				if(temp==9)
				{
					jumpsign=2;
					temp=0;
				}
			}
			if(jumpsign==2 && hero.y<300)
			{
				hero.y+=10;
			}
			
			
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
				Cheese m = mlist.get(i);
				if(m.x>0)
				{m.move();}
				else{rmMisobj(0);}
			}
		}
		//계속하여 다시 그려줍니다.
		repaint();

		}
	}
	



	/*키를받는 메소드들입니다.
	 *키를 입력받았을 때에 우주선에있는 메소드를 호출하여 우주선의 이동값을 변경시켜
	 *결과적으로 키를눌렀을 시 그 방향으로 움직이게하고, 떼었을 시 우주선을 멈추게하는
	 *그런동작을 하게끔 해주는 메소드들입니다.*/
	public void keyPressed(KeyEvent e) {
		hero.keyPressed(e);
		int keycode=e.getKeyCode();
		switch(keycode)
		{
		case KeyEvent.VK_LEFT: dx=-1; break;		
		case KeyEvent.VK_RIGHT: dx=1; break;				
		case KeyEvent.VK_SPACE: jumpsign=1; break;
		case KeyEvent.VK_A: startsign=1; break;
		}}
	public void keyReleased(KeyEvent e) {hero.keyReleased(e);}
	public void keyTyped(KeyEvent arg0){}
}