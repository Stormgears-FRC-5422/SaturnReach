package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import frc.robot.Constants;
import frc.robot.Constants.SparkConstants;
import frc.robot.RobotState;
import frc.robot.joysticks.CrescendoJoystick;
import frc.utils.StormSubsystem;

import java.util.function.DoubleSupplier;

import static frc.robot.subsystems.Shooter.Direction.FORWARD;
import static frc.robot.subsystems.Shooter.Direction.REVERSE;

public class Shooter extends StormSubsystem {
    private final RobotState robotState;
    private final SparkMaxConfig upperLeaderConfig;
    private final SparkMaxConfig lowerConfig;
    private final SparkMax upperLeaderMotor;
    private final SparkMax lowerMotor;
    private final double upperLowerFreeSpeedRatio;
    double upperMotorSpeed = 0;
    double lowerMotorSpeed = 0;
    Boolean shooterStaged = false;
    ShooterState shooterState;
    DoubleSupplier slider;

    public Shooter(CrescendoJoystick joystick) {
        upperLowerFreeSpeedRatio = SparkConstants.FreeSpeedRPM / SparkConstants.Neo550FreeSpeedRPM;
        upperLeaderMotor = new SparkMax(Constants.Shooter.upperLeaderID, SparkLowLevel.MotorType.kBrushless);
        SparkMax upperFollowerMotor = new SparkMax(Constants.Shooter.upperFollowerID, SparkLowLevel.MotorType.kBrushless);
        lowerMotor = new SparkMax(Constants.Shooter.lowerID, SparkLowLevel.MotorType.kBrushless);

        upperLeaderConfig = new SparkMaxConfig();
        lowerConfig = new SparkMaxConfig();
        SparkMaxConfig globalConfig = new SparkMaxConfig();
        SparkMaxConfig upperFollowerConfig = new SparkMaxConfig();

        globalConfig.smartCurrentLimit(Constants.SparkConstants.CurrentLimit)
            .voltageCompensation(Constants.SparkConstants.NominalVoltage)
            .openLoopRampRate(Constants.Shooter.openLoopRampRate);

        upperLeaderConfig.apply(globalConfig)
            .inverted(Constants.Shooter.invertLeader);

        upperFollowerConfig.apply(globalConfig).follow(upperLeaderMotor, true);

        lowerConfig.apply(globalConfig)
            .inverted(Constants.Shooter.invertIntake);

        upperLeaderMotor.configure(upperLeaderConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);

        upperFollowerMotor.configure(upperFollowerConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters);

        slider = joystick::getSlider;
        robotState = RobotState.getInstance();
        setShooterState(ShooterState.IDLE);
    }

    @Override
    public void periodic() {
        double sliderScale = slider.getAsDouble();

        upperLeaderMotor.set(upperMotorSpeed * sliderScale);
        lowerMotor.set(lowerMotorSpeed * sliderScale);
        robotState.setUpperSensorTriggered(isUpperSensorTriggered());
    }

    public void setShooterState(ShooterState state) {
        this.shooterState = state;
        robotState.setShooterState(state);

        switch (state) {
            case IDLE -> {
                shooterStaged = false;
                setIdleModeAll(IdleMode.kBrake);
                setMotorSpeeds(FORWARD, 0, 0);
            }

            case GROUND_PICKUP -> {
                shooterStaged = false;
                setLimitSwitch(FORWARD, true);
                setIdleModeAll(IdleMode.kBrake);
                setMotorSpeeds(FORWARD,
                    Constants.Shooter.intakeLowerMotorSpeed,
                    Constants.Shooter.intakeUpperMotorSpeed);
            }

            case SPEAKER_SHOOTING -> {
                shooterStaged = false;
                setLimitSwitch(FORWARD, false);
                setMotorSpeeds(FORWARD,
                    Constants.Shooter.shootLowerMotorSpeed,
                    Constants.Shooter.shootUpperMotorSpeed);
            }

            case OUTTAKE -> {
                shooterStaged = false;
                setLimitSwitch(REVERSE, false);
                setIdleModeAll(IdleMode.kCoast);
                setMotorSpeeds(REVERSE,
                    Constants.Shooter.outtakeMotorSpeed,
                    Constants.Shooter.outtakeMotorSpeed);
            }

            case STAGED_FOR_SHOOTING -> {
                shooterStaged = true;
                setIdleModeAll(IdleMode.kBrake);
                setMotorSpeeds(FORWARD, 0, 0);
            }

            default -> System.out.println("invalid state");
        }
    }

    private void setIdleModeAll(IdleMode mode) {
        upperLeaderConfig.idleMode(mode);
        lowerConfig.idleMode(mode);

        upperLeaderMotor.configure(upperLeaderConfig, SparkBase.ResetMode.kNoResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters);

        lowerMotor.configure(lowerConfig, SparkBase.ResetMode.kNoResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters);
    }

    private void setLimitSwitch(Direction d, boolean enabled) {
        switch (d) {
            case FORWARD -> {
                lowerConfig.limitSwitch.forwardLimitSwitchEnabled(enabled);
            }
            case REVERSE -> {
                lowerConfig.limitSwitch.reverseLimitSwitchEnabled(enabled);
            }
        }

        lowerMotor.configure(lowerConfig, SparkBase.ResetMode.kNoResetSafeParameters,
            SparkBase.PersistMode.kNoPersistParameters);
    }

    public void setMotorSpeeds(Direction d, double lowerSpeed, double upperSpeed) {
        lowerMotorSpeed = lowerSpeed * (d == FORWARD ? 1 : -1);
        upperMotorSpeed = upperSpeed * (d == FORWARD ? 1 : -1);
    }

    public boolean isUpperSensorTriggered() {
        return lowerMotor.getForwardLimitSwitch().isPressed();
    }

    public enum ShooterState {
        IDLE,
        GROUND_PICKUP,
        SPEAKER_SHOOTING,
        STAGED_FOR_SHOOTING,
        OUTTAKE
    }

    public enum Direction {
        FORWARD,
        REVERSE
    }
}
