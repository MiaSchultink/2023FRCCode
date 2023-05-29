package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Pivot;

public class AutoPivot extends CommandBase{
    private Pivot pivot;
    private double pivotEncoderValue;
    private double speed;

    public AutoPivot(Pivot p, double pivVal, double s){
        pivot = p;
        pivotEncoderValue = pivVal;
        speed = s;

        addRequirements(p);
    }

    @Override
    public void initialize(){

    }

    public void pivotToPos(){
        if(pivot.getPivotEncoderValue() > pivotEncoderValue){
            pivot.movePivot(speed);
        }
        else if(pivot.getPivotEncoderValue() < pivotEncoderValue){
            pivot.movePivot(-speed);
        }
        else{
            pivot.movePivot(0);
        }
    }

    @Override
    public void execute(){
        pivotToPos();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
