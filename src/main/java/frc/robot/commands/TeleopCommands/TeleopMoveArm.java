package frc.robot.commands.TeleopCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.TelescopicArm;

public class TeleopMoveArm extends CommandBase{
    private TelescopicArm arm;
    private XboxController copilot;

    public TeleopMoveArm(TelescopicArm a){

        arm = a;
        copilot = new XboxController(Constants.Cotnrollers.copilotPort);
        addRequirements(arm);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double speed = copilot.getRightY();
        this.arm.moveArm(speed);
    }

    @Override
    public void end(boolean interrupted){
        if(interrupted){
            System.out.println("Teleop move arm command interrupted");
        }
        arm.stop();
    }

    @Override
    public boolean isFinished(){
        return !copilot.getRightStickButton();
    }

}
