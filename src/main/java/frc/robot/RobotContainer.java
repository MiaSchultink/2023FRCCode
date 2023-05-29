// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.security.cert.TrustAnchor;
import java.util.function.BooleanSupplier;

import org.apache.commons.io.input.TeeInputStream;

import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.AutoCommands.AutoCommand;
import frc.robot.commands.AutoCommands.AutoMoveIntake;
import frc.robot.commands.TeleopCommands.TeleopIntake;
import frc.robot.commands.TeleopCommands.TeleopMoveArm;
import frc.robot.commands.TeleopCommands.TeleopOuttake;
import frc.robot.commands.TeleopCommands.TeleopPivot;
import frc.robot.commands.TeleopCommands.TeleopSwerve;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.TelescopicArm;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final XboxController driverController;
  private final XboxController copilotController;

  private final Swerve swerve;
  private final Pivot pivot;
  private final Intake intake;
  private final TelescopicArm arm;

  private final AutoCommand autoCommand;
  private final TeleopMoveArm teleopMoveArm;
  private final TeleopPivot teleopPivot;
  private final TeleopIntake teleopIntake;
  private final TeleopOuttake teleopOuttake;
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driverController =  new XboxController(Constants.Cotnrollers.driverPort);
    copilotController  = new XboxController(Constants.Cotnrollers.copilotPort);
  
    swerve = new Swerve();
    arm = new TelescopicArm();
    pivot = new Pivot();
    intake = new Intake();

    autoCommand = new AutoCommand(swerve, arm, intake, pivot);
    teleopMoveArm = new TeleopMoveArm(arm);
    teleopPivot = new TeleopPivot(pivot);
    teleopIntake = new TeleopIntake(intake);
    teleopOuttake = new TeleopOuttake(intake);

    // Configure the button bindings

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new Trigger(copilotController::getLeftStickButton).onTrue(teleopMoveArm);
    new Trigger(copilotController::getRightStickButton).onTrue(teleopPivot);
    new Trigger(copilotController::getRightBumper).onTrue(teleopIntake);
    new Trigger(copilotController::getLeftBumper).onTrue(teleopOuttake);
    swerve.setDefaultCommand(new TeleopSwerve(swerve, copilotController, () -> driverController.getLeftStickButton()));
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return autoCommand;
  }
}
