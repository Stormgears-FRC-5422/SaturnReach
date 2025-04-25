package frc.robot.subsystems.drive.config;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.Constants;
import com.revrobotics.spark.SparkBase.ControlType;
import org.littletonrobotics.junction.Logger;

public class SwerveModule {
    public final CANcoder steerCANCoder;
    public final SparkMax driveMotor;
    public final SparkMax steerMotor;
    public final RelativeEncoder driveEncoder;
    public final RelativeEncoder steerEncoder;
    public final double steerOffset;
    private final SparkMaxConfig driveConfig;
    private final SparkMaxConfig steerConfig;
    private SparkClosedLoopController steerController;
    private SparkClosedLoopController driveController;
    private String name;
    
    private final double ROTATIONS_TO_DEGREES = 360.0;
    private final double INCHES_TO_METERS = 2.54 / 100.0;
    private final double DIAMETER_TO_ROTATIONS = 1.0/Math.PI;
    private final double MINUTES_TO_SECONDS = 60.0;

    public SwerveModule(ModuleConfig moduleConfig, PidConfig dPid, PidConfig sPid) {
        name = moduleConfig.name;
        steerOffset = moduleConfig.encoderOffset;

        steerCANCoder = new CANcoder(moduleConfig.encoderID);
        driveConfig = new SparkMaxConfig();
        steerConfig = new SparkMaxConfig();

        driveMotor = new SparkMax(moduleConfig.driveID, SparkLowLevel.MotorType.kBrushless);
        steerMotor = new SparkMax(moduleConfig.steerID, SparkLowLevel.MotorType.kBrushless);

        SparkMaxConfig globalConfig = new SparkMaxConfig();

        globalConfig.smartCurrentLimit(Constants.SparkConstants.CurrentLimit)
                .voltageCompensation(Constants.SparkConstants.NominalVoltage);

        driveConfig.apply(globalConfig)
                .openLoopRampRate(Constants.Drive.driveOpenLoopRampRate)
                .inverted(moduleConfig.driveInverted);

        EncoderConfig driveEncoderConfig = new EncoderConfig()
            .velocityConversionFactor(
                moduleConfig.wheelDiameter *
                DIAMETER_TO_ROTATIONS *
                INCHES_TO_METERS *
                MINUTES_TO_SECONDS *
                1.0 / moduleConfig.driveRatio);
        driveConfig.apply(driveEncoderConfig);

        driveConfig.closedLoop
                .p(dPid.kP)
                .i(dPid.kI)
                .d(dPid.kD)
                .outputRange(dPid.kMin, dPid.kMax);

        steerConfig.apply(globalConfig)
            .openLoopRampRate(Constants.Drive.steerOpenLoopRampRate)
            .inverted(moduleConfig.steerInverted);

        EncoderConfig steerEncoderConfig = new EncoderConfig()
            .positionConversionFactor( ROTATIONS_TO_DEGREES / moduleConfig.steerRatio );
        steerConfig.apply(steerEncoderConfig);

        steerConfig.closedLoop
                .p(sPid.kP)
                .i(sPid.kI)
                .d(sPid.kD)
                .outputRange(sPid.kMin, sPid.kMax);

        driveMotor.configure(driveConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);

        steerMotor.configure(steerConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);

        driveController = driveMotor.getClosedLoopController();
        driveEncoder = driveMotor.getEncoder();

        steerController = steerMotor.getClosedLoopController();
        steerEncoder = steerMotor.getEncoder();
    }

    public void telemetry() {
        Logger.recordOutput(name + " dr vel", driveMotor.getEncoder().getVelocity());
        Logger.recordOutput(name + " dr pos", driveMotor.getEncoder().getPosition());
        Logger.recordOutput(name + " st vel", steerMotor.getEncoder().getVelocity());
        Logger.recordOutput(name + " dr pos", steerMotor.getEncoder().getPosition());
    }

    public void setDriveVelocity(double velocity) {
        driveController.setReference(velocity, ControlType.kVelocity);
        System.out.println("driving with values "+ velocity);
    }

    public void setSteerAngle(Rotation2d rotation2d) {
        steerController.setReference(rotation2d.getDegrees(), ControlType.kPosition);
        System.out.println("driving with values "+ rotation2d);
    }
}
