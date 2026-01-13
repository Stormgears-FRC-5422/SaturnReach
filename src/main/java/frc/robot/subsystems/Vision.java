package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.utils.StormSubsystem;
import frc.utils.vision.LimelightHelpers;
import frc.utils.vision.LimelightHelpers.LedMode;

/**
 * Vision subsystem for Limelight camera.
 * Provides target detection data and camera controls.
 * 
 * Dashboard outputs:
 * - Vision/Connected: Is the Limelight connected?
 * - Vision/HasTarget: Is a target currently detected?
 * - Vision/TX: Horizontal offset to target (degrees)
 * - Vision/TY: Vertical offset to target (degrees)
 * - Vision/TA: Target area (percentage)
 * - Vision/Pipeline: Current pipeline number
 */
public class Vision extends StormSubsystem {

    // Limelight network name (configured in Limelight web interface)
    private static final String LIMELIGHT_NAME = "limelight-tags";
    
    // Default pipeline to use on startup
    private static final int DEFAULT_PIPELINE = 0;

    // Cached values for efficiency
    private boolean connected = false;
    private boolean hasTarget = false;
    private double tx = 0.0;
    private double ty = 0.0;
    private double ta = 0.0;
    private int currentPipeline = 0;

    public Vision() {
        super();
        
        // Set default pipeline and LED mode on startup
        setPipeline(DEFAULT_PIPELINE);
        setLedMode(LedMode.PIPELINE);
        
        console("Initialized with Limelight: " + LIMELIGHT_NAME);
    }

    @Override
    public void periodic() {
        super.periodic();
        
        // Update cached values from NetworkTables
        connected = LimelightHelpers.isConnected(LIMELIGHT_NAME);
        hasTarget = LimelightHelpers.hasTarget(LIMELIGHT_NAME);
        tx = LimelightHelpers.getTX(LIMELIGHT_NAME);
        ty = LimelightHelpers.getTY(LIMELIGHT_NAME);
        ta = LimelightHelpers.getTA(LIMELIGHT_NAME);
        currentPipeline = LimelightHelpers.getPipeline(LIMELIGHT_NAME);

        // Publish to SmartDashboard
        SmartDashboard.putBoolean("Vision/Connected", connected);
        SmartDashboard.putBoolean("Vision/HasTarget", hasTarget);
        SmartDashboard.putNumber("Vision/TX", tx);
        SmartDashboard.putNumber("Vision/TY", ty);
        SmartDashboard.putNumber("Vision/TA", ta);
        SmartDashboard.putNumber("Vision/Pipeline", currentPipeline);
    }

    // ============================================
    // Controls
    // ============================================

    /**
     * Switch to a different pipeline.
     * @param pipeline Pipeline index (0-9)
     */
    public void setPipeline(int pipeline) {
        LimelightHelpers.setPipeline(LIMELIGHT_NAME, pipeline);
        console("Pipeline set to " + pipeline);
    }

    /**
     * Set the LED mode.
     * @param mode LED mode (PIPELINE, OFF, BLINK, ON)
     */
    public void setLedMode(LedMode mode) {
        LimelightHelpers.setLedMode(LIMELIGHT_NAME, mode);
    }
}
