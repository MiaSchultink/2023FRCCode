package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Swerve;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.TelescopicArm;

public class AutoCommand extends SequentialCommandGroup{
    private Swerve swerve;
    private TelescopicArm arm;
    private Intake intake;
    private Pivot pivot;
    private AutoChooser autoChooser;
    private double backupTime;
    
    private double pivotVal;
    private double armVal;
    
    public AutoCommand(Swerve s, TelescopicArm a, Intake i, Pivot p){

        autoChooser = new AutoChooser();
        swerve = s;
        intake = i;
        pivot = p;
        arm = a;

        if(autoChooser.getSelectedAuto().contains("No_Backup")){
            this.backupTime = 0;
        }
        else{
            this.backupTime = Constants.Auto.backupTime;
        }

        if(autoChooser.getSelectedAuto() == "" || autoChooser.getSelectedAuto() == "Nothing"){
            new WaitCommand(15);
         }
         else if(autoChooser.getSelectedAuto().contains("High_Cone")){
              pivotVal = Constants.Auto.pivotTopConePos;
              armVal = Constants.Auto.armTopConePos;
         }
         else if(autoChooser.getSelectedAuto().contains("High_Cube")){
             pivotVal = Constants.Auto.pivotTopCubePos;
             armVal = Constants.Auto.armTopCubePos;
         }
         else if(autoChooser.getSelectedAuto().contains("Mid_Cone")){
             pivotVal = Constants.Auto.pivotMidConePos;
             armVal = Constants.Auto.armMidConePos;
         }
         else if(autoChooser.getSelectedAuto().contains("Mid_Cube")){
             pivotVal = Constants.Auto.pivotMidCubePos;
             armVal = Constants.Auto.armMidCubePos;
         }

        addCommands(
            new AutoPivot(pivot, pivotVal, Constants.Auto.pivotSpeed),
            new AutoMoveArm(arm, armVal, Constants.Auto.armSpeed),
            new AutoMoveIntake(intake, Constants.Auto.intakeSpeed),
            new AutoBackup(swerve, this.backupTime)
        );
        addRequirements(s, a, i);
    }


    @Override
    public void initialize(){

    }

   
    @Override
    public void execute(){
       
    }


}
