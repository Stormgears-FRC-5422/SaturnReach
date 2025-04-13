package frc.robot.subsystems.drive.config;/*
 * Copyright (C) Cross The Road Electronics.  All rights reserved.
 * License information can be found in CTRE_LICENSE.txt
 * For support and suggestions contact support@ctr-electronics.com or file
 * an issue tracker at https://github.com/CrossTheRoadElec/Phoenix-Releases
 */
// This class was shamelessly stolen from the Slot0Configs class from CTRE. Simplified for the outreach robot

public class PidConfig {
    /**
     * Proportional Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by error in the input, the units should be
     * defined as units of output per unit of input error. For example,
     * when controlling velocity using a duty cycle closed loop, the units
     * for the proportional gain will be duty cycle per rps of error, or
     * 1/rps.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */
    public double kP = 0;

    /**
     * Integral Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by error in the input integrated over time
     * (in units of seconds), the units should be defined as units of
     * output per unit of integrated input error. For example, when
     * controlling velocity using a duty cycle closed loop, integrating
     * velocity over time results in rps * s = rotations. Therefore, the
     * units for the integral gain will be duty cycle per rotation of
     * accumulated error, or 1/rot.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */

    public double kI = 0;
    /**
     * Derivative Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the derivative of error in the input
     * with respect to time (in units of seconds), the units should be
     * defined as units of output per unit of the differentiated input
     * error. For example, when controlling velocity using a duty cycle
     * closed loop, the derivative of velocity with respect to time is rot
     * per sec², which is acceleration. Therefore, the units for the
     * derivative gain will be duty cycle per unit of acceleration error,
     * or 1/(rot per sec²).
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */

    public double kD = 0;
    /**
     * Static Feedforward Gain.
     * <p>
     * This is added to the closed loop output. The unit for this constant
     * is dependent on the control mode, typically fractional duty cycle,
     * voltage, or torque current.
     * <p>
     * The sign is typically determined by reference velocity when using
     * position, velocity, and Motion Magic® closed loop modes. However,
     * when using position closed loop with zero velocity reference (no
     * motion profiling), the application can instead use the position
     * closed loop error by setting the Static Feedforward Sign
     * configuration parameter.  When doing so, we recommend the minimal
     * amount of kS, otherwise the motor output may dither when closed
     * loop error is near zero.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> -512
     *   <li> <b>Maximum Value:</b> 511
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */

    public double kS = 0;

    /**
     * Velocity Feedforward Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the requested velocity, the units should
     * be defined as units of output per unit of requested input velocity.
     * For example, when controlling velocity using a duty cycle closed
     * loop, the units for the velocity feedfoward gain will be duty cycle
     * per requested rps, or 1/rps.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */
    public double kV = 0;

    /**
     * Acceleration Feedforward Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the requested acceleration, the units
     * should be defined as units of output per unit of requested input
     * acceleration. For example, when controlling velocity using a duty
     * cycle closed loop, the units for the acceleration feedfoward gain
     * will be duty cycle per requested rot per sec², or 1/(rot per sec²).
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     */
    public double kA = 0;

    /**
     * Modifies this configuration's kP parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Proportional Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by error in the input, the units should be
     * defined as units of output per unit of input error. For example,
     * when controlling velocity using a duty cycle closed loop, the units
     * for the proportional gain will be duty cycle per rps of error, or
     * 1/rps.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKP Parameter to modify
     * @return Itself
     */
    public PidConfig withKP(double newKP)
    {
        kP = newKP;
        return this;
    }

    /**
     * Modifies this configuration's kI parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Integral Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by error in the input integrated over time
     * (in units of seconds), the units should be defined as units of
     * output per unit of integrated input error. For example, when
     * controlling velocity using a duty cycle closed loop, integrating
     * velocity over time results in rps * s = rotations. Therefore, the
     * units for the integral gain will be duty cycle per rotation of
     * accumulated error, or 1/rot.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKI Parameter to modify
     * @return Itself
     */
    public PidConfig withKI(double newKI)
    {
        kI = newKI;
        return this;
    }

    /**
     * Modifies this configuration's kD parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Derivative Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the derivative of error in the input
     * with respect to time (in units of seconds), the units should be
     * defined as units of output per unit of the differentiated input
     * error. For example, when controlling velocity using a duty cycle
     * closed loop, the derivative of velocity with respect to time is rot
     * per sec², which is acceleration. Therefore, the units for the
     * derivative gain will be duty cycle per unit of acceleration error,
     * or 1/(rot per sec²).
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKD Parameter to modify
     * @return Itself
     */
    public PidConfig withKD(double newKD)
    {
        kD = newKD;
        return this;
    }

    /**
     * Modifies this configuration's kS parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Static Feedforward Gain.
     * <p>
     * This is added to the closed loop output. The unit for this constant
     * is dependent on the control mode, typically fractional duty cycle,
     * voltage, or torque current.
     * <p>
     * The sign is typically determined by reference velocity when using
     * position, velocity, and Motion Magic® closed loop modes. However,
     * when using position closed loop with zero velocity reference (no
     * motion profiling), the application can instead use the position
     * closed loop error by setting the Static Feedforward Sign
     * configuration parameter.  When doing so, we recommend the minimal
     * amount of kS, otherwise the motor output may dither when closed
     * loop error is near zero.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> -512
     *   <li> <b>Maximum Value:</b> 511
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKS Parameter to modify
     * @return Itself
     */
    public PidConfig withKS(double newKS)
    {
        kS = newKS;
        return this;
    }

    /**
     * Modifies this configuration's kV parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Velocity Feedforward Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the requested velocity, the units should
     * be defined as units of output per unit of requested input velocity.
     * For example, when controlling velocity using a duty cycle closed
     * loop, the units for the velocity feedfoward gain will be duty cycle
     * per requested rps, or 1/rps.
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKV Parameter to modify
     * @return Itself
     */
    public PidConfig withKV(double newKV)
    {
        kV = newKV;
        return this;
    }

    /**
     * Modifies this configuration's kA parameter and returns itself for
     * method-chaining and easier to use config API.
     * <p>
     * Acceleration Feedforward Gain.
     * <p>
     * The units for this gain is dependent on the control mode. Since
     * this gain is multiplied by the requested acceleration, the units
     * should be defined as units of output per unit of requested input
     * acceleration. For example, when controlling velocity using a duty
     * cycle closed loop, the units for the acceleration feedfoward gain
     * will be duty cycle per requested rot per sec², or 1/(rot per sec²).
     *
     * <ul>
     *   <li> <b>Minimum Value:</b> 0
     *   <li> <b>Maximum Value:</b> 3.4e+38
     *   <li> <b>Default Value:</b> 0
     *   <li> <b>Units:</b>
     * </ul>
     *
     * @param newKA Parameter to modify
     * @return Itself
     */
    public PidConfig withKA(double newKA)
    {
        kA = newKA;
        return this;
    }

    public static PidConfig from(PidConfig value)
    {
        return new PidConfig() {{
            kP = value.kP;
            kI = value.kI;
            kD = value.kD;
            kS = value.kS;
            kV = value.kV;
            kA = value.kA;
        }};
    }

    @Override
    public String toString()
    {
        String ss = "PID Config:\n";
        ss += "    kP: " + kP + "\n";
        ss += "    kI: " + kI + "\n";
        ss += "    kD: " + kD + "\n";
        ss += "    kS: " + kS + "\n";
        ss += "    kV: " + kV + "\n";
        ss += "    kA: " + kA + "\n";
        return ss;
    }

}

