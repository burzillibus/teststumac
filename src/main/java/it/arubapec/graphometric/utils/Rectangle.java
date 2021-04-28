/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.utils;

import java.io.Serializable;

/**
 * 
 * @author Francesco Sighieri <francesco.sighieri@staff.aruba.it>
 */
public class Rectangle  implements Serializable {

    private static final long serialVersionUID = 2091951645264821156L;

    public int X;
    public int Y;
    public int Width;
    public int Height;
    public Location Location;
    public Size Size;

    public Rectangle(int x, int y, int width, int height) {
        X = x;
        Y = y;
        Width = width;
        Height = height;
        Location = new Location(x, y);
        Size = new Size(width, height);
    }

    @Override
    public String toString() {
        return "Rectangle{" + "X=" + X + ", Y=" + Y + ", Width=" + Width + ", Height=" + Height + ", Location=" + Location + ", Size=" + Size + '}';
    }
    
    static class Location implements Serializable{

        private static final long serialVersionUID = 3174404946162228447L;

        public int X;
        public int Y;

        public Location(int x, int y) {
            X = x;
            Y = y;
        }

        @Override
        public String toString() {
            return "Location{" + "X=" + X + ", Y=" + Y + '}';
        }
    }

    static class Size implements Serializable{

        private static final long serialVersionUID = 5712914347446936760L;

        public int Width;
        public int Height;

        public Size(int width, int height) {
            Width = width;
            Height = height;
        }

        @Override
        public String toString() {
            return "Size{" + "Width=" + Width + ", Height=" + Height + '}';
        }
    }

}
