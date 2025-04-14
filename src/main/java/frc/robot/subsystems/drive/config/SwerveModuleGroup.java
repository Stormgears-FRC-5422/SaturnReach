package frc.robot.subsystems.drive.config;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;

public class SwerveModuleGroup {
    public static final int FRONT_LEFT = 0;
    public static final int FRONT_RIGHT = 1;
    public static final int BACK_RIGHT = 2;
    public static final int BACK_LEFT = 3;
    public static final int NUM_MODULES = 4;

    final SwerveModule[] swerveModules;
    final SparkMax[] driveArray;
    final SparkMax[] steerArray;
    final CANcoder[] encoderArray;

    public SwerveModuleGroup(SaturnXModuleConstants c) {
        swerveModules = new SwerveModule[NUM_MODULES];
        swerveModules[FRONT_LEFT] = new SwerveModule(c.flModuleConfig, c.flDrivePidConfig);
        swerveModules[FRONT_RIGHT] =new SwerveModule(c.frModuleConfig, c.frDrivePidConfig);
        swerveModules[BACK_RIGHT] =new SwerveModule(c.brModuleConfig, c.brDrivePidConfig);
        swerveModules[BACK_LEFT] =new SwerveModule(c.blModuleConfig, c.blDrivePidConfig);

        driveArray = new SparkMax[NUM_MODULES];
        driveArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].driveMotor;
        driveArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].driveMotor;
        driveArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].driveMotor;
        driveArray[BACK_LEFT] = swerveModules[BACK_LEFT].driveMotor;

        steerArray = new SparkMax[NUM_MODULES];
        steerArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].steerMotor;
        steerArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].steerMotor;
        steerArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].steerMotor;
        steerArray[BACK_LEFT] = swerveModules[BACK_LEFT].steerMotor;

        encoderArray = new CANcoder[NUM_MODULES];
        encoderArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].encoder;
        encoderArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].encoder;
        encoderArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].encoder;
        encoderArray[BACK_LEFT] = swerveModules[BACK_LEFT].encoder;
    }

    public SparkMax[] getDriveMotors() { return driveArray; }
    public SparkMax[] getSteerMotors() { return steerArray; }
    public CANcoder[] getEncoders() { return encoderArray; }
    public SwerveModule[] getModules() { return swerveModules; }
}
