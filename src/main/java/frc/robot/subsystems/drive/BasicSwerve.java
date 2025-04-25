package frc.robot.subsystems.drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.drive.config.SaturnXModuleConstants;
import frc.robot.subsystems.drive.config.SwerveModule;
import frc.robot.subsystems.drive.config.SwerveModuleGroup;

public class BasicSwerve extends DrivetrainBase {
    final double m_maxMotorVoltage = Drive.maxMotorVoltage;

    final SwerveModuleGroup moduleGroup;
    final SwerveModule[] modules;

    final int FRONT_LEFT = SwerveModuleGroup.FRONT_LEFT;
    final int FRONT_RIGHT = SwerveModuleGroup.FRONT_RIGHT;
    final int BACK_RIGHT = SwerveModuleGroup.BACK_RIGHT;
    final int BACK_LEFT = SwerveModuleGroup.BACK_LEFT;

//    // Creating my kinematics object using the module locations
//    SwerveDriveKinematics m_kinematics;

    public BasicSwerve(SaturnXModuleConstants moduleConstants) {
        super();

        // These are convenient lies - the units basically work
        setMaxVelocities(m_maxMotorVoltage * Drive.driveSpeedScale,
                m_maxMotorVoltage * Drive.driveSpeedScale);

        moduleGroup = new SwerveModuleGroup(moduleConstants);
        modules = moduleGroup.getModules();

        // Create this list explicitly so we can control the order and keep FRONT_LEFT,
        // etc. honest.
        Translation2d[] moduleTranslations = new Translation2d[4];
        moduleTranslations[FRONT_LEFT] = moduleConstants.flModuleConfig.offset;
        moduleTranslations[FRONT_RIGHT] = moduleConstants.frModuleConfig.offset;
        moduleTranslations[BACK_RIGHT] = moduleConstants.brModuleConfig.offset;
        moduleTranslations[BACK_LEFT] = moduleConstants.blModuleConfig.offset;

        SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(moduleTranslations);
    }


    @Override
    public void periodic() {
        console("driving with values "+ m_chassisSpeeds, 25);
        double steerpos = Math.atan2(m_chassisSpeeds.vxMetersPerSecond, m_chassisSpeeds.vyMetersPerSecond);

        for (SwerveModule m : modules) {
            double velocity = Math.hypot(m_chassisSpeeds.vxMetersPerSecond, m_chassisSpeeds.vyMetersPerSecond);
            m.setDriveVelocity(velocity);
            if (velocity > 0.05) {
                m.setSteerAngle(new Rotation2d(steerpos));
            }
            m.telemetry();
       }
    }
}
