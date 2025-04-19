package frc.robot.subsystems;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.subsystems.BatteryMonitor.BatteryState;
import frc.utils.StormSubsystem;

import static edu.wpi.first.units.Units.*;

public class Lights extends StormSubsystem {
    // Alliance variables
    private final RobotState robotState;
    Color RED_COLOR = Color.kRed;
    Color BLUE_COLOR = Color.kBlue;
    Color ORANGE_COLOR = new Color(255, 32, 0);

    private AddressableLED addressableLED;
    private AddressableLEDBuffer addressableLEDBuffer;
    private boolean batteryPulse = false;
    private double batteryPulsePeriod;

    public Lights() {
        addressableLED = new AddressableLED(Constants.Lights.port);
        addressableLEDBuffer = new AddressableLEDBuffer(Constants.Lights.ledLength);

        addressableLED.setLength(addressableLEDBuffer.getLength());
        addressableLED.start();

        robotState = RobotState.getInstance();
        batteryPulsePeriod = Constants.Lights.batteryPulsePeriod;
    }

    @Override
    public void periodic() {
        super.periodic();
        this.batteryPulse = (robotState.getBatteryState() != BatteryState.GOOD);

        if (robotState.getIsShooting() || robotState.getIsIntaking()) {
            setRainbow();
        } else if (robotState.getIsOuttaking()) {
            setSolid(BLUE_COLOR);
        }
        else if (robotState.getUpperSensorTriggered()) {
            setSolid(ORANGE_COLOR);
        } else {
            setSolid(RED_COLOR);
        }

        // Write the data to the LED strip
        addressableLED.setData(addressableLEDBuffer);
    }

    // Wrapper function to allow pulse effect to be applied to any pattern. Call this function rather
    // than calling pattern.applyTo()
    private <T extends LEDReader & LEDWriter> void patternApplyTo(LEDPattern basePattern, T view) {
        LEDPattern finalPattern = basePattern;

        if (batteryPulse) {
            finalPattern = basePattern.breathe(Seconds.of(batteryPulsePeriod));
        }

        finalPattern.applyTo(view);
    }

    public void setRainbow() {
        LEDPattern rainbow = LEDPattern.rainbow(255, 128);
        Distance kLedSpacing = Meters.of(1 / 120.0);
        LEDPattern scrollingRainbow = rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(0.45), kLedSpacing);
        patternApplyTo(scrollingRainbow, addressableLEDBuffer);
    }

    public void setSolid(Color color) {
        LEDPattern pattern = LEDPattern.solid(color);
        patternApplyTo(pattern, addressableLEDBuffer);
    }
}
