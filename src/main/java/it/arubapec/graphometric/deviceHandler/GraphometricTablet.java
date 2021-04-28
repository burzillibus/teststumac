package it.arubapec.graphometric.deviceHandler;


public abstract class GraphometricTablet {

    protected SupportedTablet connectedTabletModel;

    /**
     * Set tablet in standby mode. Disable iniking mode and show the no writing
     * screen image
     */
    public abstract void showNoWritingScreen(String... images);

    /**
     * Return the instance of the connected tablet
     *
     * @return
     */
    public abstract GraphometricTablet getConnectedTablet();

    /**
     * Return the model of the connected tablet
     *
     * @return the SupportedTablet model
     */
    public SupportedTablet getConnectedTabletModel() {
        return this.connectedTabletModel;
    }

    /**
     * Enumeration of all supported tablet.
     * <ul>
     * Supported devices:
     * <li>WacomSTU530</li>
     * <li>WacomSTU430</li>
     * <li>WacomDTU1141</li>
     * <li>Undefined</li>
     * </ul>
     */
    public enum SupportedTablet {
        WacomStu530, WacomStu430, WacomDtu1141, Undefined
    }

}
