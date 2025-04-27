package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.subsystems.BatteryMonitor.BatteryState;
import frc.robot.subsystems.Shooter.ShooterState;

public class RobotState {

    private static RobotState instance;
    private final StateSimMode stateSimMode;
    private StatePeriod statePeriod;
    private boolean upperSensorTriggered;
    private BatteryState batteryState;
    private ShooterState shooterState;

    public enum StateSimMode {
        REAL,
        SIMULATION,
        AKIT_REPLAY,
        AKIT_SIM
    }

    public enum StatePeriod {
        DISABLED,
        AUTONOMOUS,
        TELEOP,
        TEST
    }

    private RobotState() {
        if (RobotBase.isReal()) {
            stateSimMode = StateSimMode.REAL;
        } else if (Constants.Toggles.useAdvantageKit) {
            if (Constants.Akit.doReplay) {
                stateSimMode = StateSimMode.AKIT_REPLAY;
            } else {
                stateSimMode = StateSimMode.AKIT_SIM;
            }
        } else { // basic simulation
            stateSimMode = StateSimMode.SIMULATION;
        }

        batteryState = BatteryState.GOOD;
        shooterState = ShooterState.IDLE;
    }

    public static RobotState getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new RobotState();
        return instance;
    }

    public StateSimMode getSimMode() {
        return stateSimMode;
    }

    public boolean getUpperSensorTriggered() {
        return upperSensorTriggered;
    }

    public void setUpperSensorTriggered(boolean triggered) {
        upperSensorTriggered = triggered;
    }

    public StatePeriod getPeriod() {
        return statePeriod;
    }

    public void setPeriod(StatePeriod period) {
        statePeriod = period;
    }

    public BatteryState getBatteryState() {
        return batteryState;
    }

    public void setBatteryState(BatteryState state) {
        batteryState = state;
    }

    public void setShooterState(ShooterState state) {
        shooterState = state;
    }

    public boolean getIsShooting() {
        return shooterState == ShooterState.SPEAKER_SHOOTING;
    }

    public boolean getIsIntaking() {
        return shooterState == ShooterState.GROUND_PICKUP;
    }

    public boolean getIsOuttaking() {
        return shooterState == ShooterState.OUTTAKE;
    }
}
