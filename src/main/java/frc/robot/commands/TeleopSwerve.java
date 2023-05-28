package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class TeleopSwerve extends CommandBase {
    private double rotation;
    private Translation2d translation;
    private BooleanSupplier fieldRelative;

    private Swerve swerve;
    private XboxController driverController;

    public TeleopSwerve(Swerve s, XboxController c, BooleanSupplier field) {
        swerve = s;
        driverController = c;
        fieldRelative = field;

        addRequirements(swerve);
    }

    @Override
    public void execute() {
        if(fieldRelative.getAsBoolean() ==true){
            Shuffleboard.getTab("Karam").add(getName(), true);
        }
        else{
            Shuffleboard.getTab("Karam").add(getName(), false);
        }

        double x = -driverController.getLeftX();
        double y = -driverController.getLeftY();
        double rot = -driverController.getRightX();

        //square inputs
        y= Math.copySign(y*y, y);
        x = Math.copySign(x*x, x);
        rot = Math.copySign(rot*rot, rot);

        translation = new Translation2d(x, y).times(Constants.Swerve.maxSpeed);
        rotation  = rot * Constants.Swerve.maxAngularVel;

        swerve.drive(translation.times(0.8), rotation*0.8, fieldRelative.getAsBoolean());

    }
}
