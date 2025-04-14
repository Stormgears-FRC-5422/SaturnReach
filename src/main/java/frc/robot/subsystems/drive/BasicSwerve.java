package frc.robot.subsystems.drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.drive.config.SaturnXModuleConstants;
import frc.robot.subsystems.drive.config.SwerveModule;
import frc.robot.subsystems.drive.config.SwerveModuleGroup;

public class BasicSwerve extends DrivetrainBase {
    final double m_maxMotorVoltage = Drive.maxMotorVoltage;

    final SwerveModuleGroup moduleGroup;
    final SwerveModule[] modules;
    final SparkMax[] driveMotors;
    final SparkMax[] steerMotors;
    final CANcoder[] encoders;

    final int FRONT_LEFT = SwerveModuleGroup.FRONT_LEFT;
    final int FRONT_RIGHT = SwerveModuleGroup.FRONT_RIGHT;
    final int BACK_RIGHT = SwerveModuleGroup.BACK_RIGHT;
    final int BACK_LEFT = SwerveModuleGroup.BACK_LEFT;

    // Creating my kinematics object using the module locations
    SwerveDriveKinematics m_kinematics;

    public BasicSwerve(SaturnXModuleConstants moduleConstants) {
        super();

        // These are convenient lies - the units basically work
        setMaxVelocities(m_maxMotorVoltage * Drive.driveSpeedScale,
            m_maxMotorVoltage * Drive.driveSpeedScale);

        moduleGroup = new SwerveModuleGroup(moduleConstants);
        driveMotors = moduleGroup.getDriveMotors();
        steerMotors = moduleGroup.getSteerMotors();
        encoders = moduleGroup.getEncoders();
        modules = moduleGroup.getModules();

        // Create this list explicitly so we can control the order and keep FRONT_LEFT, etc. honest.
        Translation2d[] moduleTranslations = new Translation2d[4];
        moduleTranslations[FRONT_LEFT] = modules[FRONT_LEFT].moduleConfig.offset;
        moduleTranslations[FRONT_RIGHT] = modules[FRONT_RIGHT].moduleConfig.offset;
        moduleTranslations[BACK_RIGHT] = modules[BACK_RIGHT].moduleConfig.offset;
        moduleTranslations[BACK_LEFT] = modules[BACK_LEFT].moduleConfig.offset;

        SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(moduleTranslations);
    }

    @Override
    public void periodic() {
        double driveSpeed = m_chassisSpeeds.vxMetersPerSecond;
        double steerSpeed = m_chassisSpeeds.omegaRadiansPerSecond;

        // TODO - as is, this won't quite work
        // the existing SwerveModule class doesn't expose these methods, but
        // I like the idea of doing that.
        // If we don't change the SwerveModule class then we have to use driveMotors & steerMotors
        // from above
//        for (SwerveModule module : swerveModules) {
//            module.setDriveSpeed(driveSpeed);
//            module.setSteerSpeed(steerSpeed);
//        }
    }
}
