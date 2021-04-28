/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.utils;

import java.io.Serializable;

/**
 * 
 * @author Francesco Sighieri <francesco.sighieri@staff.aruba.it>
 */
public class Point implements Serializable{

    private static final long serialVersionUID = 7703357494610406449L;
    
    public int X;
    public int Y;

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "X=" + X + ", Y=" + Y + '}';
    }
}
