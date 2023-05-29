package frc.robot.commands.TeleopCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Pivot;

public class TeleopPivot extends CommandBase{

    private Pivot pivot;
    private XboxController copilot;
    
    public TeleopPivot(Pivot p){
        pivot = p;
        copilot = new XboxController(Constants.Cotnrollers.copilotPort);
        addRequirements(pivot);
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        double speed = copilot.getRightY();
        this.pivot.movePivot(speed);
    }

    @Override
    public void end(boolean interrupted){
        pivot.stop();
    }

    @Override
    public boolean isFinished(){
        return !copilot.getLeftStickButton();
    }
    
}
