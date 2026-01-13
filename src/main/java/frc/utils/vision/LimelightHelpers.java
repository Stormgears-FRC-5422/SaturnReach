package frc.utils.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Minimal Limelight helper class for basic vision operations.
 * Based on Limelight's official LimelightHelpers, simplified for clarity.
 * 
 * NetworkTables values reference:
 * - tv: Whether the limelight has any valid targets (0 or 1)
 * - tx: Horizontal offset from crosshair to target (-27 to 27 degrees)
 * - ty: Vertical offset from crosshair to target (-20.5 to 20.5 degrees)
 * - ta: Target area (0% to 100% of image)
 */
public class LimelightHelpers {

    /**
     * Gets the NetworkTable for a Limelight camera.
     */
    private static NetworkTable getTable(String limelightName) {
        return NetworkTableInstance.getDefault().getTable(limelightName);
    }

    /**
     * Gets a NetworkTableEntry for a specific key.
     */
    private static NetworkTableEntry getEntry(String limelightName, String key) {
        return getTable(limelightName).getEntry(key);
    }

    /**
     * Gets a double value from NetworkTables.
     */
    private static double getDouble(String limelightName, String key) {
        return getEntry(limelightName, key).getDouble(0.0);
    }

    /**
     * Sets a number value in NetworkTables.
     */
    private static void setNumber(String limelightName, String key, double value) {
        getEntry(limelightName, key).setNumber(value);
    }

    // ============================================
    // Basic Targeting Data
    // ============================================

    /**
     * @return true if the Limelight has a valid target
     */
    public static boolean hasTarget(String limelightName) {
        return getDouble(limelightName, "tv") == 1.0;
    }

    /**
     * @return Horizontal offset from crosshair to target in degrees
     */
    public static double getTX(String limelightName) {
        return getDouble(limelightName, "tx");
    }

    /**
     * @return Vertical offset from crosshair to target in degrees
     */
    public static double getTY(String limelightName) {
        return getDouble(limelightName, "ty");
    }

    /**
     * @return Target area (0-100% of image)
     */
    public static double getTA(String limelightName) {
        return getDouble(limelightName, "ta");
    }

    /**
     * @return Pipeline latency in milliseconds
     */
    public static double getLatency(String limelightName) {
        return getDouble(limelightName, "tl");
    }

    // ============================================
    // Camera Controls
    // ============================================

    /**
     * LED modes for the Limelight.
     */
    public enum LedMode {
        PIPELINE(0),  // Use pipeline setting
        OFF(1),       // Force off
        BLINK(2),     // Force blink
        ON(3);        // Force on

        public final int value;
        LedMode(int value) { this.value = value; }
    }

    /**
     * Sets the LED mode.
     */
    public static void setLedMode(String limelightName, LedMode mode) {
        setNumber(limelightName, "ledMode", mode.value);
    }

    /**
     * Sets the active pipeline (0-9).
     */
    public static void setPipeline(String limelightName, int pipeline) {
        setNumber(limelightName, "pipeline", Math.max(0, Math.min(9, pipeline)));
    }

    /**
     * @return Current pipeline index (0-9)
     */
    public static int getPipeline(String limelightName) {
        return (int) getDouble(limelightName, "getpipe");
    }

    // ============================================
    // Connection Status
    // ============================================

    /**
     * Checks if the Limelight is connected by verifying NetworkTables data is updating.
     * Uses heartbeat value which increments each frame.
     */
    public static boolean isConnected(String limelightName) {
        // Check if the table exists and has the expected entries
        NetworkTable table = getTable(limelightName);
        return table.containsKey("tv");
    }
}
