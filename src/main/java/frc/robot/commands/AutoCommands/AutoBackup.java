package frc.robot.commands.AutoCommands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.math.Conversions;
import frc.robot.subsystems.Swerve;

public class AutoBackup extends CommandBase{
   private Swerve swerve;
   private double time;
   
   public AutoBackup(Swerve s, double t){
       swerve = s;
       time = t;
   }


   @Override
   public void initialize(){
       
   }

   public void driveForTime(double seconds){
       long currentTime = System.currentTimeMillis();
       long miliTime = Conversions.secondsToMili(seconds);
       
       if(System.currentTimeMillis() < currentTime+miliTime){
           swerve.drive(new Translation2d(), 0, true);
       }
       else{
           swerve.stop();
       }

   }

   @Override
   public void execute(){
       this.driveForTime(this.time);
   }

   @Override
   public boolean isFinished(){
       return false;
   }
}
