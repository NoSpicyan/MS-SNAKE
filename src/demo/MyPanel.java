package demo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;

public class MyPanel extends JPanel implements KeyListener, ActionListener{
    
    //声明右侧蛇头和身体图片//
    ImageIcon right = new ImageIcon("src/images/right.jpg");
    ImageIcon body = new ImageIcon("src/images/body.jpg");

    //声明上下左右蛇头图片
    ImageIcon top=new ImageIcon ("src/images/top.jpg");
    ImageIcon bottom=new ImageIcon("src/images/bottom.jpg");
    ImageIcon left=new ImageIcon("src/images/left.jpg");


    //生命一个初始值，表示蛇的长度//
    int len=3;
    //声明两个数组分别存放x&y坐标位置
    int[] snakeX = new int[1008]; //最大值：宽*高
    int[] snakeY = new int[1008]; //最大值：宽*高

    //声明枚举类型变量，表示蛇头方向
    Direction direction = demo.Direction.right;

    //声明一个变量，标记游戏是否开始，当值为true表示开始游戏
    boolean isStart = false;
    boolean isOver = false;

    //创建定时器对象
    Timer timer=new Timer(100, this);

    //声明两个变量食物坐标位置
    int foodX;
    int foodY;
    int Score;
    //声明随机变量random
    Random random=new Random();
    //声明食物图片
    ImageIcon food=new ImageIcon("src/images/food.jpg");

 
    public MyPanel() {
        //设定蛇的头部和身体初始位置
        snakeX[0]=100;
        snakeY[0]=100;

        snakeX[1]=75;
        snakeY[1]=100;
        snakeX[2]=50;
        snakeY[2]=100;

        //获取键盘事件
        this.setFocusable(true);
        //添加监听
        this.addKeyListener(this);

        //启动定时器
        timer.start();

        //食物坐标
        foodX=25+25*random.nextInt(10);
        foodY=25+25*random.nextInt(20);
        Score=0; 
    }



    //重写画组建//左键/source action/override, implement Methods/...
    @Override
   protected void paintComponent(Graphics g) {
        //调用父类的方法做一些基本工作//
        super.paintComponent(g);
        //设置背景颜色//
        this.setBackground(Color.gray);
        //在画布上添加游戏区域//
        g.fillRect(0, 0, 700, 900);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Lobster", Font.BOLD, 20));
        g.drawString("Score:"+Score, 330, 30);


        //添加右侧蛇头//
        //right.paintIcon(this, g, snakeX[0], snakeY[0]);
        //根据枚举变量的方向值，进行判断，显示哪个方向的蛇头
        switch (direction) {
            case top:
                top.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case bottom:
                bottom.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case left:
                left.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case right:
                right.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;



        }

        //add body
        /*body.paintIcon(this, g, 75, 100);
        body.paintIcon(this, g, 50, 100);*/
        for(int i=1;i<len;i++){
            body.paintIcon(this, g, snakeX[i], snakeY[i]);
        //重复
        }

        //判断当前游戏是否开始的值isStart
        if(!isStart){ 
            g.setColor(Color.WHITE);
            g.setFont(new Font("October Crow", Font.BOLD, 40));
            g.drawString("Press 'Space' and start the game", 100, 500); 
        }
        if(!isOver){
            g.setColor(Color.RED);
            g.setFont(new Font("October Crow", Font.CENTER_BASELINE, 25));
            g. drawString("hahahaha u loser!", 100, 800);
        }
        
        
        //添加食物
        food.paintIcon(this, g, foodX, foodY);



    }




    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode=e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {

            //判断，当按空格键时数字值应该为32
            //标记游戏状态值取反
            isStart = !isStart;
            //重新画组件
            repaint();
        }else if(keyCode==KeyEvent.VK_UP);{
            direction=demo.Direction.top;
        } if(keyCode==KeyEvent.VK_DOWN){
            direction=demo.Direction.bottom;
        }else if(keyCode==KeyEvent.VK_LEFT){
            direction=demo.Direction.left;
        }else if(keyCode==KeyEvent.VK_RIGHT){
            direction=demo.Direction.right;
        }
    

    }






    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //当isStart值为true则表示游戏开始
        if(isStart){ 
            //移动身体
            for(int i=len-1;i>0;i--){
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }



        

            //通过方向值direction进行判断，移动蛇头
            switch (direction) {
                case top:
                    snakeY[0]-=25;
                    if(snakeY[0]<=0){
                        snakeY[0]=900;
                    }
                    break;
                case bottom:
                    snakeY[0]+=25;
                    if(snakeY[0]>=900){
                        snakeY[0]=0;
                    }
                    break;
                case left:
                    //水平向左移动
                    snakeX[0]-=25;
                    if(snakeX[0]<=0){
                        snakeX[0]=700;
                    }
                    break;
                case right:
                    //假如蛇头水平向右移动，则蛇头的值+25
                    snakeX[0]+=25;
                    //判断当前蛇头的值，若超出700，则x值再从0开始
                    if(snakeX[0]>=700){
                    snakeX[0]=0;
                    } 
                    break;
            
                default:
                    break;
            }

            //判断，当蛇头X和食物坐标x一致，并且蛇头y和食物坐标y一致，则表示吃掉食物
            if(snakeX[0]==foodX && snakeY[0]==foodY){
                //蛇的长度+1
                len ++;
                Score +=10;
                //重新生成食物坐标位置
                foodX=25+25*random.nextInt(20);
                foodY=25+25*random.nextInt(20);
            }
    

            

          
            //重新画组件方法
            repaint();
            //重新启动定时器
            timer.start();
        }
    }}
    

       



