import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class APM extends JFrame{
	
	int ww, wh;
	BufferedImage buffer, welcome, whatAreYou, custom;
	BufferedImage manHead, manBody, manHandLeft, manHandRight, manFootLeft, manFootRight;
	BufferedImage machineHead, machineBody, machineHandLeft, machineHandRight, machineFootLeft, machineFootRight;
	BufferedImage errorBodyPart;
	int menuID;
	int px, py;//UL of object
	int[][] animxy;
	int[] imID;
	boolean animPhaseIncrease;
	int subAnimCounter;
	int animPhase;
	boolean[] dirs;//left, right, up, down
	int moveSpeed;
	int[] xyval;
	Keys k;
	Mouse m;
	
	public APM(){
		ww = 600;
		wh = 600;
		buffer = new BufferedImage(ww, wh, BufferedImage.TYPE_INT_ARGB);
		try {
			welcome = ImageIO.read(new File("save/welcome.png"));
			whatAreYou = ImageIO.read(new File("save/whatAreYou.png"));
			custom = ImageIO.read(new File("save/custom.png"));
			manHead = ImageIO.read(new File("save/humanHead.png"));
			manBody = ImageIO.read(new File("save/humanBody.png"));
			manHandLeft = ImageIO.read(new File("save/humanHandLeft.png"));
			manHandRight = ImageIO.read(new File("save/humanHandRight.png"));
			manFootLeft = ImageIO.read(new File("save/humanFootLeft.png"));
			manFootRight = ImageIO.read(new File("save/humanFootRight.png"));
			machineHead = ImageIO.read(new File("save/machineHead.png"));
			machineBody = ImageIO.read(new File("save/machineBody.png"));
			machineHandLeft = ImageIO.read(new File("save/machineHandLeft.png"));
			machineHandRight = ImageIO.read(new File("save/machineHandRight.png"));
			machineFootLeft = ImageIO.read(new File("save/machineFootLeft.png"));
			machineFootRight = ImageIO.read(new File("save/machineFootRight.png"));
			errorBodyPart = ImageIO.read(new File("save/errorBodyPart.png"));
		} catch (IOException e) {
			System.out.println("ERROR: failed to load an image");
			e.printStackTrace();
		}
		menuID = 1;
		px = 0;
		py = 0;
		animxy = new int[6][2];
		imID = new int[6];
		for(int x = 0; x < 6; x++){
			animxy[x][0] = 0;
			animxy[x][1] = 0;
			imID[x] = 0;
		}
		animPhaseIncrease = false;
		subAnimCounter = 0;
		animPhase = 1;
		dirs = new boolean[4];
		for(int x = 0; x < 4; x++){
			dirs[x] = false;
		}
		moveSpeed = 1;
		xyval = new int[2];
		xyval[0] = 0;
		xyval[1] = 0;
		k = new Keys();
		m = new Mouse();
		addKeyListener(k);
		addMouseListener(m);
		setTitle("Character Test");
		setSize(ww, wh);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args){
		APM apm = new APM();
	}
	
	public void paint(Graphics g){
		Graphics g2 = buffer.createGraphics();
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, ww - 1, wh - 1);
		if(menuID == 1){//welcome
			g2.drawImage(welcome, 0, 0, null);
		}else if(menuID == 2){//what are you
			g2.drawImage(whatAreYou, 0, 0, null);
		}else if(menuID == 3){//custom
			g2.drawImage(custom, 0, 0, null);
		}else{
			animPhaseIncrease = false;
			if(dirs[1]){//right
				px += moveSpeed;
				animPhaseIncrease = true;
			}else if(dirs[0]){//left
				px -= moveSpeed;
				animPhaseIncrease = true;
			}
			if(dirs[3]){//down
				py += moveSpeed;
				animPhaseIncrease = true;
			}else if(dirs[2]){//up
				py -= moveSpeed;
				animPhaseIncrease = true;
			}
			if(animPhaseIncrease){
				subAnimCounter++;
				if(subAnimCounter == 20){
					animPhase++;
					subAnimCounter = 0;
				}
				if(animPhase > 5){
					animPhase = 1;
				}
			}
			for(int x = 0; x < 6; x++){
				animPhaseOffset(x + 1, animPhase);
				g2.drawImage(imageByID(imID[x]), px + xyval[0], py + xyval[1], null);
			}
		}
		g.drawImage(buffer, 0, 0, null);
		repaint();
	}
	
	void animPhaseOffset(int part, int phase){
		switch(part){
		case 1://head
			xyval[0] = animxy[0][0];
			xyval[1] = animxy[0][1];
			break;
		case 2://body
			xyval[0] = animxy[1][0];
			xyval[1] = animxy[1][1];
			break;
		case 3://hand left
			switch(phase){
			case 1:
				xyval[0] = animxy[2][0];
				xyval[1] = animxy[2][1];
				break;
			case 2:
				xyval[0] = animxy[2][0] - 5;
				xyval[1] = animxy[2][1];
				break;
			case 3:
				xyval[0] = animxy[2][0] - 10;
				xyval[1] = animxy[2][1];
				break;
			case 4:
				xyval[0] = animxy[2][0] - 5;
				xyval[1] = animxy[2][1];
				break;
			case 5:
				xyval[0] = animxy[2][0];
				xyval[1] = animxy[2][1];
				break;
			default: break;
			}
			break;
		case 4://hand right
			switch(phase){
			case 1:
				xyval[0] = animxy[3][0];
				xyval[1] = animxy[3][1];
				break;
			case 2:
				xyval[0] = animxy[3][0] + 5;
				xyval[1] = animxy[3][1];
				break;
			case 3:
				xyval[0] = animxy[3][0] + 10;
				xyval[1] = animxy[3][1];
				break;
			case 4:
				xyval[0] = animxy[3][0] + 5;
				xyval[1] = animxy[3][1];
				break;
			case 5:
				xyval[0] = animxy[3][0];
				xyval[1] = animxy[3][1];
				break;
			default: break;
			}
			break;
		case 5://foot left
			switch(phase){
			case 1:
				xyval[0] = animxy[4][0];
				xyval[1] = animxy[4][1];
				break;
			case 2:
				xyval[0] = animxy[4][0];
				xyval[1] = animxy[4][1] + 7;
				break;
			case 3:
				xyval[0] = animxy[4][0];
				xyval[1] = animxy[4][1] + 14;
				break;
			case 4:
				xyval[0] = animxy[4][0];
				xyval[1] = animxy[4][1] + 7;
				break;
			case 5:
				xyval[0] = animxy[4][0];
				xyval[1] = animxy[4][1];
				break;
			default: break;
			}
			break;
		case 6://foot right
			switch(phase){
			case 1:
				xyval[0] = animxy[5][0];
				xyval[1] = animxy[5][1];
				break;
			case 2:
				xyval[0] = animxy[5][0];
				xyval[1] = animxy[5][1] + 7;
				break;
			case 3:
				xyval[0] = animxy[5][0];
				xyval[1] = animxy[5][1] + 14;
				break;
			case 4:
				xyval[0] = animxy[5][0];
				xyval[1] = animxy[5][1] + 7;
				break;
			case 5:
				xyval[0] = animxy[5][0];
				xyval[1] = animxy[5][1];
				break;
			default: break;
			}
			break;
		default:break;
		}
	}	
	BufferedImage imageByID(int i){
		switch(i){
		case 1: return manHead;
		case 2: return manBody;
		case 3: return manHandLeft;
		case 4: return manHandRight;
		case 5: return manFootLeft;
		case 6: return manFootRight;
		case 7: return machineHead;
		case 8: return machineBody;
		case 9: return machineHandLeft;
		case 10: return machineHandRight;
		case 11: return machineFootLeft;
		case 12: return machineFootRight;
		default: return errorBodyPart;
		}
	}
	
	class Mouse extends MouseAdapter{
		public Mouse(){
			
		}
		public void mousePressed(MouseEvent e){
			if(menuID == 1){//welcome
				if(e.getX() > 273 && e.getX() < 319 && e.getY() > 338 && e.getY() < 372){
					menuID++;
				}
			}else if(menuID == 2){//what are you
				if(e.getX() > 114 && e.getX() < 222 && e.getY() > 183 && e.getY() < 375){
					menuID++;
					//set man
					imID[0] = 1;
					imID[1] = 2;
					imID[2] = 3;
					imID[3] = 4;
					imID[4] = 5;
					imID[5] = 6;
				}else if(e.getX() > 280 && e.getX() < 505 && e.getY() > 183 && e.getY() < 376){
					menuID++;
					//set machine
					imID[0] = 7;
					imID[1] = 8;
					imID[2] = 9;
					imID[3] = 10;
					imID[4] = 11;
					imID[5] = 12;
				}
			}else if(menuID == 3){//custom
				if(e.getX() > 273 && e.getX() < 319 && e.getY() > 338 && e.getY() < 372){
					menuID++;
					//setting initial positions of entity object and entity body parts
					px = ww / 2;
					py = wh / 2;
					animxy[0][0] = 40;//head
					animxy[0][1] = 6;
					animxy[1][0] = 40;//body
					animxy[1][1] = 30;
					animxy[2][0] = 13;//hand left
					animxy[2][1] = 40;
					animxy[3][0] = 67;//hand right
					animxy[3][1] = 40;
					animxy[4][0] = 23;//foot left
					animxy[4][1] = 86;
					animxy[5][0] = 57;//foot right
					animxy[5][1] = 86;
				}
			}
		}
	}
	class Keys extends KeyAdapter{
		public Keys(){
			
		}
		public void keyPressed(KeyEvent e){
			if(menuID != 1 && menuID != 2 && menuID != 3){
				if(e.getKeyCode() == KeyEvent.VK_W){//up
					dirs[2] = true;//up
					dirs[3] = false;//down
				}else if(e.getKeyCode() == KeyEvent.VK_S){//down
					dirs[2] = false;//up
					dirs[3] = true;//down
				}
				if(e.getKeyCode() == KeyEvent.VK_A){//left
					dirs[0] = true;//left
					dirs[1] = false;//right
				}else if(e.getKeyCode() == KeyEvent.VK_D){//right
					dirs[0] = false;//left
					dirs[1] = true;//right
				}
			}
		}
		public void keyReleased(KeyEvent e){
			if(menuID != 1 && menuID != 2 && menuID != 3){
				if(menuID != 1 && menuID != 2 && menuID != 3){
					if(e.getKeyCode() == KeyEvent.VK_W){//up
						dirs[2] = false;//up
					}
					if(e.getKeyCode() == KeyEvent.VK_S){//down
						dirs[3] = false;//down
					}
					if(e.getKeyCode() == KeyEvent.VK_A){//left
						dirs[0] = false;//left
					}
					if(e.getKeyCode() == KeyEvent.VK_D){//right
						dirs[1] = false;//right
					}
				}
			}
		}
	}
}
