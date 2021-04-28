/*
 * TabletEvent
 *
 * Created on 22-giu-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric.deviceHandler.event;

import it.arubapec.graphometric.deviceHandler.GraphometricPoint;

import javax.swing.event.EventListenerList;
import java.util.EventObject;

/**
 *
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class TabletEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * Enumeration with all tablet event type
     * <ul>Tablet event types:
     * <li><b>Reset</b>: in signing mode this event will clear the sign on the
     * signbox</li>
     * <li><b>Ok</b>: in privacy mode, this event will accept the privacy
     * conditions</li>
     * <li><b>Cancel</b>: this event abort the procedure</li>
     * <li><b>Accept</b>: in signing mode it will accept the sign</li>
     * </ul>
     */
    public enum TabletEventType {
        /**
         * in signing mode this event will clear the sign on the signbox
         */
        Reset,
        /**
         * in privacy mode, this event will accept the privacy conditions
         */
        Ok,
        /**
         * this event abort the procedure
         */
        Cancel,
        /**
         * in signing mode it will accept the sign
         */
        Accept
    }

    public static EventListenerList listenerList = new EventListenerList();
    public static boolean eventSet = false;
    public TabletEventType EventType;

    /**
     * Default constructor
     *
     * @param source
     * @param evt
     */
    public TabletEvent(Object source, TabletEventType evt) {
        super(source);
        EventType = evt;
    }

    /**
     *
     * @param evt
     */
    public static void fireTabletEvent(TabletEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == TabletEventListener.class) {
                ((TabletEventListener) listeners[i + 1]).tabletEventOccurred(evt);
            }
        }
    }

    /**
     *
     * @param evt
     */
    public static void fireTouchEvent(TouchEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == TabletEventListener.class) {
                ((TabletEventListener) listeners[i + 1]).touchEventOccurred(evt);
            }
        }
    }

    /**
     * Tocco della penna sul pad
     */
    public static class TouchEvent extends EventObject {

        private static final long serialVersionUID = 1L;

        public GraphometricPoint EventPoint;

        /**
         *
         * @param source
         * @param eventPoint
         */
        public TouchEvent(Object source, GraphometricPoint eventPoint) {
            super(source);
            EventPoint = eventPoint;
        }
    }
}
