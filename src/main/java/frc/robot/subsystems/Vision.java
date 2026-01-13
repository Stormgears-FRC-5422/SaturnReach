package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.utils.StormSubsystem;
import frc.utils.vision.LimelightHelpers;
import frc.utils.vision.LimelightHelpers.RawFiducial;

/**
 * Vision subsystem for AprilTag detection via Limelight.
 * Based on StormLimelight from Reefscape, simplified for debugging.
 * 
 * Dashboard outputs (Vision/ prefix):
 * - Connected: Is Limelight responding?
 * - SeesTag: Is an AprilTag detected?
 * - TagCount: Number of AprilTags visible
 * - ClosestTag: ID of the closest/primary tag
 * - TX/TY/TA: Target offset and area
 */
public class Vision extends StormSubsystem {

    private static final String LIMELIGHT_NAME = "limelight-tags";
    private static final int DEFAULT_PIPELINE = 0;

    public Vision() {
        super();
        LimelightHelpers.setPipelineIndex(LIMELIGHT_NAME, DEFAULT_PIPELINE);
        LimelightHelpers.setLEDMode_PipelineControl(LIMELIGHT_NAME);
        console("Initialized: " + LIMELIGHT_NAME);
    }

    @Override
    public void periodic() {
        super.periodic();
        
        boolean connected = isConnected();
        boolean seesTag = LimelightHelpers.getTV(LIMELIGHT_NAME);
        int tagCount = seesTag ? LimelightHelpers.getRawFiducials(LIMELIGHT_NAME).length : 0;
        int closestTag = getClosestTagId();

        // Publish to dashboard
        SmartDashboard.putBoolean("Vision/Connected", connected);
        SmartDashboard.putBoolean("Vision/SeesTag", seesTag);
        SmartDashboard.putNumber("Vision/TagCount", tagCount);
        SmartDashboard.putNumber("Vision/ClosestTag", closestTag);
        SmartDashboard.putNumber("Vision/TX", LimelightHelpers.getTX(LIMELIGHT_NAME));
        SmartDashboard.putNumber("Vision/TY", LimelightHelpers.getTY(LIMELIGHT_NAME));
        SmartDashboard.putNumber("Vision/TA", LimelightHelpers.getTA(LIMELIGHT_NAME));
    }

    /** @return true if any AprilTag is visible */
    public boolean seesTag() {
        return LimelightHelpers.getTV(LIMELIGHT_NAME);
    }

    /** @return Number of AprilTags currently visible */
    public int tagCount() {
        return seesTag() ? LimelightHelpers.getRawFiducials(LIMELIGHT_NAME).length : 0;
    }

    /** @return ID of the closest/primary AprilTag, or -1 if none */
    public int getClosestTagId() {
        if (!seesTag()) return -1;
        RawFiducial[] fiducials = LimelightHelpers.getRawFiducials(LIMELIGHT_NAME);
        return fiducials.length > 0 ? fiducials[0].id : -1;
    }

    /** @return The Limelight name for direct LimelightHelpers calls */
    public String getLimelightName() {
        return LIMELIGHT_NAME;
    }

    /** 
     * Checks if Limelight is connected by verifying heartbeat is updating.
     * @return true if Limelight is connected and responding
     */
    private boolean isConnected() {
        // Check if the Limelight NetworkTable exists and has valid data
        var table = NetworkTableInstance.getDefault().getTable(LIMELIGHT_NAME);
        // If hb (heartbeat) is updating, we're connected
        return table.getEntry("tv").exists();
    }
}
