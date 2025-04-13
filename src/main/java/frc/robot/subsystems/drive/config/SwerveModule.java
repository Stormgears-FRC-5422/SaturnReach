package frc.robot.subsystems.drive.config;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.ctre.phoenix6.hardware.CANcoder;
import frc.robot.Constants;

public class SwerveModule {
    private final SparkMaxConfig driveConfig;
    private final SparkMaxConfig steerConfig;
    public final SparkMax driveMotor;
    public final SparkMax steerMotor;
    public final CANcoder encoder;

    public SwerveModule(ModuleConfig moduleConfig, PidConfig pidConfig) {
        driveConfig = new SparkMaxConfig();
        steerConfig = new SparkMaxConfig();

        driveMotor = new SparkMax(moduleConfig.driveID, SparkLowLevel.MotorType.kBrushless);
        steerMotor = new SparkMax(moduleConfig.steerID, SparkLowLevel.MotorType.kBrushless);
        encoder = new CANcoder(moduleConfig.encoderID);

        SparkMaxConfig globalConfig = new SparkMaxConfig();

        globalConfig.smartCurrentLimit(Constants.SparkConstants.CurrentLimit)
            .voltageCompensation(Constants.SparkConstants.NominalVoltage);

        driveConfig.apply(globalConfig)
            .openLoopRampRate(Constants.Drive.driveOpenLoopRampRate)
            .inverted(moduleConfig.driveInverted);

        steerConfig.apply(globalConfig)
            .openLoopRampRate(Constants.Drive.steerOpenLoopRampRate)
            .inverted(moduleConfig.steerInverted);

        driveMotor.configure(driveConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);

        steerMotor.configure(steerConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);
    }
}
