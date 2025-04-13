package frc.robot.commands.shoot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;
import frc.utils.StormCommand;

public class Outtake extends StormCommand {
    private final Shooter s;

    public Outtake(Shooter s) {
        this.s = s;
        addRequirements(s);
    }

    @Override
    public void initialize() {
        super.initialize();
        s.setShooterState(Shooter.ShooterState.OUTTAKE);
    }

    @Override
    public void execute() {
        super.execute();
    }

    @Override
    public void end(boolean interrupted) {
        s.setShooterState(Shooter.ShooterState.IDLE);
        super.end(interrupted);
    }
}
