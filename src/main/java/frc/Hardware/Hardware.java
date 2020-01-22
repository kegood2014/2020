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

import frc.HardwareInterfaces.DoubleThrowSwitch;
import frc.HardwareInterfaces.IRSensor;
import frc.HardwareInterfaces.KilroyEncoder;
import frc.HardwareInterfaces.KilroySPIGyro;
import frc.HardwareInterfaces.LightSensor;
import frc.HardwareInterfaces.MomentarySwitch;
import frc.HardwareInterfaces.Potentiometer;
import frc.HardwareInterfaces.LightSensor;
import frc.HardwareInterfaces.SingleThrowSwitch;
import frc.HardwareInterfaces.SixPositionSwitch;
import frc.vision.*;
import frc.Utils.drive.Drive;
import frc.Utils.Launcher;
import frc.Utils.Telemetry;
import frc.HardwareInterfaces.Transmission.TankTransmission;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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

public class Hardware {

   public static enum Identifier {
        CurrentYear, PrevYear
    };

    public static Identifier robotIdentity = Identifier.CurrentYear;

    public static void initialize() {

        // ==============Buttons=============
        cancelAuto = new JoystickButton(Hardware.rightDriver, 5);
        gearUp = new JoystickButton(Hardware.rightDriver, 1);
        gearDown = new JoystickButton(Hardware.leftDriver, 1);
        launchButton = new JoystickButton(Hardware.rightOperator, 1);
        intakeButton = new JoystickButton(Hardware.leftOperator, 1);

        if (robotIdentity == Identifier.CurrentYear) {

            
            // ==============CAN INIT=============
            // Motor Controllers          
            leftFrontMotor = new WPI_TalonFX(13);
            rightFrontMotor = new WPI_TalonFX(15);
            // leftRearMotor = new WPI_TalonFX(12);
            // rightRearMotor = new WPI_TalonFX(14);


            leftDriveGroup = new SpeedControllerGroup(/*leftRearMotor,*/leftFrontMotor);
            rightDriveGroup = new SpeedControllerGroup(/*rightRearMotor,*/ rightFrontMotor);


            leftEncoder = new KilroyEncoder((WPI_TalonFX) leftFrontMotor);
            rightDriveEncoder = new KilroyEncoder((WPI_TalonFX) rightFrontMotor);

            // ==============DIO INIT=============

            // ============ANALOG INIT============

            // ==============RIO INIT=============

            // =============OTHER INIT============
            visionInterface = new NewVisionInterface();
            visionDriving = new NewDriveWithVision();
            transmission = new TankTransmission(leftDriveGroup, rightDriveGroup);
            drive = new Drive(transmission, leftEncoder, rightDriveEncoder, gyro);

        } else if (robotIdentity == Identifier.PrevYear) {

            // ==============DIO INIT=============

            // ============ANALOG INIT============
            // delayPot = new Potentiometer(0);

            // ==============CAN INIT=============
            // Motor Controllers
            //leftFrontMotor = new CANSparkMax(13, MotorType.kBrushless);
            // rightFrontMotor = new CANSparkMax(15, MotorType.kBrushless);
            // leftRearMotor = new CANSparkMax(2, MotorType.kBrushless);
            // rightRearMotor = new CANSparkMax(3, MotorType.kBrushless);

            // leftFrontMotor = new WPI_TalonFX(13);

            // rightFrontMotor = new WPI_TalonFX(15);

            // Encoders
<<<<<<< HEAD
            //leftEncoder = new KilroyEncoder((CANSparkMax) leftFrontMotor);
            //rightEncoder = new KilroyEncoder((CANSparkMax) rightFrontMotor);

            //leftDriveGroup = new SpeedControllerGroup(/* leftRearMotor, */ leftFrontMotor);
            //rightDriveGroup = new SpeedControllerGroup(/* rightRearMotor, */
            //        rightFrontMotor);
            // ==============RIO INIT==============

            // =============OTHER INIT============
            //transmission = new TankTransmission(leftDriveGroup, rightDriveGroup);
            //drive = new Drive(transmission, null, null, gyro);
            // drivePID = new DrivePID(transmission, leftEncoder, rightEncoder, gyro);
=======
            leftEncoder = new KilroyEncoder((CANSparkMax) leftFrontMotor);
            rightDriveEncoder = new KilroyEncoder((CANSparkMax) rightFrontMotor);

           

            leftDriveGroup = new SpeedControllerGroup(/* leftRearMotor, */ leftFrontMotor);
            rightDriveGroup = new SpeedControllerGroup(/* rightRearMotor, */
                    rightFrontMotor);

            // ==============RIO INIT==============

            // =============OTHER INIT============
            transmission = new TankTransmission(leftDriveGroup, rightDriveGroup);
            drive = new Drive(transmission, null, null, gyro);
            // drivePID = new DrivePID(transmission, leftEncoder, , gyro);
>>>>>>> 9a11d5eff5e0d68afe57b34667b6c26bf41335af

            visionInterface = new NewVisionInterface();
            visionDriving = new NewDriveWithVision();
            launcher = new Launcher(intakeRL, firingRL, upStoreRL, lowStoreRL, null, null);

            // armMotor = new WPI_TalonSRX(24);
            // liftMotor = new WPI_TalonSRX(23);
            // armRoller = new WPI_TalonSRX(10);

<<<<<<< HEAD
            //Hardware.leftFrontMotor.setInverted(false);
            //Hardware.rightFrontMotor.setInverted(true);
=======
            Hardware.leftFrontMotor.setInverted(false);
            Hardware.rightFrontMotor.setInverted(true);
            

           
>>>>>>> 9a11d5eff5e0d68afe57b34667b6c26bf41335af

            leftEncoder.setDistancePerPulse(DISTANCE_PER_TICK_XIX);
            rightDriveEncoder.setDistancePerPulse(DISTANCE_PER_TICK_XIX);

        }
        
    }

