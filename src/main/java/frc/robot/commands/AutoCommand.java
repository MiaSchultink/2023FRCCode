package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.TelescopicArm;

public class AutoCommand extends CommandBase{
    private Swerve swerve;
    private TelescopicArm arm;
    private Intake intake;
    
    
    public AutoCommand(Swerve s, TelescopicArm a, Intake i){
        s = swerve;
        a = arm;
        i = intake;

        addRequirements(s, a, i);
    }


}
