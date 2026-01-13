package frc.utils.vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoubleArrayEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.TimestampedDoubleArray;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Limelight helper class for AprilTag detection.
 * Simplified from official LimelightHelpers, focusing on AprilTag/fiducial tracking.
 * 
 * Key NetworkTables values:
 * - tv: Valid target present (0 or 1)
 * - tid: Primary AprilTag ID
 * - botpose_wpiblue: Robot pose in WPILib Blue alliance coordinates
 */
public class LimelightHelpers {

    private static final Map<String, DoubleArrayEntry> doubleArrayEntries = new ConcurrentHashMap<>();

    // ============================================
    // Data Classes
    // ============================================

    /** Raw fiducial/AprilTag detection data */
    public static class RawFiducial {
        public int id;
        public double txnc;       // X offset (no crosshair)
        public double tync;       // Y offset (no crosshair)
        public double ta;         // Target area
        public double distToCamera;
        public double distToRobot;
        public double ambiguity;

        public RawFiducial(int id, double txnc, double tync, double ta, 
                          double distToCamera, double distToRobot, double ambiguity) {
            this.id = id;
            this.txnc = txnc;
            this.tync = tync;
            this.ta = ta;
            this.distToCamera = distToCamera;
            this.distToRobot = distToRobot;
            this.ambiguity = ambiguity;
        }
    }

    /** Pose estimate from MegaTag */
    public static class PoseEstimate {
        public Pose2d pose;
        public double timestampSeconds;
        public double latency;
        public int tagCount;
        public double tagSpan;
        public double avgTagDist;
        public double avgTagArea;
        public RawFiducial[] rawFiducials;
        public boolean isMegaTag2;

        public PoseEstimate() {
            this.pose = new Pose2d();
            this.rawFiducials = new RawFiducial[0];
        }

        public PoseEstimate(Pose2d pose, double timestampSeconds, double latency,
                           int tagCount, double tagSpan, double avgTagDist,
                           double avgTagArea, RawFiducial[] rawFiducials, boolean isMegaTag2) {
            this.pose = pose;
            this.timestampSeconds = timestampSeconds;
            this.latency = latency;
            this.tagCount = tagCount;
            this.tagSpan = tagSpan;
            this.avgTagDist = avgTagDist;
            this.avgTagArea = avgTagArea;
            this.rawFiducials = rawFiducials;
            this.isMegaTag2 = isMegaTag2;
        }
    }

    // ============================================
    // NetworkTables Helpers
    // ============================================

    private static NetworkTable getTable(String limelightName) {
        return NetworkTableInstance.getDefault().getTable(limelightName);
    }

    private static NetworkTableEntry getEntry(String limelightName, String key) {
        return getTable(limelightName).getEntry(key);
    }

    private static double getDouble(String limelightName, String key) {
        return getEntry(limelightName, key).getDouble(0.0);
    }

    private static double[] getDoubleArray(String limelightName, String key) {
        return getEntry(limelightName, key).getDoubleArray(new double[0]);
    }

    private static void setDouble(String limelightName, String key, double value) {
        getEntry(limelightName, key).setNumber(value);
    }

    private static void setDoubleArray(String limelightName, String key, double[] values) {
        getEntry(limelightName, key).setDoubleArray(values);
    }

    private static DoubleArrayEntry getDoubleArrayEntry(String limelightName, String key) {
        String fullKey = limelightName + "/" + key;
        return doubleArrayEntries.computeIfAbsent(fullKey, k -> 
            getTable(limelightName).getDoubleArrayTopic(key).getEntry(new double[0]));
    }

    public static void flush() {
        NetworkTableInstance.getDefault().flush();
    }

    // ============================================
    // Basic Targeting (AprilTags)
    // ============================================

    /** @return true if any valid target (AprilTag) is detected */
    public static boolean getTV(String limelightName) {
        return getDouble(limelightName, "tv") == 1.0;
    }

    /** @return Horizontal offset to target in degrees */
    public static double getTX(String limelightName) {
        return getDouble(limelightName, "tx");
    }

    /** @return Vertical offset to target in degrees */
    public static double getTY(String limelightName) {
        return getDouble(limelightName, "ty");
    }

    /** @return Target area (0-100% of image) */
    public static double getTA(String limelightName) {
        return getDouble(limelightName, "ta");
    }

    /** @return Primary AprilTag ID (-1 if none) */
    public static int getFiducialID(String limelightName) {
        return (int) getDouble(limelightName, "tid");
    }

    /** @return Pipeline latency in milliseconds */
    public static double getLatency_Pipeline(String limelightName) {
        return getDouble(limelightName, "tl");
    }

    /** @return Capture latency in milliseconds */
    public static double getLatency_Capture(String limelightName) {
        return getDouble(limelightName, "cl");
    }

    // ============================================
    // AprilTag Raw Fiducials
    // ============================================

