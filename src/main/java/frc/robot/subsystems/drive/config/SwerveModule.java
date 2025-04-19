package frc.robot.subsystems.drive.config;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.Constants;
import com.revrobotics.spark.SparkBase.ControlType;


public class SwerveModule {
    public final SparkMax driveMotor;
    public final SparkMax steerMotor;
    public final CANcoder encoder;
    public final ModuleConfig moduleConfig;
    public final PidConfig pidConfig;
    private final SparkMaxConfig driveConfig;
    private final SparkMaxConfig steerConfig;
    private SparkClosedLoopController steerController;
    private SparkClosedLoopController driveController;

    public SwerveModule(ModuleConfig moduleConfig, PidConfig pidConfig) {
        this.moduleConfig = moduleConfig;
        this.pidConfig = pidConfig;

        driveConfig = new SparkMaxConfig();
        steerConfig = new SparkMaxConfig();

        driveMotor = new SparkMax(moduleConfig.driveID, SparkLowLevel.MotorType.kBrushless);
        steerMotor = new SparkMax(moduleConfig.steerID, SparkLowLevel.MotorType.kBrushless);
        encoder = new CANcoder(moduleConfig.encoderID);
        steerController = steerMotor.getClosedLoopController();
        driveController = driveMotor.getClosedLoopController();

        SparkMaxConfig globalConfig = new SparkMaxConfig();

        globalConfig.smartCurrentLimit(Constants.SparkConstants.CurrentLimit)
                .voltageCompensation(Constants.SparkConstants.NominalVoltage);

        driveConfig.apply(globalConfig)
                .openLoopRampRate(Constants.Drive.driveOpenLoopRampRate)
                .inverted(moduleConfig.driveInverted);

        steerConfig.apply(globalConfig)
                .openLoopRampRate(Constants.Drive.steerOpenLoopRampRate)
                .inverted(moduleConfig.steerInverted);

        driveConfig.closedLoop
                .p(0.5)
                .i(0)
                .d(0)
                .outputRange(-1, 1)
                .p(0).i(0).d(0);
        steerConfig.closedLoop
                .p(0)
                .i(0)
                .d(0)
                .outputRange(-1, 1)
                .p(0.004).i(0).d(0);

        driveMotor.configure(driveConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);

        steerMotor.configure(steerConfig,
                SparkBase.ResetMode.kResetSafeParameters,
                SparkBase.PersistMode.kPersistParameters);
    }

    public void setDriveVelocity(double velocity) {
        driveController.setReference(velocity, ControlType.kVelocity);
        System.out.println("driving with values "+ velocity);

    }

    public void setSteerAngle(Rotation2d rotation2d) {
        steerController.setReference(rotation2d.getRotations(), ControlType.kPosition);
        System.out.println("driving with values "+ rotation2d);

    }
}
