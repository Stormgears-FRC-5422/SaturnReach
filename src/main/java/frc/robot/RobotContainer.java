// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.JoyStickDrive;
import frc.robot.commands.shoot.*;
import frc.robot.joysticks.CrescendoJoystick;
import frc.robot.subsystems.BatteryMonitor;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Lights;
import frc.robot.subsystems.drive.DrivetrainBase;
import frc.robot.subsystems.drive.DrivetrainFactory;
import frc.robot.subsystems.drive.IllegalDriveTypeException;
import frc.utils.joysticks.StormLogitechController;
import frc.robot.Constants.Toggles;

public class RobotContainer {
    private BatteryMonitor batteryMonitor;
    private DrivetrainBase drivetrain;
    private Lights lights;
    private Shooter shooter;

    // **********
    // Commands
    // **********
    private Shoot shoot;
    private Outtake outtake;

    // **********
    // Fields
    // **********
    final RobotState robotState;

    // **********
    // Control
    // **********
    CrescendoJoystick joystick;

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

        new Trigger(() -> joystick.shoot()).onTrue(shoot);
        new Trigger(() -> joystick.outtake()).onTrue(outtake);

        if (Toggles.useDrive) {
            JoyStickDrive driveWithJoystick = new JoyStickDrive(drivetrain, joystick);
            drivetrain.setDefaultCommand(driveWithJoystick);
        }

        if (Toggles.useShooter) {
            shoot = new Shoot(shooter);
            outtake = new Outtake(shooter);
        }

        console("[DONE] configureBindings");
    }

    public void console (String message){
        System.out.println("RobotContainer : " + message);
    }
}
