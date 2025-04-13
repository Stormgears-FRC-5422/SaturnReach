package frc.robot.subsystems.drive;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.spark.SparkMax;
import frc.robot.Constants.Drive;
import frc.robot.subsystems.drive.config.SaturnXModuleConstants;
import frc.robot.subsystems.drive.config.SwerveModule;
import org.littletonrobotics.junction.Logger;

public class DiagnosticSwerve extends DrivetrainBase {
    public static final double m_maxMotorVoltage = Drive.maxMotorVoltage;
    public SwerveModule[] swerveModules;
    public SparkMax[] driveArray;
    public SparkMax[] steerArray;
    public CANcoder[] encoderArray;

    private final int FRONT_LEFT = 0;
    private final int FRONT_RIGHT = 1;
    private final int BACK_LEFT = 3;
    private final int BACK_RIGHT = 2;

    public DiagnosticSwerve(){
        // These are convenient lies - the units basically work
        double maxVelocityMetersPerSecond = m_maxMotorVoltage;
        double maxAngularVelocityRadiansPerSecond = m_maxMotorVoltage;
        setMaxVelocities(maxVelocityMetersPerSecond * Drive.driveSpeedScale,
            maxAngularVelocityRadiansPerSecond * Drive.driveSpeedScale);

        SaturnXModuleConstants c = new SaturnXModuleConstants();
        swerveModules = new SwerveModule[4];
        swerveModules[FRONT_LEFT] = new SwerveModule(c.flModuleConfig, c.flDrivePidConfig);
        swerveModules[FRONT_RIGHT] =new SwerveModule(c.frModuleConfig, c.frDrivePidConfig);
        swerveModules[BACK_RIGHT] =new SwerveModule(c.brModuleConfig, c.brDrivePidConfig);
        swerveModules[BACK_LEFT] =new SwerveModule(c.blModuleConfig, c.blDrivePidConfig);

        driveArray = new SparkMax[4];
        driveArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].driveMotor;
        driveArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].driveMotor;
        driveArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].driveMotor;
        driveArray[BACK_LEFT] = swerveModules[BACK_LEFT].driveMotor;

        steerArray = new SparkMax[4];
        steerArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].steerMotor;
        steerArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].steerMotor;
        steerArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].steerMotor;
        steerArray[BACK_LEFT] = swerveModules[BACK_LEFT].steerMotor;

        encoderArray = new CANcoder[4];
        encoderArray[FRONT_RIGHT] = swerveModules[FRONT_RIGHT].encoder;
        encoderArray[FRONT_LEFT] = swerveModules[FRONT_LEFT].encoder;
        encoderArray[BACK_RIGHT] = swerveModules[BACK_RIGHT].encoder;
        encoderArray[BACK_LEFT] = swerveModules[BACK_LEFT].encoder;
    }

    @Override
    public void periodic() {
        double driveSpeed = m_chassisSpeeds.vxMetersPerSecond;
        double steerSpeed = m_chassisSpeeds.omegaRadiansPerSecond;

        for (SparkMax m : driveArray) {
            m.set(driveSpeed);
        }

        for (SparkMax m : steerArray) {
            m.set(steerSpeed);
        }

        Logger.recordOutput("fl steer", steerArray[FRONT_LEFT].getEncoder().getPosition());
        Logger.recordOutput("fr steer", steerArray[FRONT_RIGHT].getEncoder().getPosition());
        Logger.recordOutput("bl steer", steerArray[BACK_LEFT].getEncoder().getPosition());
        Logger.recordOutput("br steer", steerArray[BACK_RIGHT].getEncoder().getPosition());

        Logger.recordOutput("fl encoder", steerArray[FRONT_LEFT].getEncoder().getPosition());
        Logger.recordOutput("fr encoder", steerArray[FRONT_RIGHT].getEncoder().getPosition());
        Logger.recordOutput("bl encoder", steerArray[BACK_LEFT].getEncoder().getPosition());
        Logger.recordOutput("br encoder", steerArray[BACK_RIGHT].getEncoder().getPosition());

        Logger.recordOutput("fl drive pos", steerArray[FRONT_LEFT].getEncoder().getPosition());
        Logger.recordOutput("fr drive pos", steerArray[FRONT_RIGHT].getEncoder().getPosition());
        Logger.recordOutput("bl drive pos", steerArray[BACK_LEFT].getEncoder().getPosition());
        Logger.recordOutput("br drive pos", steerArray[BACK_RIGHT].getEncoder().getPosition());

        Logger.recordOutput("fl drive vel", steerArray[FRONT_LEFT].getEncoder().getVelocity());
        Logger.recordOutput("fr drive vel", steerArray[FRONT_RIGHT].getEncoder().getVelocity());
        Logger.recordOutput("bl drive vel", steerArray[BACK_LEFT].getEncoder().getVelocity());
        Logger.recordOutput("br drive vel", steerArray[BACK_RIGHT].getEncoder().getVelocity());
    }
}
