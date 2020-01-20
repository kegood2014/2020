/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/
// ====================================================================
// FILE NAME: Autonomous.java (Team 339 - Kilroy)
//
// CREATED ON: Jan 13, 2015
// CREATED BY: Nathanial Lydick
// MODIFIED ON:
// MODIFIED BY:
// ABSTRACT:
// This file is where almost all code for Kilroy will be
// written. Some of these functions are functions that should
// override methods in the base class (IterativeRobot). The
// functions are as follows:
// -----------------------------------------------------
// Init() - Initialization code for autonomous mode
// should go here. Will be called each time the robot enters
// autonomous mode.
// -----------------------------------------------------
// Periodic() - Periodic code for autonomous mode should
// go here. Will be called periodically at a regular rate while
// the robot is in autonomous mode.
// -----------------------------------------------------
//
// NOTE: Please do not release this code without permission from
// Team 339.
// ====================================================================
package frc.robot;

import frc.Hardware.Hardware;

/**
 * An Autonomous class. This class <b>beautifully</b> uses state machines in
 * order to periodically execute instructions during the Autonomous period.
 *
 * This class contains all of the user code for the Autonomous part of the
 * match, namely, the Init and Periodic code
 *
 *
 * @author Michael Andrzej Klaczynski
 * @written at the eleventh stroke of midnight, the 28th of January, Year of our
 *          LORD 2016. Rewritten ever thereafter.
 *
 * @author Nathanial Lydick
 * @written Jan 13, 2015
 */
public class Autonomous
{

/**
 * User Initialization code for autonomous mode should go here. Will run once
 * when the autonomous first starts, and will be followed immediately by
 * periodic().
 */
public static void init ()
{

    Hardware.drive.setGearPercentage(4, AUTO_GEAR);
    Hardware.drive.setGear(4);

} // end Init

/*
 * User Periodic code for autonomous mode should go here. Will be called
 * periodically at a regular rate while the robot is in autonomous mode.
 *
 * @author Nathanial Lydick
 * @written Jan 13, 2015
 *
 *          FYI: drive.stop cuts power to the motors, causing the robot to
 *          coast. drive.brake results in a more complete stop.
 *          Meghan Brown; 10 February 2019
 */

public static enum Path{
     NOTHING, LEAVE_LINE, AUTO_LAUNCH, PICK_UP_LAUNCH, PICKUP
}

public static Path path = Path.NOTHING;
public static enum State {
INIT, DELAY, CHOOSE_PATH, FINISH
}

public static State autoState = State.INIT;
public static void periodic ()
{
    if(/*Hardware.cancelAuto.get()*/true){
        autoState = State.FINISH;
    }

    switch(autoState){

        case INIT:
            Hardware.autoTimer.start();
            autoState = State.DELAY;
            break;

        case DELAY:
            if( Hardware.autoTimer.get() > Hardware.delayPot.get(0, 5.0) ){

                autoState = State.CHOOSE_PATH;
                Hardware.autoTimer.stop();
            }

            break;

         case CHOOSE_PATH:
            choosePath();
            break;

        case FINISH: 
            Hardware.drive.drive(0, 0);
             break;

        default: 
        autoState = State.FINISH;
             break;

}


}

// =====================================================================
// Path Methods
// =====================================================================
private static void choosePath(){

switch(path){

    case LEAVE_LINE:
    //if true forwards off line
    leaveLine(false);
    break;

    case NOTHING:
        autoState = State.FINISH;
        break;
    default:
    path = Path.NOTHING;
       break;

}

}

private static boolean leaveLine(boolean Direction){

    return false;
}



/*
 * ==============================================================
 * Constants
 * ==============================================================
 */
private final static double AUTO_GEAR  = 1.0;
}
