package frc.robot.subsystems.drive.config;

import frc.robot.Constants;

public class ModuleConstants {
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

    public ModuleConstants() {
        // Save some space with these convenience variables
        double x = Constants.Drive.drivetrainTrackwidthMeters / 2.0;
        double y = Constants.Drive.drivetrainWheelbaseMeters / 2.0;

        flModuleConfig = getDefaultModuleConfig()
            .withDriveID(Constants.Drive.frontLeftDriveID)
            .withSteerID(Constants.Drive.frontLeftSteerID)
            .withEncoderID(Constants.Drive.frontLeftEncoderID)
            .withWheelDiameter(Constants.Drive.frontLeftDiameter)
            .withDriveInverted(!Constants.Drive.invertRight)
            .withXpos(x)
            .withYpos(y);

        frModuleConfig = getDefaultModuleConfig()
            .withDriveID(Constants.Drive.frontRightDriveID)
            .withSteerID(Constants.Drive.frontRightSteerID)
            .withEncoderID(Constants.Drive.frontRightEncoderID)
            .withWheelDiameter(Constants.Drive.frontRightDiameter)
            .withDriveInverted(Constants.Drive.invertRight)
            .withXpos(x)
            .withYpos(-y);

        blModuleConfig =  getDefaultModuleConfig()
            .withDriveID(Constants.Drive.backLeftDriveID)
            .withSteerID(Constants.Drive.backLeftSteerID)
            .withEncoderID(Constants.Drive.backLeftEncoderID)
            .withWheelDiameter(Constants.Drive.backLeftDiameter)
            .withDriveInverted( ! Constants.Drive.invertRight)
            .withXpos(x)
            .withYpos(-y);

        brModuleConfig = getDefaultModuleConfig()
            .withDriveID(Constants.Drive.backRightDriveID)
            .withSteerID(Constants.Drive.backRightSteerID)
            .withEncoderID(Constants.Drive.backRightEncoderID)
            .withWheelDiameter(Constants.Drive.backRightDiameter)
            .withDriveInverted( ! Constants.Drive.invertRight)
            .withXpos(-x)
            .withYpos(-y);

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
            .withKP(Constants.Drive.driveKP)
            .withKI(Constants.Drive.driveKI)
            .withKD(Constants.Drive.driveKD);
    }

    PidConfig getDefaultSteerPidConfig() {
        return getDefaultPidConfig()
            .withKP(Constants.Drive.steerKP)
            .withKI(Constants.Drive.steerKI)
            .withKD(Constants.Drive.steerKD);
    }

    PidConfig getDefaultPidConfig() {
        return new PidConfig();
    }

    ModuleConfig getDefaultModuleConfig() {
        return new ModuleConfig()
            .withDriveCoast(Constants.Drive.driveCoastMode)
            .withSteerCoast(Constants.Drive.steerCoastMode)
            .withSteerInverted(Constants.Drive.invertSteer);
    }

}
