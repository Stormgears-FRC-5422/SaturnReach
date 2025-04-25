package frc.robot.subsystems.drive.config;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants.Drive;

public class SaturnXModuleConstants {
    public ModuleConfig flModuleConfig;
    public ModuleConfig frModuleConfig;
    public ModuleConfig blModuleConfig;
    public ModuleConfig brModuleConfig;

    public PidConfig flDrivePidConfig;
    public PidConfig frDrivePidConfig;
    public PidConfig blDrivePidConfig;
    public PidConfig brDrivePidConfig;

    public PidConfig flSteerPidConfig;
    public PidConfig frSteerPidConfig;
    public PidConfig blSteerPidConfig;
    public PidConfig brSteerPidConfig;

    public SaturnXModuleConstants() {
        // Save some space with these convenience variables
        double dx = Drive.drivetrainTrackwidthMeters / 2.0;
        double dy = Drive.drivetrainWheelbaseMeters / 2.0;

        flModuleConfig = getDefaultModuleConfig()
            .withName("Front Left")
            .withDriveID(Drive.frontLeftDriveID)
            .withSteerID(Drive.frontLeftSteerID)
            .withEncoderID(Drive.frontLeftEncoderID)
            .withWheelDiameter(Drive.frontLeftDiameter)
            .withDriveInverted(!Drive.invertRight)
            .withEncoderOffset(Drive.frontLeftEncoderOffset)
            .withOffset(new Translation2d(dx,dy));

        frModuleConfig = getDefaultModuleConfig()
            .withName("Front Right")
            .withDriveID(Drive.frontRightDriveID)
            .withSteerID(Drive.frontRightSteerID)
            .withEncoderID(Drive.frontRightEncoderID)
            .withWheelDiameter(Drive.frontRightDiameter)
            .withDriveInverted(Drive.invertRight)
            .withEncoderOffset(Drive.frontRightEncoderOffset)
            .withOffset(new Translation2d(dx,-dy));

        blModuleConfig =  getDefaultModuleConfig()
            .withName("Back Left")
            .withDriveID(Drive.backLeftDriveID)
            .withSteerID(Drive.backLeftSteerID)
            .withEncoderID(Drive.backLeftEncoderID)
            .withWheelDiameter(Drive.backLeftDiameter)
            .withDriveInverted( ! Drive.invertRight)
            .withEncoderOffset(Drive.backLeftEncoderOffset)
            .withOffset(new Translation2d(-dx,dy));

        brModuleConfig = getDefaultModuleConfig()
            .withName("Back Right")
            .withDriveID(Drive.backRightDriveID)
            .withSteerID(Drive.backRightSteerID)
            .withEncoderID(Drive.backRightEncoderID)
            .withWheelDiameter(Drive.backRightDiameter)
            .withDriveInverted( ! Drive.invertRight)
            .withEncoderOffset(Drive.backRightEncoderOffset)
            .withOffset(new Translation2d(-dx,-dy));

        flDrivePidConfig = getDefaultDrivePidConfig();
        frDrivePidConfig = getDefaultDrivePidConfig();
        blDrivePidConfig = getDefaultDrivePidConfig();
        brDrivePidConfig = getDefaultDrivePidConfig();

        flSteerPidConfig = getDefaultSteerPidConfig();
        frSteerPidConfig = getDefaultSteerPidConfig();
        blSteerPidConfig = getDefaultSteerPidConfig();
        brSteerPidConfig = getDefaultSteerPidConfig();
    }

    PidConfig getDefaultDrivePidConfig() {
        return getDefaultPidConfig()
            .withKP(Drive.driveKP)
            .withKI(Drive.driveKI)
            .withKD(Drive.driveKD)
            .withKMinMax(Drive.driveKMin, Drive.driveKMax);
    }

    PidConfig getDefaultSteerPidConfig() {
        return getDefaultPidConfig()
            .withKP(Drive.steerKP)
            .withKI(Drive.steerKI)
            .withKD(Drive.steerKD)
            .withKMinMax(Drive.steerKMin, Drive.steerKMax);
    }

    PidConfig getDefaultPidConfig() {
        return new PidConfig();
    }

    ModuleConfig getDefaultModuleConfig() {
        return new ModuleConfig()
            .withDriveCoast(Drive.driveCoastMode)
            .withSteerCoast(Drive.steerCoastMode)
            .withSteerInverted(Drive.invertSteer)
            .withDriveRatio(Drive.driveRatio)
            .withSteerRatio(Drive.steerRatio)
            .withInvertEncoder(Drive.invertEncoder);
    }

}
