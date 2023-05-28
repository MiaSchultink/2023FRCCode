package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class DriveForwards extends CommandBase {

    private Swerve swerve;
    private double distane;
    private double maxError;
    
    private PIDController pidController = new PIDController(1, 1, 1);


    public DriveForwards(Swerve s, double d, double maxE) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        swerve = s;
        distane = d;
        maxError = maxE;
        addRequirements(swerve);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    public void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    public void interrupted() {
    }
}