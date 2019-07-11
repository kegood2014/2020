// ====================================================================
// FILE NAME: Hardware.java (Team 339 - Kilroy)
//
// CREATED ON: Jan 2, 2011
// CREATED BY: Bob Brown
// MODIFIED ON: June 24, 2019
// MODIFIED BY: Ryan McGee
// ABSTRACT:
// This file contains all of the global definitions for the
// hardware objects in the system
//
// NOTE: Please do not release this code without permission from
// Team 339.
// ====================================================================
package frc.Hardware;

import frc.HardwareInterfaces.KilroyEncoder;
import frc.HardwareInterfaces.KilroySPIGyro;
import frc.Utils.drive.Drive;
import frc.Utils.drive.DrivePID;
import frc.Utils.Telemetry;
import frc.HardwareInterfaces.Transmission.TankTransmission;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;


/**
 * ------------------------------------------------------- puts all of the
 * hardware declarations into one place. In addition, it makes them available to
 * both autonomous and teleop.
 *
 * @class HardwareDeclarations
 * @author Bob Brown
 * 
 * @written Jan 2, 2011 -------------------------------------------------------
 */

public class Hardware
{

enum Identifier {CurrentYear, PrevYear};

public static Identifier robotIdentity = Identifier.CurrentYear;

public static void initialize()
{
        if(robotIdentity == Identifier.CurrentYear)
        {
        // ==============DIO INIT=============

        // ============ANALOG INIT============

        // ==============CAN INIT=============
                //Motor Controllers
                leftRearMotor = new CANSparkMax(13, MotorType.kBrushless);
                rightRearMotor = new CANSparkMax(15, MotorType.kBrushless);
                leftFrontMotor = new CANSparkMax(11, MotorType.kBrushless);
                rightFrontMotor = new CANSparkMax(14, MotorType.kBrushless);
                
                //Encoders
                leftRearEncoder = new KilroyEncoder((CANSparkMax) leftRearMotor);
                rightRearEncoder = new KilroyEncoder((CANSparkMax) rightRearMotor);
                leftFrontEncoder = new KilroyEncoder((CANSparkMax) leftFrontMotor);
                rightFrontEncoder = new KilroyEncoder((CANSparkMax) rightFrontMotor);
        // ==============RIO INIT==============
                gyro = new KilroySPIGyro(false);
        // =============OTHER INIT============
                transmission = new TankTransmission(leftDriveGroup, rightDriveGroup);
                drive = new Drive(transmission, leftFrontEncoder, rightFrontEncoder, leftRearEncoder, rightRearEncoder, gyro);
                drivePID = new DrivePID(transmission, leftFrontEncoder, rightFrontEncoder, leftRearEncoder, rightRearEncoder, gyro);

        }else if(robotIdentity == Identifier.PrevYear)
        {
        // ==============DIO INIT=============

        // ============ANALOG INIT============

        // ==============CAN INIT=============

        // ==============RIO INIT=============

        // =============OTHER INIT============
        }
}

// **********************************************************
// CAN DEVICES
// **********************************************************

public static SpeedController leftRearMotor = null;
public static SpeedController rightRearMotor = null;
public static SpeedController leftFrontMotor = null;
public static SpeedController rightFrontMotor = null;

public static SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(leftRearMotor, leftFrontMotor);
public static SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(rightRearMotor, rightFrontMotor);

public static KilroyEncoder leftRearEncoder = null;
public static KilroyEncoder rightRearEncoder = null;
public static KilroyEncoder leftFrontEncoder = null;
public static KilroyEncoder rightFrontEncoder = null;

// **********************************************************
// DIGITAL I/O 
// **********************************************************

// **********************************************************
// ANALOG I/O 
// **********************************************************

// **********************************************************
// PNEUMATIC DEVICES
// **********************************************************

// **********************************************************
// roboRIO CONNECTIONS CLASSES
// **********************************************************

public static PowerDistributionPanel pdp = new PowerDistributionPanel();

public static KilroySPIGyro gyro = null;

// **********************************************************
// DRIVER STATION CLASSES
// **********************************************************

public static DriverStation driverStation = DriverStation.getInstance();

public static Joystick leftDriver = new Joystick(0);
public static Joystick rightDriver = new Joystick(1);
public static Joystick leftOperator = new Joystick(2);
public static Joystick rightOperator = new Joystick(3);

// **********************************************************
// Kilroy's Ancillary classes
// **********************************************************

// ------------------------------------
// Utility classes
// ------------------------------------
public static Timer autoTimer = new Timer();

public static Timer telopTimer = new Timer();

public static Telemetry telemetry = new Telemetry(driverStation);

// ------------------------------------
// Drive system
// ------------------------------------
public static Drive drive = null;

public static DrivePID drivePID = null;

public static TankTransmission transmission = null;

// -------------------
// Subassemblies
// -------------------

} // end class
