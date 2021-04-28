package it.arubapec.graphometric;


import it.arubapec.graphometric.deviceHandler.GraphometricTablet;
import it.arubapec.graphometric.deviceHandler.GraphometricTabletFactory;

import static it.arubapec.graphometric.exception.ErrorDefines.DEVICE_NOT_FOUND;


public class Test {

    private GraphometricTablet connectedTablet;

    public static void main(String[] args) {
        Test t = new Test();
        t.init();

    }


    private void init() {
        connectedTablet = GraphometricTabletFactory.connect(null, null);
        if (connectedTablet == null) {
            System.out.println(DEVICE_NOT_FOUND);
        }
        System.out.println("Connected device: " + connectedTablet.getConnectedTabletModel());
        connectedTablet.showNoWritingScreen();
    }
}
