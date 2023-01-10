package demo;
import javax.swing.*;
import java.awt.*;

public class MySnake{
//create a grid//
    public static void main(String[] args){
        JFrame frame = new JFrame();
        //x&y//
        frame.setBounds(600, 100, 700, 900);
       //拖拽不改变大小//
        frame.setResizable(false);
       //当点击窗口关闭按钮时，执行操作是退出//
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //添加画布//
        frame.add(new MyPanel());
       //show up//
        frame.setVisible(true);


    
    }
}