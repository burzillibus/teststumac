/*
 * Utils
 *
 * Created on 22-giu-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric.utils;


import it.arubapec.graphometric.exception.GraphometricException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;

import static it.arubapec.graphometric.exception.ErrorDefines.*;

/**
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * Load the QTJambi and STU dll for the correct Java architecture
     *
     * @throws GraphometricException
     */
    public static void loadlib() throws GraphometricException {
        System.loadLibrary("wgssSTU");
    }

    /**
     * Returns the specified 32-bit signed integer value as an array of bytes of
     * 2 items. It is specific to calculate the DHBase
     *
     * @param a The value to convert
     * @return the int converted in byte[2]
     */
    public static byte[] intToByteArray(int a) {
        //lo limito ad un array di 2 perche' altrimenti eccede la lunghezza prevista per DHBase.
        byte[] ret = new byte[2];
        ret[1] = (byte) (a & 0xFF);
        ret[0] = (byte) ((a >> 8) & 0xFF);
        return ret;
    }

    /**
     * Convert BigInteger to byte without sign bit
     *
     * @param bigInteger the BigInteger to convert
     * @return BigInteger converted in byte array
     */
    public static byte[] bigIntToByte(BigInteger bigInteger) {
        byte[] array = bigInteger.toByteArray();
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }
        return array;
    }
}
