/*
 * Defines
 *
 * Created on 21-giu-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric;

import it.arubapec.graphometric.exception.GraphometricException;

/**
 *
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class Defines {

    private static final String root = "it/arubapec/graphometric/tablet/";

    //<editor-fold defaultstate="expanded" desc="Resources">    
    public static final String IMG_NO_WRITE = "no_write.jpg";
    public static final String IMG_WRITE = "write_image.png";
    public static final String IMG_CONSENSO = "consenso.jpg";
    public static final String IMG_HOURGLASS = "hourglass.jpg";
    public static final String IMG_WRITE_SKIP = "write_image_skip.png";
    
    public static final String SCROLL_INTERFACE = "scroll_interface.jpg";
    public static final String SCROLL_INTERFACE_FIRST = "scroll_interface_first.jpg";
    public static final String SCROLL_INTERFACE_FIRST_NO_BUTTONS = "scroll_interface_first_no_buttons.jpg";
    public static final String SCROLL_INTERFACE_LAST = "scroll_interface_last.jpg";
    public static final String SCROLL_INTERFACE_LAST_NO_BUTTONS = "scroll_interface_last_no_buttons.jpg";
    public static final String SCROLL_INTERFACE_NO_BUTTONS = "scroll_interface_no_buttons.jpg";
    public static final String SCROLL_INTERFACE_NO_SCROLL = "scroll_interface_noscroll.jpg";
    public static final String SCROLL_INTERFACE_NO_SCROLL_NO_BUTTONS = "scroll_interface_noscroll_no_buttons.jpg";

    public static final String PRIVACY_NO_SCROLL = "consenso.jpg";
    public static final String PRIVACY_SCROLL_FIRST = "consenso_scroll_first.jpg";
    public static final String PRIVACY_SCROLL_LAST = "consenso_scroll_last.jpg";
    public static final String PRIVACY_SCROLL_FULL = "consenso_scroll_full.jpg";

    
    //Risorse per le wacom DTU
    public static final String IMG_FRM_CONSENSO_NO = "NonAcconsento.png";
    public static final String IMG_FRM_CONSENSO_SI = "Acconsento.png";
    public static final String IMG_FRM_ESCI = "esci.png";
    public static final String IMG_FRM_CONFERMA = "conferma.png";
    public static final String IMG_FRM_PULISCI = "pulisci.png";
    public static final String IMG_NO_WRITE_DTU = "no_write_aruba.png";
    public static final String IMG_FRM_FIRMA = "PulsanteFirma60.png";
    public static final String IMG_FRM_OK = "ok.png";
    public static final String IMG_FRM_CANCEL = "cancel.png";
    // </editor-fold>


    /**
     * Return the path to java package that contains the resource for the STU
     * devices
     *
     * @param deviceModel the device model. Example "W430" for STU 430
     * @param resource the resource name to get
     * @return the string that represent the path to the java package. I.E.
     * "it/arubapec/graphometric/resources/tablet/scroll_interface_noscroll_no_buttons.jpg"
     * @throws it.arubapec.graphometric.exception.GraphometricException
     */
    public static String getSTUResource(String deviceModel, String resource) throws GraphometricException {
        return root + deviceModel + "/" + resource;
    }

}
