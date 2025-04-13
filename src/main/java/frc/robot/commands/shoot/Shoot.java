package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.utils.StormCommand;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;

public class Shoot extends StormCommand {
    private final Shooter s;
    private Timer timer;
    RobotState robotState;
    ShooterState shooterState;

    public Shoot(Shooter s) {
        this.s = s;
        robotState = RobotState.getInstance();
        timer = new Timer();

        addRequirements(s);
    }

    @Override
    public void initialize() {
        super.initialize();

        robotState.setIsShooting(true);

        if (s.isUpperSensorTriggered()) {
            console("start shooting");
            shooterState = Shooter.ShooterState.SPEAKER_SHOOTING;
            timer.restart();
        } else {
            console("start pickup");
            shooterState = Shooter.ShooterState.GROUND_PICKUP;
        }

        s.setShooterState(shooterState);
    }

    @Override
    public boolean isFinished() {
        if (shooterState == ShooterState.SPEAKER_SHOOTING) {
            return timer.get() > Constants.Shooter.shootTimerSec;
        } else {
            return s.isUpperSensorTriggered();
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (!interrupted && shooterState == ShooterState.GROUND_PICKUP) {
            console("staged for shooting");
            s.setShooterState(ShooterState.STAGED_FOR_SHOOTING);
        } else {
            console("idle");
            s.setShooterState(ShooterState.IDLE);
        }
        robotState.setIsShooting(false);
        super.end(interrupted);
    }
}



