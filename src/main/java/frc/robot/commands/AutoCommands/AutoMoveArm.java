package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.TelescopicArm;

public class AutoMoveArm extends CommandBase{
    private TelescopicArm arm;
    private double armEncoderVal;
    private double speed;

    public AutoMoveArm(TelescopicArm a, double val, double s){
        arm = a;
        armEncoderVal = val;
        speed = s;

        addRequirements(a);
    }

    public void armToPos(){
        if(arm.getArmEncoderValue() < armEncoderVal && arm.getArmEncoderValue() < Constants.Arm.maxExtention){
            arm.moveArm(speed);
        }
        else if(arm.getArmEncoderValue() > armEncoderVal && arm.getArmEncoderValue() < Constants.Arm.maxRetraction){
            arm.moveArm(-speed);
        }
        else{
            arm.moveArm(0);
        }
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        armToPos();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
