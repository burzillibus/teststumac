/*
 * ErrorDefines
 *
 * Created on 5-set-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric.exception;

/**
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class ErrorDefines {

    private ErrorDefines() {
        throw new IllegalStateException("Utility class");
    }

    //<editor-fold defaultstate="expanded" desc="Errors">    
    public static final String NOT_FOUND_ERROR = "NOT_FOUND_ERROR";
    public static final String DEVICE_NOT_FOUND = "DEVICE_NOT_FOUND";
    public static final String DEVICE_DISCONNECTED = "DEVICE_DISCONNECTED";
    public static final String WRONG_DTU_RESOLUTION = "WRONG_DTU_RESOLUTION";
    public static final String GENERIC_ERROR = "GENERIC_ERROR";
    public static final String PROPERTIES_NOT_LOADED = "PROPERTIES_NOT_LOADED";
    public static final String DEVICE_INITIALIZED = "DEVICE_INITIALIZED";
    public static final String ERROR_CONNECT_DEVICE = "ERROR_CONNECT_DEVICE";
    public static final String INSTANCE_INITIALIZED = "INSTANCE_INITIALIZED";
    public static final String WRITING_DISPLAY_ERROR = "WRITING_DISPLAY_ERROR";
    public static final String RESOURCES_NOT_INITIALIZED = "RESOURCES_NOT_INITIALIZED";
    public static final String INCOMPATIBLE_OS_ARCHITECTURE = "INCOMPATIBLE_OS_ARCHITECTURE";
    public static final String PRIVACY_TEXT_EMPTY = "PRIVACY_TEXT_EMPTY";
    public static final String DEVICE_INCONSISTENT = "DEVICE_INCONSISTENT";
    public static final String SCREEN_NOT_SELECTED = "SCREEN_NOT_SELECTED";
    public static final String SCREEN_OUT_OF_BOUND = "SCREEN_OUT_OF_BOUND";
    public static final String CERTIFICATE_NOT_VALUED = "CERTIFICATE_NOT_VALUED";
    public static final String CERTIFICATE_PASSWORD_NULL = "CERTIFICATE_PASSWORD_NULL";
    public static final String DEVICE_SIGNING = "DEVICE_SIGNING";
    public static final String IO_ERROR = "IO_ERROR";
    public static final String RESOURCES_NOT_INSTALLED = "RESOURCES_NOT_INSTALLED";
    public static final String ONLY_FOR_DTU = "ONLY_FOR_DTU";
    public static final String ONLY_FOR_STU = "ONLY_FOR_STU";
    public static final String NOT_SUPPORTED = "NOT_SUPPORTED";
    public static final String SIGNATURE_IMAGE_NOT_VALUED = "SIGNATURE_IMAGE_NOT_VALUED";
    public static final String PRODUCT_NOT_PROVIDED = "PRODUCT_NOT_PROVIDED";
    //</editor-fold>
}
