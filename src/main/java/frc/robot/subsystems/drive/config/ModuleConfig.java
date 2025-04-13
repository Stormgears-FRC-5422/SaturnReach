package frc.robot.subsystems.drive.config;

public class ModuleConfig {
    int driveID = 0;
    int steerID = 0;
    int encoderID = 0;
    boolean driveInverted = false;
    boolean steerInverted = false;
    double wheelDiameter = 0;
    boolean driveCoast = true;
    boolean steerCoast = true;

    public ModuleConfig withDriveID(int newID)
    {
        driveID = newID;
        return this;
    }

    public ModuleConfig withSteerID(int newID)
    {
        steerID = newID;
        return this;
    }

    public ModuleConfig withEncoderID(int newID)
    {
        steerID = newID;
        return this;
    }

    public ModuleConfig withDriveInverted(boolean newInverted)
    {
        driveInverted = newInverted;
        return this;
    }

    public ModuleConfig withSteerInverted(boolean newInverted)
    {
        steerInverted = newInverted;
        return this;
    }

    public ModuleConfig withDriveCoast(boolean newCoast)
    {
        driveCoast = newCoast;
        return this;
    }

    public ModuleConfig withSteerCoast(boolean newCoast)
    {
        steerCoast = newCoast;
        return this;
    }

    public ModuleConfig withWheelDiameter(double newDiameter)
    {
        wheelDiameter = newDiameter;
        return this;
    }

    public static ModuleConfig from(ModuleConfig value)
    {
        return new ModuleConfig() {{
            driveID = value.driveID;
            steerID = value.steerID;
            encoderID = value.encoderID;
            driveInverted = value.driveInverted;
            steerInverted = value.steerInverted;
        }};
    }

    @Override
    public String toString()
    {
        String ss = "Module Config\n";
        ss += "    driveID: " + driveID + "\n";
        ss += "    steerID: " + steerID + "\n";
        ss += "    encoderID: " + encoderID + "\n";
        ss += "    driveInverted: " + driveInverted + "\n";
        ss += "    steerInverted: " + steerInverted + "\n";
        return ss;
    }


}
