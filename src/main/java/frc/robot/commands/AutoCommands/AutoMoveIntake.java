package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.math.Conversions;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class AutoMoveIntake extends CommandBase{
    private Intake intake;
    private double speed;
    
    public AutoMoveIntake(Intake i, double s){
        intake = i;
        speed = s;

        addRequirements(i);
    }

    public void autoMoveIntake(){
        long currentTime = System.currentTimeMillis();
        long runTime = Conversions.secondsToMili(Constants.Auto.outTakeTime);

        if(System.currentTimeMillis() < currentTime + runTime){
            intake.moveIntake(-speed);
        }
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        autoMoveIntake();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
