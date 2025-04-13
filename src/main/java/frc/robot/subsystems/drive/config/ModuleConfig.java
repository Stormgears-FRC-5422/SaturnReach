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
    double xpos = 0; 
    double ypos = 0;

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

    public ModuleConfig withXpos(double newXpos){
        xpos = newXpos;
        return this;
    }

    public ModuleConfig withYpos(double newYpos){
        ypos = newYpos;
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
            wheelDiameter = value.wheelDiameter;
            driveCoast = value.driveCoast;
            steerCoast = value.steerCoast;
            xpos = value.xpos;
            ypos = value.ypos;
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
        ss += "    wheelDiameter: " + wheelDiameter + "\n";
        ss += "    driveCoast: " + driveCoast + "\n";
        ss += "    steerCoast: " + steerCoast + "\n";
        ss += "    xpos: " + xpos + "\n";
        ss += "    ypos: " + ypos + "\n";
        return ss;
    }
    


}
