package frc.robot.commands.TeleopCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class TeleopOuttake extends CommandBase {

    private XboxController copilot;
    private Intake intake;

    public TeleopOuttake(Intake i){
        copilot = new XboxController(Constants.Cotnrollers.copilotPort);
        intake = i;
        addRequirements(intake);
    }
    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        this.intake.moveIntake(-Constants.Intake.autoSpeed);
    }

    @Override
    public void end(boolean interrupted){
        if(interrupted){
            System.out.println("Teleop intake command interrupted");
        }
        intake.stop();
    }

    @Override
    public boolean isFinished(){
        return !copilot.getLeftBumper();
    }

}
