package frc.Utils;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.Hardware.Hardware;
import frc.HardwareInterfaces.LightSensor;

public class StorageControl
    {
    LightSensor intakeRL = null;
    LightSensor lowerRL = null;
    LightSensor upperRL = null;
    LightSensor shootRL = null;

    SpeedControllerGroup conveyorMotors = null;

    public StorageControl(LightSensor intakeRL, LightSensor lowerRL, LightSensor upperRL, LightSensor shootRL,
            SpeedControllerGroup conveyorMotors)
        {
            this.intakeRL = intakeRL;
            this.lowerRL = lowerRL;
            this.upperRL = upperRL;
            this.shootRL = shootRL;
            this.conveyorMotors = conveyorMotors;
        }

    private enum ControlState
        {
        INIT, PASSIVE, UP, DOWN
        }

    public ControlState state = ControlState.INIT;

    public void storageControlState()
    {

        SmartDashboard.putNumber("", Hardware.ballcounter.getBallCount());

        SmartDashboard.putBoolean("Green", Hardware.visionInterface.getDistanceFromTarget() <= 120);

        SmartDashboard.putBoolean("Red", Hardware.visionInterface.getDistanceFromTarget() > 120);

        switch (state)
            {
            case INIT:
                state = ControlState.PASSIVE;
                break;
            case PASSIVE:
                //this.conveyorMotors.set(HOLDING_SPEED);

                if (this.intakeRL.get() && prevRL == false)
                    {
                    prevRL = true;
                    if (Hardware.intake.intaking)
                        {
                        System.out.println("adding");
                        Hardware.ballcounter.addBall();
                        }
                    else if (Hardware.intake.outtaking)
                        {
                        System.out.println("subtracting");
                        Hardware.ballcounter.subtractBall();
                        }
                    }
                if (!this.intakeRL.get())
                    {
                    prevRL = false;
                    }

                break;
            case UP:
                conveyorUp();
                break;
            case DOWN:
                conveyorDown();
                break;
            default:
                state = ControlState.PASSIVE;
                break;
            }
    }

    public void conveyorUp()
    {
        //sets the motors to UP_SPEED
        //whats UP_SPEED?
        //SPEED: not much, how about you?

        System.out.println("conveyor up");
        Hardware.conveyorMotorGroup.set(UP_SPEED);
    }

    public void conveyorDown()
    {
        System.out.println("conveyor down");
        Hardware.conveyorMotorGroup.set(DOWN_SPEED);
    }

    private enum ShootState
        {
        INITIAL_UP, WAIT_FOR_POWER, INIT
        }

    ShootState shootState = ShootState.INIT;

    public boolean prepareToShoot()
    {

        if (Hardware.ballcounter.getBallCount() > 0)
            {
            switch (shootState)
                {
                case INIT:
                    shootState = ShootState.INITIAL_UP;
                    break;
                case INITIAL_UP:
                    //TODO this might have to be the upperRL
                    if (this.shootRL.get())
                        {
                        System.out.println("got shoot rl");
                        state = ControlState.PASSIVE;
                        shootState = ShootState.WAIT_FOR_POWER;
                        }
                    else
                        {
                        state = ControlState.UP;
                        }
                    break;
                case WAIT_FOR_POWER:

                    preparedToFire = true;
                    return true;
                default:
                    shootState = ShootState.INIT;
                    break;
                }
            }

        return false;
    }

    public boolean loadToFire()
    {
        if (preparedToFire)
            {

            if (this.shootRL.get())
                {
                System.out.println("shooting ball");
                state = ControlState.UP;
                }
            else
                {
                state = ControlState.PASSIVE;
                if (Hardware.ballcounter.getBallCount() > 1)
                    {
                    prepareToShoot();
                    return true;
                    }
                }
            }
        return false;
    }

    private static boolean prevRL = false;

    private static boolean preparedToFire = false;

    final double HOLDING_SPEED = 0;

    final double UP_SPEED = .2;

    final double DOWN_SPEED = -.2;

    }
