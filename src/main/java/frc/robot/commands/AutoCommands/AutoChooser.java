package frc.robot.commands.AutoCommands;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoChooser extends CommandBase {
    static AutoChooser instance;
    SendableChooser<String> chooser;

    
    public AutoChooser(){
        chooser = new SendableChooser<>();
        chooser.setDefaultOption("Nothing", "Nothing");
        
        chooser.addOption("Top Cube Backup", "Top_Cube_Backup");
        chooser.addOption("Top Cone Backup", "Top_Cone _Backup");
        chooser.addOption("Mid Cube Backup", "Mid_Cube_Backup");
        chooser.addOption("Mid Cone Backup", "Mid_Cone_Backup");
        chooser.addOption("Top Cube No Backup", "Top_Cube_No_Backup");
        chooser.addOption("Top Cone No Backup", "Top_Cone_No_Backup");
        chooser.addOption("Mid Cube No Backup", "Mid_Cube_No_Backup");
        chooser.addOption("Mid Cone No Backup", "Mid_Cone_No_Backup");

        Shuffleboard.getTab("Karma").add(chooser);
    }

    public AutoChooser getInstance(){
        AutoChooser retVal = null;
        if(instance == null){
            retVal = new AutoChooser();
        }
        else{
            retVal = instance;
        }
        return retVal;
    }

    public String getSelectedAuto(){
        return chooser.getSelected();
    }
}
