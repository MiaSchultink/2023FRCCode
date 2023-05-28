package frc.robot.subsystems;

import org.apache.commons.lang3.Conversion;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Conversions;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {
    NetworkTable table;
    NetworkTableEntry tx; //x offset
    NetworkTableEntry ty; //y offset
    NetworkTableEntry ta; //target area

    public Limelight(){
      table =  NetworkTableInstance.getDefault().getTable("limelight");
      tx = table.getEntry("tx");
      ty = table.getEntry("ty");
      ta = table.getEntry("ta");
    }

   public double getTx(){
       return tx.getDouble(0.0);
   }
   public double getTy(){
       return ty.getDouble(0.0);
   }

   public double getTa(){
       return ta.getDouble(0.0);
   }

   @Override
   public void periodic(){
       Shuffleboard.getTab("Limelight").add("tx", this.getTx());
       Shuffleboard.getTab("Limelight").add("ty", this.getTy());
       Shuffleboard.getTab("Limelight").add("ta", this.getTa());
   }

   public double getDistanceToTarget(double targetHeight){
       double verticalOffset = this.getTy();
       double degAngleToGoal = Constants.Limelight.limelightAngle + verticalOffset;
       double radToGoal = Conversions.degToRad(degAngleToGoal);

       double distance = (targetHeight - Constants.Limelight.limelightHeight)/Math.tan(radToGoal);
       return distance;

   }
   
   public boolean hasTarget(){
       return this.getTa()>0.0;
   }

}
