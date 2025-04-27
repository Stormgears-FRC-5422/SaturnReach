// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Toggles;
import frc.robot.commands.HoldSwerveModuleStates;
import frc.robot.commands.JoyStickDrive;
import frc.robot.commands.shoot.Outtake;
import frc.robot.commands.shoot.Shoot;
import frc.robot.elastic.SwerveModuleTuner;
import frc.robot.joysticks.CrescendoJoystick;
import frc.robot.subsystems.BatteryMonitor;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.drive.DrivetrainBase;
import frc.robot.subsystems.drive.DrivetrainFactory;
import frc.robot.subsystems.drive.IllegalDriveTypeException;
import frc.robot.subsystems.drive.config.SaturnXModuleConstants;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.MetersPerSecond;

public class RobotContainer {
    // **********
    // Control
    // **********
    CrescendoJoystick joystick;
    @SuppressWarnings("unused")
    private BatteryMonitor batteryMonitor;
    private DrivetrainBase drivetrain;
    private SwerveModuleTuner tuner;

    // **********
    // Commands
    // **********
    // (no pre-created commands)
    @SuppressWarnings("unused")
    private Lights lights;
    private Shooter shooter;

    public RobotContainer() {
        try {
            console("[Init] RobotContainer");

            console("Making joystick!");
            joystick = new CrescendoJoystick(Constants.ButtonBoard.driveJoystickPort);

            if (Toggles.useBatteryMonitor) {
                batteryMonitor = new BatteryMonitor();
            }

            if (Toggles.useDrive) {
                console("Create drive type " + Constants.Drive.driveType);
                drivetrain = DrivetrainFactory.getInstance(Constants.Drive.driveType, "");
            }

            if (Toggles.useShooter) {
                console("Create shooter");
                shooter = new Shooter(joystick);
            }

            if (Toggles.useLights) {
                lights = new Lights();
            }

            if (Toggles.usePidTuner) {
                tuner = new SwerveModuleTuner("PID Tuning", SaturnXModuleConstants.drivePidConfig, SaturnXModuleConstants.steerPidConfig);
            }

            configureBindings();
            console("[DONE] RobotContainer");
        } catch (IllegalDriveTypeException e) {
            console("Error: Invalid drive type specified: " + e.getMessage());
        } catch (RuntimeException e) {
            console("Error initializing RobotContainer: " + e.getMessage());
        }
    }

    private void configureBindings() {
        console("[Init] configureBindings");

        if (Toggles.useShooter) {
            new Trigger(() -> joystick.shoot()).whileTrue(new Shoot(shooter));
            new Trigger(() -> joystick.outtake()).whileTrue(new Outtake(shooter));
        }

        if (Toggles.useDrive) {
            JoyStickDrive driveWithJoystick = new JoyStickDrive(drivetrain, joystick);
            HoldSwerveModuleStates holdCommand = new HoldSwerveModuleStates(drivetrain, MetersPerSecond.of(0.5), Degrees.of(90));

            drivetrain.setDefaultCommand(driveWithJoystick);
            new Trigger(() -> joystick.holdSwerveState()).whileTrue(holdCommand);
        }

        console("[DONE] configureBindings");
    }

    public void periodic() {
        if (tuner != null) {
            tuner.periodic();
        }
    }

    private void console(String message) {
        System.out.println("RobotContainer : " + message);
    }
}
