//package frc.robot.subsystems.drive;
//
//import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
//import frc.robot.Constants.Drive;
//
//public class BasicSwerve extends DrivetrainBase {
//
//
//    Translation2d m_frontLeftLocation = new Translation2d(0.381, 0.381);
//    Translation2d m_frontRightLocation = new Translation2d(0.381, -0.381);
//    Translation2d m_backLeftLocation = new Translation2d(-0.381, 0.381);
//    Translation2d m_backRightLocation = new Translation2d(-0.381, -0.381);
//// Creating my kinematics object using the module locations
//SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
//  m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
//);
//    public BasicSwerve() {
//        super();
//        setMaxVelocities(Drive.maxDriveVelocity, Drive.maxAngularVelocity);
//    }
//
//    @Override
//    public void periodic() {
//        double driveSpeed = m_chassisSpeeds.vxMetersPerSecond;
//        double steerSpeed = m_chassisSpeeds.omegaRadiansPerSecond;
//
//        for (SwerveModule module : swerveModules) {
//            module.setDriveSpeed(driveSpeed);
//            module.setSteerSpeed(steerSpeed);
//        }
//    }
//
//}
