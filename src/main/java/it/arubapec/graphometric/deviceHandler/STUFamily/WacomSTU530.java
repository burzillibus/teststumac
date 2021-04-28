/*
 * Copyright (c) 2016  Aruba S.p.A.
 */
package it.arubapec.graphometric.deviceHandler.STUFamily;

import com.WacomGSS.STU.Tablet;
import com.WacomGSS.STU.UsbDevice;
import it.arubapec.graphometric.deviceHandler.GraphometricTablet;
import it.arubapec.graphometric.exception.GraphometricException;
import it.arubapec.graphometric.utils.Rectangle;
import it.arubapec.graphometric.utils.Point;


/**
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public class WacomSTU530 extends STUFamily {

    private static WacomSTU530 instance = null;

    /**
     * Default constructor
     */
    private WacomSTU530() throws GraphometricException {
        tablet = new Tablet();

        //inizializzo le dimensioni dei pulsanti del tablet
        initButtonsDim();
        super.innerStatus=TabletStatus.Idle;
        DeviceModel="W530";
    }

    /**
     * Return the instance of Wacom STU530.
     *
     * @return
     * @throws it.arubapec.graphometric.exception.GraphometricException
     */
    public static WacomSTU530 getInstance() throws GraphometricException {
        if (instance == null) {
            instance = new WacomSTU530();
        }
        return instance;
    }

    /**
     * Initialize tablet buttons dimensions
     */
    private void initButtonsDim() {
        okRectangle = new Rectangle(591, 406, 191, 60);
        acceptRectangle = new Rectangle(555, 406, 230, 60);
        declineRectangle = new Rectangle(21, 406, 295, 60);
        cancelRectangle = new Rectangle(21, 406, 106, 60);
        resetRectangle = new Rectangle(414, 406, 164, 60);

        skipRectangle = new Rectangle(144, 406, 120, 60);
        scrollConfirmRectangle = new Rectangle(615, 438, 170, 50);
        scrollDownRectangle = new Rectangle(335, 438, 40, 50);
        scrollExitRectangle = new Rectangle(25, 438, 160, 50);
        scrollUpRectangle = new Rectangle(415, 438, 40, 50);

        textScrollDownRectangle = new Rectangle(359, 433, 50, 55);
        textScrollUpRectangle = new Rectangle(433, 433, 50, 55);
    }

    /**
     * Init the device
     *
     * @param device
     * @throws GraphometricException
     */
    @Override
    public void initDevice(UsbDevice device, String... images) throws GraphometricException {
        super.initDevice(device, images);
        super.setHandwritingDisplayArea(35, 35, 30, 100);
    }

    @Override
    public GraphometricTablet getConnectedTablet() {
        return instance;
    }
}
