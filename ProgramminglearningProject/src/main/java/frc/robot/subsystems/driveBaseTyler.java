// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class driveBaseTyler extends SubsystemBase {
  /** Creates a new driveBaseTyler. */
  
    private final WPI_TalonFX leftMotorMaster = new WPI_TalonFX(0);
    private final WPI_TalonFX rightMotorMaster = new WPI_TalonFX(1);
    private final WPI_TalonFX leftMotorSlave = new WPI_TalonFX(2);
    private final WPI_TalonFX rightMotorSlave = new WPI_TalonFX(3);
    private final WPI_TalonFX leftMotorSlave2 = new WPI_TalonFX(3);
    private final WPI_TalonFX rightMotorSlave2 = new WPI_TalonFX(3);

    private boolean isBrake = true;

    private final DifferentialDrive drive = new DifferentialDrive(leftMotorMaster, rightMotorMaster);
public driveBaseTyler() {

  leftMotorSlave.follow(leftMotorMaster);
  rightMotorSlave.follow(rightMotorMaster);
  leftMotorSlave2.follow(leftMotorMaster);
  rightMotorSlave2.follow(rightMotorMaster);

  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
  
  public void switchMotorMode(){
    //Either has the robot coast or brake.
    if(!isBrake){
      isBrake = true;
      leftMotorMaster.setNeutralMode(NeutralMode.Brake);
      rightMotorMaster.setNeutralMode(NeutralMode.Brake);
      leftMotorSlave.setNeutralMode(NeutralMode.Brake);
      rightMotorSlave.setNeutralMode(NeutralMode.Brake);
      leftMotorSlave2.setNeutralMode(NeutralMode.Brake);
      rightMotorSlave2.setNeutralMode(NeutralMode.Brake);
    }
    else{
      isBrake = false;
      leftMotorMaster.setNeutralMode(NeutralMode.Coast);
      rightMotorMaster.setNeutralMode(NeutralMode.Coast);
      leftMotorSlave.setNeutralMode(NeutralMode.Coast);
      rightMotorSlave.setNeutralMode(NeutralMode.Coast);
      leftMotorSlave2.setNeutralMode(NeutralMode.Coast);
      rightMotorSlave2.setNeutralMode(NeutralMode.Coast);
      SmartDashboard.putBoolean("isBrake", isBrake);
    }

  }
  public void teleopDrive(double speed, double turn) {
    drive.arcadeDrive(speed, turn);
  }
}
