package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.subsystems.BatteryMonitor.BatteryState;
import frc.robot.subsystems.Shooter.ShooterState;

public class RobotState {
    private static RobotState instance;
    private final StateSimMode stateSimMode;
    boolean upperSensorTriggered;
    boolean shooting;
    private BatteryState batteryState;
    private ShooterState shooterState;
    private StatePeriod statePeriod;

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
    }

    public static RobotState getInstance() {
        if (instance != null) return instance;

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

    public ShooterState getShooterState() {
        return shooterState;
    }

    public void setShooterState(ShooterState s) {
        shooterState = s;
    }

    public BatteryState getBatteryState() {
        return this.batteryState;
    }

    public void setBatteryState(BatteryState batteryState) {
        this.batteryState = batteryState;
    }

    public boolean getIsShooting() {
        return shooting;
    }

    public void setIsShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public enum StatePeriod {
        NONE, DISABLED, AUTONOMOUS, TELEOP, TEST
    }

    public enum StateSimMode {
        REAL, SIMULATION, AKIT_REPLAY, AKIT_SIM;
    }
}
