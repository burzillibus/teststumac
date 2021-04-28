/*
 * STUFamily
 *
 * Created on 4-lug-2016
 *
 * Copyright(c) 2016 Aruba S.p.A.
 *
 */
package it.arubapec.graphometric.deviceHandler.STUFamily;

import com.WacomGSS.STU.ITabletHandler;
import com.WacomGSS.STU.Protocol.*;
import com.WacomGSS.STU.STUException;
import com.WacomGSS.STU.Tablet;
import com.WacomGSS.STU.UsbDevice;
import it.arubapec.graphometric.AgiLib;
import it.arubapec.graphometric.Defines;
import it.arubapec.graphometric.deviceHandler.encryptionHandler.AgiEncryptionHandler;
import it.arubapec.graphometric.deviceHandler.encryptionHandler.AgiEncryptionHandler2;
import it.arubapec.graphometric.deviceHandler.event.TabletEvent;
import it.arubapec.graphometric.exception.GraphometricException;
import it.arubapec.graphometric.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static it.arubapec.graphometric.Defines.*;
import static it.arubapec.graphometric.exception.ErrorDefines.*;
import it.arubapec.graphometric.utils.Rectangle;

/**
 * @author Vincenzo Mangiapanello <vincenzo.mangiapanello@staff.aruba.it>
 */
public abstract class STUFamily implements ITabletHandler {
    public String DeviceModel;

    protected Tablet tablet;
    protected boolean useColor;
    protected EncodingMode encodingMode;
    //Coefficenti di scala fra sensore e pixel
    protected double xCoeff, yCoeff;
    //le dimensioni dei pulsanti
    protected Rectangle okRectangle;
    protected Rectangle acceptRectangle;
    protected Rectangle declineRectangle;
    protected Rectangle cancelRectangle;
    protected Rectangle resetRectangle;
    protected Rectangle skipRectangle, scrollConfirmRectangle, scrollDownRectangle, scrollExitRectangle, scrollUpRectangle, textScrollDownRectangle, textScrollUpRectangle;
    protected UsbDevice usbdevice;
    //per la gestione del pdf preview
    protected int tabLowBorder = 0;
    protected int tabHeight = 0;
    protected int tabWidth = 0;
    //Immagini di default rasterizzate per la tavoletta
    private byte[] noWritingScreenImage, signingScreenImage, hourGlass, signingScreenImageSkip;
    //Random generated sesion id
    private int sessionId;
    private boolean canRaiseEvent = true; //TODO verificare che sia veramente necessario
    //L'ultimo punto rilevato era a contatto con la schermo
    private boolean wasDown = false;
    //Lo schermo è stato toccato almeno una volta
    private boolean firstTouchOccurred = false;
    private int elapsedMillisecond = 0;
    private int shortModuleAccumulator = 0;
    private int lastTime = 0;
    private int actualStroke = -1;
    private BufferedImage[] chunkList;
    private int actualChunk = 0;
    private boolean scrollingPreview = false;

    //per la gestione del text preview
    private boolean scrollingText = false;
    private int actualPageText = 0;
    private List<String> pagesText = new ArrayList<>();
    private BufferedImage scrollLastImg;
    private BufferedImage scrollNoImg;
    private BufferedImage scrollFullImg;
    private BufferedImage scrollFirstImg;
    private int fontSize;
    private Graphics genericGraphic;
    private FontMetrics fontMetrics;
    private int textX, textY;
    /**
     * Enumeration of the tablet status.
     * <ul>
     * Possible states are:
     * <li>Idle</li>
     * <li>Signing: tablet is on signing mode</li>
     * <li>Previewing: tablet is previewing the document to sign</li>
     * </ul>
     */
    public enum TabletStatus {
        Idle, Signing, Previewing, inPrivacy
    }

    protected TabletStatus innerStatus;

    /**
     * Init the device
     *
     * @param device the device connected
     * @throws GraphometricException
     */
    public void initDevice(UsbDevice device, String... images) throws GraphometricException {

        usbdevice = device;
        //Handler per la cifratura del canale
        if (Objects.isNull(tablet.getEncryptionHandler()) || Objects.isNull(tablet.getEncryptionHandler2())) {
            tablet.setEncryptionHandler(new AgiEncryptionHandler());
            tablet.setEncryptionHandler2(new AgiEncryptionHandler2());
        }

        try {
            //Connessione alla tavoletta
            int error = tablet.usbConnect(device, false);
            if (error != 0) {
                throw new GraphometricException(String.format("%1: %2", ERROR_CONNECT_DEVICE, error), ERROR_CONNECT_DEVICE);
            }

            BigInteger prime = new BigInteger(128, new SecureRandom()).nextProbablePrime();
            while (!prime.isProbablePrime(1024)) {
                prime = new BigInteger(128, new SecureRandom()).nextProbablePrime();
            }
            tablet.setPenDataOptionMode(PenDataOptionMode.TimeCountSequence);

            try {
                tablet.getEncryptionHandler().setDH(new DHprime(Utils.bigIntToByte(prime)), new DHbase(Utils.intToByteArray(5)));
            } catch (IllegalArgumentException ie) {
                throw new GraphometricException(ie);
            }
            //Setup variabili di scrittura sulla tavoletta
            byte encodingFlag = ProtocolHelper.simulateEncodingFlag(tablet.getProductId(), tablet.getCapability().getEncodingFlag());
            useColor = ProtocolHelper.encodingFlagSupportsColor(encodingFlag);
            useColor = useColor && tablet.supportsWrite();
            if (useColor) {
                if (tablet.supportsWrite()) {
                    encodingMode = EncodingMode.EncodingMode_16bit_Bulk;
                } else {
                    encodingMode = EncodingMode.EncodingMode_16bit;
                }
            } else {
                encodingMode = EncodingMode.EncodingMode_1bit;
            }
            if (images != null && images.length > 0) {
                initResources(images[0], images[1], images[2], images[3]);
            } else {
                initResources();
            }
            int width = tablet.getCapability().getScreenWidth();
            int height = tablet.getCapability().getScreenHeight();
            xCoeff = (float) width / (float) tablet.getCapability().getTabletMaxX();
            yCoeff = (float) height / (float) tablet.getCapability().getTabletMaxY();

            initGraphicText();
        } catch (STUException ex) {
            throw new GraphometricException(ex);
        }
    }


