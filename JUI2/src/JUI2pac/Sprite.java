package JUI2pac;
import java.awt.Image;
import java.awt.image.BufferedImage;

//공통된 필드와메소드를 모아두는 클래스를 구현 합니다.
public class Sprite{
	/*x와 y좌표의 값 필드인 x,y를 선언하고
	 *x와 y좌표의 움직이는 속도를 구현하는 필드인 dx,dy를 선언하고
	 *이미지필드 image를 선언합니다. */
	public int dx;
	public int dy;
	public int x;
	public int y;
	public BufferedImage image;

	
	
	//접근자 getX와 getY그리고 getImage를 구현합니다.
	public int getX() {return x;}
	public int getY() {return y;}
	public Image getImage(){return image;}
	public int getWidth() {return image.getWidth();}
	public int getHeight() {return image.getHeight();}
	
}
