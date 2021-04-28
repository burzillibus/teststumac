/*
 * EventListener
 *
 * Created on 22-giu-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric.deviceHandler.event;

/**
 *
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public interface TabletEventListener extends java.util.EventListener {

    /**
     * Event occurred when tablet is touched in the tool bar
     * @param evt 
     */
    public void tabletEventOccurred(TabletEvent evt);

    /**
     * Event occurred when tablet is touched
     * @param evt 
     */
    public void touchEventOccurred(TabletEvent.TouchEvent evt);
}
