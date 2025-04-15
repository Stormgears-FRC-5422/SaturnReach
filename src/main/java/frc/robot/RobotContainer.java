// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Toggles;
import frc.robot.commands.JoyStickDrive;
import frc.robot.commands.shoot.Outtake;
import frc.robot.commands.shoot.Shoot;
import frc.robot.joysticks.CrescendoJoystick;
import frc.robot.subsystems.BatteryMonitor;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.drive.DrivetrainBase;
import frc.robot.subsystems.drive.DrivetrainFactory;
import frc.robot.subsystems.drive.IllegalDriveTypeException;

public class RobotContainer {
    // **********
    // Fields
    // **********
    final RobotState robotState;

    // **********
    // Control
    // **********
    CrescendoJoystick joystick;
    private BatteryMonitor batteryMonitor;
    private DrivetrainBase drivetrain;

    // **********
    // Commands
    // **********
    // (no pre-created commands)
    private Lights lights;
    private Shooter shooter;

    public RobotContainer() throws IllegalDriveTypeException {
        console("[Init] RobotContainer");
        robotState = RobotState.getInstance();

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

        configureBindings();
        console("[DONE] RobotContainer");
    }

    private void configureBindings() {
        console("[Init] configureBindings");

        if (Toggles.useShooter) {
            new Trigger(() -> joystick.shoot()).whileTrue(new Shoot(shooter));
            new Trigger(() -> joystick.outtake()).whileTrue(new Outtake(shooter));
        }

        if (Toggles.useDrive) {
            JoyStickDrive driveWithJoystick = new JoyStickDrive(drivetrain, joystick);
            drivetrain.setDefaultCommand(driveWithJoystick);
        }

        console("[DONE] configureBindings");
    }

    public void console(String message) {
        System.out.println("RobotContainer : " + message);
    }
}