    // **********************************************************
    // CAN DEVICES
    // **********************************************************

    public static SpeedController leftRearMotor = null;
    public static SpeedController rightRearMotor = null;
    public static SpeedController leftFrontMotor = null;
    public static SpeedController rightFrontMotor = null;

    public static SpeedControllerGroup leftDriveGroup = null;
    public static SpeedControllerGroup rightDriveGroup = null;

    public static KilroyEncoder leftEncoder = null;
    public static KilroyEncoder rightDriveEncoder = null;
    public static KilroyEncoder liftingEncoder = null;

    

    // public static SpeedController liftMotor = null;

    // **********************************************************
    // DIGITAL I/O
    // **********************************************************

    public static LightSensor intakeRL = new LightSensor(12); // bottom
    public static LightSensor lowStoreRL = new LightSensor(3); // lower middle
    public static LightSensor upStoreRL = new LightSensor(4); // upper middle
    public static LightSensor firingRL = new LightSensor(1); // top

    public static SixPositionSwitch autoSixPosSwitch = new SixPositionSwitch(13, 14, 15, 16, 17, 18);
    public static SingleThrowSwitch demoSwitch = new SingleThrowSwitch(0);

    public static SingleThrowSwitch autoCrossTheLineForward = new SingleThrowSwitch(22);
    public static SingleThrowSwitch autoCrossTheLineBack = new SingleThrowSwitch(23);
    public static DoubleThrowSwitch autoDisabled = new DoubleThrowSwitch(autoCrossTheLineForward, autoCrossTheLineBack);

    // public static SingleThrowSwitch autoZeroBallsIn = new SingleThrowSwitch(24);
    // public static SingleThrowSwitch autoThreeBallsIn = new SingleThrowSwitch(25);
    // public static DoubleThrowSwitch autoTwoBalls = new DoubleThrowSwitch(autoZeroBallsIn, autoThreeBallsIn);

    // **********************************************************
    // ANALOG I/O
    // **********************************************************

    public static Potentiometer delayPot = new Potentiometer(0);

    // **********************************************************
    // PNEUMATIC DEVICES
    // **********************************************************

    // **********************************************************
    // roboRIO CONNECTIONS CLASSES
    // **********************************************************

    public static PowerDistributionPanel pdp = new PowerDistributionPanel(2);

    public static KilroySPIGyro gyro = new KilroySPIGyro(false);

    // **********************************************************
    // DRIVER STATION CLASSES
    // **********************************************************

    public static DriverStation driverStation = DriverStation.getInstance();

    public static Joystick leftDriver = new Joystick(0);
    public static Joystick rightDriver = new Joystick(1);
    public static Joystick leftOperator = new Joystick(2);
    public static Joystick rightOperator = new Joystick(3);

    // **********************************************************
    // Buttons
    // **********************************************************

    public static MomentarySwitch invertTempoMomentarySwitch = new MomentarySwitch();

    public static JoystickButton cancelAuto = new JoystickButton(Hardware.rightDriver, 5);
    public static JoystickButton gearUp = new JoystickButton(Hardware.rightDriver, 1);
    public static JoystickButton gearDown = new JoystickButton(Hardware.leftDriver, 1);
    public static JoystickButton launchButton = new JoystickButton(Hardware.rightOperator, 1);
    public static JoystickButton intakeButton = new JoystickButton(Hardware.leftOperator, 1);
    // **********************************************************
    // Kilroy's Ancillary classes
    // **********************************************************

    public static UsbCamera usbCam0 = CameraServer.getInstance().startAutomaticCapture("usb0",0);
    public static UsbCamera usbCam1 = CameraServer.getInstance().startAutomaticCapture(1);

    // ------------------------------------
    // Utility classes
    // ------------------------------------
    public static Timer autoTimer = new Timer();

    public static Timer telopTimer = new Timer();

    public static Timer camTimer1 = new Timer();

    public static Timer camTimer2 = new Timer();

    public static Telemetry telemetry = new Telemetry(driverStation);

    // ------------------------------------
    // Drive system
    // ------------------------------------
    public static Drive drive = null;

    public static TankTransmission transmission = new TankTransmission(leftDriveGroup, rightDriveGroup);

    // ------------------------------------------
    // Vision stuff
    // ----------------------------

    public static NewDriveWithVision visionDriving = null;

    public static NewVisionInterface visionInterface = null;

    public static Launcher launcher = null;

    public final static double DISTANCE_PER_TICK_XIX = 23/13.8;//.0346;
    // -------------------
    // Subassemblies
    // -------------------

} // end class
