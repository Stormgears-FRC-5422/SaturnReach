package frc.robot.subsystems.drive;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotState;
import frc.robot.elastic.options.DriveOptions;
import frc.robot.subsystems.drive.config.SwerveModuleGroup;
import frc.robot.subsystems.drive.config.SwerveModule;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;

public class BasicSwerve extends DrivetrainBase {

    final SwerveModuleGroup moduleGroup;
    final SwerveModule[] modules;
    final SwerveDriveKinematics kinematics;
    private final DriveOptions options;

    public BasicSwerve() {
        super();

        options = RobotState.getInstance().getDriveOptions();

        moduleGroup = new SwerveModuleGroup();
        setMaxVelocities(moduleGroup.getMaxLinearVelocity().in(MetersPerSecond),
                moduleGroup.getMaxAngularVelocity().in(RadiansPerSecond));

        kinematics = new SwerveDriveKinematics(moduleGroup.getModuleTranslations());
        modules = moduleGroup.getModules();

        SmartDashboard.putData("Swerve Drive", builder -> {
            builder.setSmartDashboardType("SwerveDrive");
            builder.addDoubleProperty("Robot Angle", () -> 45.0, null);
            for (SwerveModule m : modules) {
                builder.addDoubleProperty(m.name + " Angle", m::getSteerAngle, null);
                builder.addDoubleProperty(m.name + " Velocity", m::getDriveVelocity, null);
            }
        });
    }

    @Override
    public void periodic() {
        super.periodic();
        options.periodic();
        moduleGroup.periodic();

        if (options.allowRotation.get()) {
            SwerveModuleState[] targetStates = kinematics.toSwerveModuleStates(m_chassisSpeeds);
            setModuleStates(targetStates);
        } else {
            driveWithoutRotation();
        }
    }

    public void setModuleStates(SwerveModuleState[] states) {
        moduleGroup.setSwerveModuleStates(states);
    }

    private void driveWithoutRotation() {
        LinearVelocity speed = MetersPerSecond.of(
                Math.hypot(m_chassisSpeeds.vxMetersPerSecond, m_chassisSpeeds.vyMetersPerSecond));
        Angle angle = Radians.of(
                Math.atan2(m_chassisSpeeds.vyMetersPerSecond, m_chassisSpeeds.vxMetersPerSecond));

        for (SwerveModule m : modules) {
            m.setDriveVelocity(speed);
            m.setSteerAngle(angle);
        }
    }

    public void stop() {
        moduleGroup.stop();
        this.drive(new ChassisSpeeds(), false);
    }
}
