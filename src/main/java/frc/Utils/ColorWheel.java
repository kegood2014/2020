//====================================================================
// FILE NAME: ColorWheel.java (Team 339 - Kilroy)
//
// CREATED ON: January 29, 2020
// CREATED BY: Guido Visioni
// ABSTRACT:
// This class manipulates the control panel (color wheel) for shield generator stage 2 and also color matches for shield generator stage 3.

// NOTE: Please do not release this code without permission from
// Team 339.
// ====================================================================
package frc.Utils;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import frc.Hardware.Hardware;

// -------------------------------------------------------
/**
 * This class manipulates the control panel (color wheel) for shield generator stage 2 and also color matches and manipulates for shield generator stage 3.
 *
 * @class ColorWheel
 * @author Guido Visioni
 * @written January 29, 2020
 * -------------------------------------------------------
 */

public class ColorWheel
    {
    /**
    * This method gets the FMS data for the shield 3 generator spin color. (What color we need to align with the field sensor)
    *
    * @method getSpinColor
    * @author Guido Visioni
    * @written January 30, 2020
    * -------------------------------------------------------
    */
    public String getSpinColor()
    {
        String gameData;

        //When we reach shield generator stage 3 this will recieve the FMS data and will return a string. The string will be the letter of the color.
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0)
            {
            switch (gameData.charAt(0))
                {
                case 'B':
                    return "B";
                case 'G':
                    return "G";
                case 'R':
                    return "R";
                case 'Y':
                    return "Y";
                default:
                    break;
                }
            }
        //Returns random letter if FMS data not detected
        return "A";
    }

    /**
    * This method will spin the control panel 3-5 times for shield generator stage 2 (no color sensing capabilities)
    *   Parameter distance will be amount of times to spin the control panel x 100.5309
        Circumference of Control Panel = 100.5309
    * @method spinControlPanel
    * @author Guido Visioni
    * @written January 30, 2020
    * -------------------------------------------------------
    */
    public boolean spinControlPanel(double distance)
    {
        // Resets wheelSpinnerEncoder
        if (driveStraightInchesInit == true)
            {
            Hardware.wheelSpinnerEncoder.reset();
            driveStraightInchesInit = false;
            }

        // Check all encoders to see if they've reached the distance
        if (Hardware.wheelSpinnerEncoder.getDistance() > distance)
            {
            Hardware.wheelSpinnerMotor.set(0);
            driveStraightInchesInit = true;
            return true;
            }

        // Spin motor until specified distance has been reached
        Hardware.wheelSpinnerMotor.set(.4);

        return false;
    }

    /**
    * This method will align the specified color from the FMS under the sensor.
    *   Parameter color will be the FMS string that represents the designated color (G=green, B=blue, etc.)
    * @method spinControlPanelToColor
    * @author Guido Visioni
    * @written January 30, 2020
    * -------------------------------------------------------
    */
    public boolean spinControlPanelToColor()
    {
        String spinColor = getSpinColor();
        Color detectedColor = Hardware.colorSensor.getColor();
        String colorString;

        //Creates color targets for the color sensor to compare the detected color to.
        final ColorMatch colorMatcher = new ColorMatch();
        final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
        final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
        final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
        final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

        //Adds the color targets to the colorMatcher
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);

        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        //Sets colorString equal to 1 of the 4 colors it detects. What color it is set to will not be what it actually detects due to offset (The detected color will not be the actual color under the sensor so strings are edited accordinly)
        if (match.color == kBlueTarget)
            {
            colorString = "G";
            }
        else if (match.color == kRedTarget)
            {
            colorString = "Y";
            }
        else if (match.color == kGreenTarget)
            {
            colorString = "R";
            }
        else if (match.color == kYellowTarget)
            {
            colorString = "B";
            }
        else
            {
            colorString = "Unknown";
            }

        // Resets wheelSpinnerEncoder
        if (driveStraightInchesInit == true)
            {
            Hardware.wheelSpinnerEncoder.reset();
            driveStraightInchesInit = false;
            }

        // Check to see if the color sensor sees the color
        if (colorString == spinColor)
            {
            Hardware.wheelSpinnerMotor.set(0);
            driveStraightInchesInit = true;
            return true;
            }

        // Spin the motor until we reach spinColor
        Hardware.wheelSpinnerMotor.set(.3);

        return false;
    }

    private boolean driveStraightInchesInit = true;
    }