    /** @return Array of all detected AprilTags with detailed data */
    public static RawFiducial[] getRawFiducials(String limelightName) {
        double[] data = getDoubleArray(limelightName, "rawfiducials");
        int valsPerEntry = 7;
        
        if (data.length == 0 || data.length % valsPerEntry != 0) {
            return new RawFiducial[0];
        }

        int count = data.length / valsPerEntry;
        RawFiducial[] fiducials = new RawFiducial[count];

        for (int i = 0; i < count; i++) {
            int base = i * valsPerEntry;
            fiducials[i] = new RawFiducial(
                (int) data[base],      // id
                data[base + 1],        // txnc
                data[base + 2],        // tync
                data[base + 3],        // ta
                data[base + 4],        // distToCamera
                data[base + 5],        // distToRobot
                data[base + 6]         // ambiguity
            );
        }
        return fiducials;
    }

    // ============================================
    // MegaTag2 Pose Estimation
    // ============================================

    /**
     * Sets the robot orientation for MegaTag2 localization.
     * Call this each frame before getting MT2 pose.
     * @param yaw Robot yaw in degrees (0 = facing red alliance wall)
     */
    public static void SetRobotOrientation(String limelightName, double yaw,
                                           double yawRate, double pitch, 
                                           double pitchRate, double roll, double rollRate) {
        double[] data = {yaw, yawRate, pitch, pitchRate, roll, rollRate};
        setDoubleArray(limelightName, "robot_orientation_set", data);
        flush();
    }

    /**
     * Gets MegaTag2 pose estimate in WPILib Blue alliance coordinates.
     * @return PoseEstimate or null if no valid pose
     */
    public static PoseEstimate getBotPoseEstimate_wpiBlue_MegaTag2(String limelightName) {
        return getBotPoseEstimate(limelightName, "botpose_orb_wpiblue", true);
    }

    /**
     * Gets MegaTag1 pose estimate in WPILib Blue alliance coordinates.
     * @return PoseEstimate or null if no valid pose
     */
    public static PoseEstimate getBotPoseEstimate_wpiBlue(String limelightName) {
        return getBotPoseEstimate(limelightName, "botpose_wpiblue", false);
    }

    private static PoseEstimate getBotPoseEstimate(String limelightName, String entryName, boolean isMegaTag2) {
        DoubleArrayEntry entry = getDoubleArrayEntry(limelightName, entryName);
        TimestampedDoubleArray tsValue = entry.getAtomic();
        double[] data = tsValue.value;
        long timestamp = tsValue.timestamp;

        if (data.length == 0) {
            return null;
        }

        Pose2d pose = toPose2D(data);
        double latency = data.length > 6 ? data[6] : 0;
        int tagCount = data.length > 7 ? (int) data[7] : 0;
        double tagSpan = data.length > 8 ? data[8] : 0;
        double avgTagDist = data.length > 9 ? data[9] : 0;
        double avgTagArea = data.length > 10 ? data[10] : 0;

        double adjustedTimestamp = (timestamp / 1000000.0) - (latency / 1000.0);

        // Parse raw fiducials from pose data
        RawFiducial[] fiducials = new RawFiducial[tagCount];
        int valsPerFiducial = 7;
        int expectedLength = 11 + valsPerFiducial * tagCount;

        if (data.length >= expectedLength) {
            for (int i = 0; i < tagCount; i++) {
                int base = 11 + i * valsPerFiducial;
                fiducials[i] = new RawFiducial(
                    (int) data[base],
                    data[base + 1],
                    data[base + 2],
                    data[base + 3],
                    data[base + 4],
                    data[base + 5],
                    data[base + 6]
                );
            }
        }

        return new PoseEstimate(pose, adjustedTimestamp, latency, tagCount, 
                               tagSpan, avgTagDist, avgTagArea, fiducials, isMegaTag2);
    }

    // ============================================
    // Pose Conversion Helpers
    // ============================================

    private static Pose2d toPose2D(double[] data) {
        if (data.length < 6) {
            return new Pose2d();
        }
        return new Pose2d(
            new Translation2d(data[0], data[1]),
            new Rotation2d(Units.degreesToRadians(data[5]))
        );
    }

    // ============================================
    // Camera Controls
    // ============================================

    /** Sets the active pipeline (0-9) */
    public static void setPipeline(String limelightName, int pipeline) {
        setDouble(limelightName, "pipeline", Math.max(0, Math.min(9, pipeline)));
    }

    /** @return Current pipeline index */
    public static int getPipeline(String limelightName) {
        return (int) getDouble(limelightName, "getpipe");
    }

    /** LED mode constants */
    public enum LedMode {
        PIPELINE(0), OFF(1), BLINK(2), ON(3);
        public final int value;
        LedMode(int value) { this.value = value; }
    }

    /** Sets the LED mode */
    public static void setLedMode(String limelightName, LedMode mode) {
        setDouble(limelightName, "ledMode", mode.value);
    }

    // ============================================
    // Validation Helpers
    // ============================================

    /** @return true if Limelight is connected (has expected NT keys) */
    public static boolean isConnected(String limelightName) {
        return getTable(limelightName).containsKey("tv");
    }

    /** @return true if pose estimate is valid and has detected tags */
    public static boolean validPoseEstimate(PoseEstimate pose) {
        return pose != null && pose.tagCount > 0 && pose.rawFiducials != null;
    }
}
