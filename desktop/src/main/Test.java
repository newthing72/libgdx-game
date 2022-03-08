package main;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test {
  public static void main(String[] args) {
    System.out.println("Working Directory = " + System.getProperty("user.dir"));
    JFrame frame = new JFrame("My First GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    JButton button = new JButton("Press");
    frame.getContentPane().add(button); // Adds Button to content pane of frame
    frame.setVisible(true);
  }
}
