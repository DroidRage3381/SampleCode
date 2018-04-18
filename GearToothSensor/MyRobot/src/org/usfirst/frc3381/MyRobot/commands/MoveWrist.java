// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3381.MyRobot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3381.MyRobot.Robot;
import org.usfirst.frc3381.MyRobot.subsystems.Intake;

/**
 *
 */
public class MoveWrist extends Command {

	private int setPoint;
	private double speed;
	private boolean isGoingDown = true;
	
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public MoveWrist() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.intake);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	
    	speed = 0.5; // get from preference?
    	
    	int povPosition = Robot.oi.gamePad.getPOV();
    	
    	int desiredPosition;
    	switch(povPosition) {
    		case 0:
    			desiredPosition = Intake.positionUp;
    			break;
    		case 90:
    			desiredPosition = Intake.positionMid;
    			break;
    		case 180:
    			desiredPosition = Intake.positionDown;
    			break;
    		default:
    			desiredPosition = Intake.positionDown;
    			break;
    	}
    	
    	setPoint = desiredPosition - Robot.intake.currentPosition;
    	//Robot.intake.currentPosition = Robot.intake.currentPosition + setPoint;
    	
    	if(setPoint < 0) {
    		setPoint = setPoint * -1; // turns a negative set-point positive
    		speed = speed * -1; // sets the speed negative for reverse
    		isGoingDown = false;
    	} else {
    		isGoingDown = true;
    	}
    	
    	Robot.intake.resetCount();
    	
    	SmartDashboard.putNumber("Wrist: povPosition", povPosition);
    	SmartDashboard.putNumber("Wrist: setPoint", setPoint);
    	SmartDashboard.putBoolean("Wrist: isGoingDown", isGoingDown);
    }


	// Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.intake.setSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    	int currentCount = Robot.intake.getCount();
    	SmartDashboard.putNumber("Wrist: currentCount", currentCount);
        return currentCount >= setPoint;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.intake.setSpeed(0);
    	
    	if(isGoingDown) {
    		Robot.intake.currentPosition = Robot.intake.currentPosition + Robot.intake.getCount();
    	} else {
    		Robot.intake.currentPosition = Robot.intake.currentPosition - Robot.intake.getCount();
    	}
    	
    	SmartDashboard.putNumber("Wrist: currentPosition", Robot.intake.currentPosition);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}
