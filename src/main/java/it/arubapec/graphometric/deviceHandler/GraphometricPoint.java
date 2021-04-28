/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.deviceHandler;

import java.io.Serializable;

/**
 * 
 * @author Francesco Sighieri <francesco.sighieri@staff.aruba.it>
 */
public class GraphometricPoint implements Serializable{

    private static final long serialVersionUID = -6938686978825683641L;

    public int X, Y, P, T;

    /**
     *
     * @param x
     * @param y
     * @param p
     * @param t
     */
    public GraphometricPoint(int x, int y, int p, int t) {
        X = x;
        Y = y;
        P = p;
        T = t;
    }

    @Override
    public String toString() {
        return "GraphometricPoint{" + "X=" + X + ", Y=" + Y + ", P=" + P + ", T=" + T + '}';
    }
}
