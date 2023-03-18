package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class GameGUI extends JPanel implements ActionListener
{
    Random r;
    Timer t;
    Color background = Color.BLACK;
    int delay = 15;
    int width = 1500;
    int height = 1000;

    double radius = 100;
    Color c_color = Color.RED;

    ArrayList<Circle> circles = new ArrayList<Circle>();

    ArrayList<Color> colors = new ArrayList<Color>();

    int max_radius = 40;
    int min_radius = 20;

    int tick_counter = 0;
    public GameGUI()
    {
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.ORANGE);

        r = new Random();
        t = new Timer(delay, this);
        addMouseListener(new GameMouseAdaptor());
        addKeyListener(new GameKeyboardAdaptor());
        setBackground(background);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        t.start();
    }

    public static void main(String args[])
    {
        GameGUI game = new GameGUI();
        GameWindow gw = new GameWindow("GAME");
        gw.add(game);
        gw.pack();
        gw.setVisible(true);
    }


    public void tick()
    {
        for(Circle c1: circles)
        {
            c1.xyupdate();
        }
        /**for(Circle circle1: circles)
        {
            java.util.List<Circle> copy = circles.stream().collect(Collectors.toList());
            for(Circle circle2: copy)
            {
                if(circle1.equals(circle2))
                {
                }
                else
                {
                    Circle result = Circle.eat(circle1,circle2);
                    if(result.alive)
                    {
                    }
                    else
                    {
                        circles.remove(result);
                    }
                }
            }
        }**/
    }



    @Override
    public void actionPerformed(ActionEvent e)
    {
        tick();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for(Circle c: circles)
        {
            g.setColor(c.color);
            g.fillOval((int)c.x - (int)c.radius, (int)c.y - (int)c.radius, (int)c.radius * 2, (int)c.radius * 2);
        }
    }

    private class GameMouseAdaptor extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent m)
        {
            int button = m.getButton();

            if(button == 1)
            {
                double x = m.getX();
                double y = m.getY();
                Color color1 = colors.get(r.nextInt(colors.size()));
                double radius1 = min_radius + r.nextInt(max_radius - min_radius);
                Circle c = new Circle(x, y, radius1, color1);
                circles.add(c);
            }

            if(button == 3)
            {
                double x = m.getX();
                double y = m.getY();
                for(Circle c : circles)
                {
                    double distance = Math.sqrt((x-c.x)*(x-c.x)+(y-c.y)*(y-c.y));
                    double radius = c.radius;
                    if(distance <= radius)
                    {
                        circles.remove(c);
                    }
                }
            }
        }
    }


    private class GameKeyboardAdaptor extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent k)
        {
            char key_pressed = k.getKeyChar();

            if(key_pressed == 'w')
            {
                int x = r.nextInt(width);
                int y = r.nextInt(height);
                Color color1 = colors.get(r.nextInt(colors.size()));
                double radius1 = min_radius + r.nextInt(max_radius - min_radius);
                Circle c = new Circle(x, y, radius1, color1);
                circles.add(c);
            }
            if(key_pressed == 's')
            {
                int index = circles.size() - 1;
                if(index >= 0)
                {
                    circles.remove(index);
                }

            }
            if(key_pressed == 'c')
            {
                circles.clear();
            }

        }
    }
}