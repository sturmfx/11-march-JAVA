package com.company;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Circle
{
    boolean alive = true;
    Random r;
    double x;
    double y;
    double radius;
    Color color;

    double speed = 2;
    double dx = 0.0;
    double dy = 0.0;

    int target_x = 250;
    int target_y = 250;
    public Circle(double x1, double y1, double r, Color c)
    {
        this.x = x1;
        this.y = y1;
        this.radius = r;
        this.color = c;
        this.r = new Random();
        update(250, 250);
    }

    public void update(int tx, int ty)
    {
        double delta_x = tx - x;
        double delta_y = ty - y;
        double dist = Math.sqrt(delta_x * delta_x + delta_y * delta_y);
        double k = dist / speed;
        dx = delta_x / k;
        dy = delta_y / k;
        target_x = tx;
        target_y = ty;
    }

    public void xyupdate()
    {
        x = x + dx;
        y = y + dy;
        if(check_coordinate())
        {
            target_update(1500, 1000);
        }
    }

    public void target_update(int x_limit, int y_limit)
    {
        int new_x = r.nextInt(x_limit);
        int new_y = r.nextInt(y_limit);
        update(new_x, new_y);
    }

    public boolean check_coordinate()
    {
        double x_diff = Math.abs(this.x - this.target_x);
        double y_diff = Math.abs(this.y - this.target_y);

        if(x_diff <= Math.abs(dx) && y_diff <= Math.abs(dy))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static Circle eat(Circle c1, Circle c2)
    {
        Circle eaten_circle;
        double radius1 = c1.radius;
        double radius2 = c2.radius;
        double x1 = c1.x;
        double y1 = c1.y;
        double x2 = c2.x;
        double y2 = c2.y;

        double distance_between_c1_and_c2 = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        if(distance_between_c1_and_c2 < (radius1 + radius2))
        {
            double new_radius = Math.sqrt(radius1*radius1 + radius2*radius2);
            if(radius1 >= radius2)
            {
                c1.radius = new_radius;
                c2.alive = false;
                return c2;
            }
            else
            {
                c2.radius = new_radius;
                c1.alive = false;
                return c1;
            }
        }
        return c1;
    }
    public boolean intersects(Circle c)
    {
        double l = Math.sqrt((c.x-this.x)*(c.x-this.x) + (c.y-this.y)*(c.y-this.y));
        if(this.radius + c.radius < l)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public double getArea()
    {
        return Math.PI * this.radius * this.radius;
    }

    public static void updateCircles(java.util.List<Circle> circles_input)
    {
        CopyOnWriteArrayList<Circle> copy = new CopyOnWriteArrayList<Circle>(circles_input);
        CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<Circle>(circles_input);
        for (Circle c1 : circles)
        {
            for (Circle c2 : copy)
            {
                if (c1 == c2) continue;
                if (c1.intersects(c2) && (c1.radius < c2.radius) && c1.alive && c1.color == c2.color)
                {
                    double newRadius = Math.sqrt((c1.getArea() + c2.getArea()) / Math.PI);
                    c2.radius = newRadius;
                    c1.alive = false;
                    circles.remove(c1);
                    break;
                }
            }
        }
        circles_input = (java.util.List<Circle>) circles.clone();
    }
    public void print1()
    {
        System.out.println("Color: " + color + ", x = " + x + ", y = " + y);
    }


}