    /**
     * Initialize the graphic resource used to show text privacy
     */
    private void initGraphicText() throws GraphometricException {
        try {
            scrollLastImg = ImageIO.read(AgiLib.getInstance().getClass().getClassLoader().getResource(Defines.getSTUResource(DeviceModel, PRIVACY_SCROLL_LAST)));
            scrollNoImg = ImageIO.read(AgiLib.getInstance().getClass().getClassLoader().getResource(Defines.getSTUResource(DeviceModel, PRIVACY_NO_SCROLL)));
            scrollFullImg = ImageIO.read(AgiLib.getInstance().getClass().getClassLoader().getResource(Defines.getSTUResource(DeviceModel, PRIVACY_SCROLL_FULL)));
            scrollFirstImg = ImageIO.read(AgiLib.getInstance().getClass().getClassLoader().getResource(Defines.getSTUResource(DeviceModel, PRIVACY_SCROLL_FIRST)));
        } catch (IOException e) {
            throw new GraphometricException(e);
        }
    }

    /**
     * Init the resources used by the tablet
     *
     * @throws it.arubapec.graphometric.exception.GraphometricException
     */
    private void initResources() throws GraphometricException {
        //Carica le immagini di default e la renderizza per il device
        noWritingScreenImage = this.resourceToByte(Defines.getSTUResource(DeviceModel, IMG_NO_WRITE));
        signingScreenImage = this.resourceToByte(Defines.getSTUResource(DeviceModel, IMG_WRITE));
        hourGlass = this.resourceToByte(Defines.getSTUResource(DeviceModel, IMG_HOURGLASS));
        signingScreenImageSkip = this.resourceToByte(Defines.getSTUResource(DeviceModel, IMG_WRITE_SKIP));
    }

    /**
     * Init the resources used by the tablet
     *
     * @throws it.arubapec.graphometric.exception.GraphometricException
     */
    public void initResources(String imgNoWrite, String imgWrite, String imgHourglass, String imgWriteSkip) throws GraphometricException {
        noWritingScreenImage = StringUtils.isNotBlank(imgNoWrite) ? resourceToByte(imgNoWrite) : resourceToByte(Defines.getSTUResource(DeviceModel, IMG_NO_WRITE));
        signingScreenImage = StringUtils.isNotBlank(imgWrite) ? resourceToByte(imgWrite) : resourceToByte(Defines.getSTUResource(DeviceModel, IMG_WRITE));
        hourGlass = StringUtils.isNotBlank(imgHourglass) ? resourceToByte(imgHourglass) : resourceToByte(Defines.getSTUResource(DeviceModel, IMG_HOURGLASS));
        signingScreenImageSkip = StringUtils.isNotBlank(imgWriteSkip) ? resourceToByte(imgWriteSkip) : resourceToByte(Defines.getSTUResource(DeviceModel, IMG_WRITE_SKIP));
    }

    /**
     * Return a byte array representing the image to show on the tablet
     *
     * @param resourcename the key name of the hashmap pointing to the image to
     *                     show
     * @return the array byte
     * @throws GraphometricException
     */
    private byte[] resourceToByte(String resourcename) throws GraphometricException {
        try {
            //Carica le immagini di default e le renderizza per il device
            BufferedImage bitmap = ImageIO.read(STUFamily.class.getClassLoader().getResource(resourcename));
            return ProtocolHelper.flatten(bitmap, bitmap.getWidth(), bitmap.getHeight(), useColor);
        } catch (IllegalArgumentException ie) {
            throw new GraphometricException(ie);
        } catch (IOException ex) {
            throw new GraphometricException(RESOURCES_NOT_INITIALIZED + ": " + resourcename, RESOURCES_NOT_INITIALIZED, ex);
        }
    }


    /**
     * Set tablet in standby mode. Disable iniking mode and show
     * noWritingScreenImage
     */

    public void showNoWritingScreen(String... images) {
        try {
            innerStatus = TabletStatus.Idle;
            tablet.setInkingMode(InkingMode.Off);
            tablet.writeImage(encodingMode, noWritingScreenImage);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }



    @Override
    public void onEventDataSignatureEncrypted(EventDataSignatureEncrypted encrypted) {

    }

    @Override
    public void onEventDataPinPad(EventDataPinPad eventDataPinPad) {

    }

    @Override
    public void onEventDataKeyPad(EventDataKeyPad eventDataKeyPad) {

    }

    @Override
    public void onEventDataSignature(EventDataSignature eventDataSignature) {

    }

    @Override
    public void onEventDataPinPadEncrypted(EventDataPinPadEncrypted eventDataPinPadEncrypted) {

    }

    @Override
    public void onEventDataKeyPadEncrypted(EventDataKeyPadEncrypted eventDataKeyPadEncrypted) {

    }

}
