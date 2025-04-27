package frc.robot.elastic;

import frc.robot.Constants.Drive;

public class DriveOptions extends OptionsController {

    public final Option<Boolean> allowRotation;
    public final Option<Boolean> cosineOptimize;
    public final Option<Boolean> angleOptimize;

    public DriveOptions() {
        super("Drive Options");
        allowRotation = createBooleanOption("Allow Rotation", Drive.allowRotation);
        cosineOptimize = createBooleanOption("Cosine Optimize", Drive.cosineOptimize);
        angleOptimize = createBooleanOption("Angle Optimize", Drive.angleOptimize);
    }
}
