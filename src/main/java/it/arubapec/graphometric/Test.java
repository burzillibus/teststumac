package it.arubapec.graphometric;


import com.WacomGSS.STU.ITabletHandler;
import it.arubapec.graphometric.deviceHandler.GraphometricTablet;
import it.arubapec.graphometric.deviceHandler.GraphometricTabletFactory;
import it.arubapec.graphometric.exception.GraphometricException;

import static it.arubapec.graphometric.exception.ErrorDefines.DEVICE_NOT_FOUND;


public class Test {

    private GraphometricTablet connectedTablet;

    public static void main(String[] args) {
        Test t = new Test();
        t.init();

    }


    private void init() {
        try {
            connectedTablet = GraphometricTabletFactory.connect(GraphometricTablet.SupportedTablet.Undefined);
        } catch (GraphometricException e) {
            e.printStackTrace();
        }
        if (connectedTablet == null) {
            System.out.println(DEVICE_NOT_FOUND);
        }
        System.out.println("Connected device: " + connectedTablet.getConnectedTabletModel());
        connectedTablet.showNoWritingScreen();
    }
}
