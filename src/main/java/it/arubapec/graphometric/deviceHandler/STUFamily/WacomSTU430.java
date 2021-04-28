/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.deviceHandler.STUFamily;

import com.WacomGSS.STU.Protocol.*;
import com.WacomGSS.STU.STUException;
import com.WacomGSS.STU.Tablet;
import com.WacomGSS.STU.UsbDevice;
import it.arubapec.graphometric.deviceHandler.GraphometricTablet;
import it.arubapec.graphometric.exception.GraphometricException;
import it.arubapec.graphometric.utils.Rectangle;

import java.io.IOException;

/**
 * @author Francesco Sighieri <francesco.sighieri@staff.aruba.it>
 */
public class WacomSTU430 extends STUFamily {

    private static WacomSTU430 instance = null;

    /**
     * Costruttore Pubblico per la tavoletta Wacom STU430. Avvia la connessione
     * Usb e carica le impostazioni del device.
     *
     * @throws GraphometricException
     * @throws STUException
     * @throws IOException
     */
    private WacomSTU430() throws GraphometricException, STUException, IOException {
        tablet = new Tablet();

        this.initButtonsDim();
        DeviceModel = "W430";
    }

    /**
     * Return the instance
     *
     * @return
     * @throws it.arubapec.graphometric.exception.GraphometricException
     * @throws com.WacomGSS.STU.STUException
     * @throws IOException
     */
    public static WacomSTU430 getInstance() throws GraphometricException, STUException, IOException {
        if (instance == null) {
            instance = new WacomSTU430();
        }
        return instance;
    }

    /**
     * Initialize the device
     *
     * @param device
     * @throws GraphometricException
     */
    @Override
    public void initDevice(UsbDevice device, String... images) throws GraphometricException {
        super.initDevice(device, images);
        super.setHandwritingDisplayArea(10, 10, 10, 45);
    }

    /**
     * Initialize tablet buttons dimensions
     */
    private void initButtonsDim() {
        okRectangle = new Rectangle(224, 160, 92, 33);
        acceptRectangle = new Rectangle(218, 160, 97, 33);
        declineRectangle = new Rectangle(4, 160, 125, 33);
        cancelRectangle = new Rectangle(5, 160, 45, 33);
        resetRectangle = new Rectangle(158, 160, 62, 33);

        skipRectangle = new Rectangle(52, 160, 62, 32);
        scrollConfirmRectangle = new Rectangle(250, 180, 70, 43);
        scrollDownRectangle = new Rectangle(121, 180, 36, 43);
        scrollExitRectangle = new Rectangle(4, 180, 70, 43);
        scrollUpRectangle = new Rectangle(162, 180, 36, 43);

        textScrollDownRectangle = new Rectangle(143, 163, 30, 37);
        textScrollUpRectangle = new Rectangle(178, 163, 30, 37);
    }


    @Override
    public GraphometricTablet getConnectedTablet() {
        return instance;
    }
}
