package frc.robot.subsystems.drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.drive.config.SaturnXModuleConstants;
import frc.robot.subsystems.drive.config.SwerveModule;
import frc.robot.subsystems.drive.config.SwerveModuleGroup;
import org.littletonrobotics.junction.Logger;

public class DiagnosticSwerve extends DrivetrainBase {
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

    public DiagnosticSwerve(SaturnXModuleConstants moduleConstants) {
        super();

        // These are convenient lies - the units basically work
        setMaxVelocities(m_maxMotorVoltage * Drive.driveSpeedScale,
            m_maxMotorVoltage * Drive.driveSpeedScale);

        moduleGroup = new SwerveModuleGroup(moduleConstants);
        driveMotors = moduleGroup.getDriveMotors();
        steerMotors = moduleGroup.getSteerMotors();
        encoders = moduleGroup.getEncoders();
        modules = moduleGroup.getModules();
    }

    @Override
    public void periodic() {
        double driveSpeed = m_chassisSpeeds.vxMetersPerSecond;
        double steerSpeed = m_chassisSpeeds.omegaRadiansPerSecond;

        for (SparkMax m : driveMotors) {
            m.set(driveSpeed);
        }

        for (SparkMax m : steerMotors) {
            m.set(steerSpeed);
        }

        Logger.recordOutput("fl steer", steerMotors[FRONT_LEFT].getEncoder().getPosition());
        Logger.recordOutput("fr steer", steerMotors[FRONT_RIGHT].getEncoder().getPosition());
        Logger.recordOutput("bl steer", steerMotors[BACK_LEFT].getEncoder().getPosition());
        Logger.recordOutput("br steer", steerMotors[BACK_RIGHT].getEncoder().getPosition());

        Logger.recordOutput("fl encoder", encoders[FRONT_LEFT].getPosition().getValue());
        Logger.recordOutput("fr encoder", encoders[FRONT_RIGHT].getPosition().getValue());
        Logger.recordOutput("bl encoder", encoders[BACK_LEFT].getPosition().getValue());
        Logger.recordOutput("br encoder", encoders[BACK_RIGHT].getPosition().getValue());

        Logger.recordOutput("fl drive pos", driveMotors[FRONT_LEFT].getEncoder().getPosition());
        Logger.recordOutput("fr drive pos", driveMotors[FRONT_RIGHT].getEncoder().getPosition());
        Logger.recordOutput("bl drive pos", driveMotors[BACK_LEFT].getEncoder().getPosition());
        Logger.recordOutput("br drive pos", driveMotors[BACK_RIGHT].getEncoder().getPosition());

        Logger.recordOutput("fl drive vel", driveMotors[FRONT_LEFT].getEncoder().getVelocity());
        Logger.recordOutput("fr drive vel", driveMotors[FRONT_RIGHT].getEncoder().getVelocity());
        Logger.recordOutput("bl drive vel", driveMotors[BACK_LEFT].getEncoder().getVelocity());
        Logger.recordOutput("br drive vel", driveMotors[BACK_RIGHT].getEncoder().getVelocity());
    }
}
