package com.company;
import javax.swing.*;

public class GameWindow extends JFrame
{
    public GameWindow(String title)
    {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
    }
}