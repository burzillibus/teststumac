 package it.arubapec.graphometric.deviceHandler;

 import com.WacomGSS.STU.ITabletHandler;
 import com.WacomGSS.STU.STUException;
import com.WacomGSS.STU.UsbDevice;
 import it.arubapec.graphometric.deviceHandler.STUFamily.WacomSTU430;
 import it.arubapec.graphometric.deviceHandler.STUFamily.WacomSTU530;
 import it.arubapec.graphometric.exception.GraphometricException;
 import org.apache.commons.lang3.ArrayUtils;

 import java.io.IOException;

 import static it.arubapec.graphometric.exception.ErrorDefines.GENERIC_ERROR;
 import static it.arubapec.graphometric.exception.ErrorDefines.INCOMPATIBLE_OS_ARCHITECTURE;


 public class GraphometricTabletFactory {
 /**
     * Connect to the tablet
     *
     * @param tabletModel
     * @return
     * @throws GraphometricException
     */
    public static GraphometricTablet connect(GraphometricTablet.SupportedTablet tabletModel, String... images) throws GraphometricException {
        try {
             // loading dylib on classpath
        System.loadLibrary("wgssSTU");
        
        // searching for usb devices
        UsbDevice[] usbDevices = UsbDevice.getUsbDevices();
        if (!ArrayUtils.isEmpty(usbDevices)) {
            UsbDevice connectedDevice;
            connectedDevice = usbDevices[0];
            switch (connectedDevice.getIdProduct()) {
                case UsbDevice.ProductId_430:
                    WacomSTU430 stu430 = WacomSTU430.getInstance();
                    stu430.initDevice(connectedDevice, images);
                    return stu430;
                case UsbDevice.ProductId_530:
                    WacomSTU530 stu530 = WacomSTU530.getInstance();
                    stu530.initDevice(connectedDevice, images);
                    return stu530;
                default:
                    return null;
            }
        }
        else return null;

        } catch (GraphometricException ge) {
            throw new GraphometricException(ge.getMessage(), ge.getCodeError(), ge);
        } catch (STUException | IOException e) {
            throw new GraphometricException(e.getMessage(), GENERIC_ERROR);
        } catch (NoSuchMethodError ne) {
            throw new GraphometricException(INCOMPATIBLE_OS_ARCHITECTURE,
                    INCOMPATIBLE_OS_ARCHITECTURE, ne);
        }
    }
}
