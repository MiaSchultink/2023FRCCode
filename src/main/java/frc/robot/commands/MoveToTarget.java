package frc.robot.commands;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Swerve;

public class MoveToTarget extends CommandBase {
    private Swerve swerve;
    private VisionProcessing vision;

    private CANSparkMax randomMotor;

    public MoveToTarget(){
        randomMotor = new CANSparkMax(34, MotorType.kBrushless);
    }

    @Override
    public void execute(){
        if(vision.getTargets().size()>0){
            randomMotor.set(0.2);
        }
    };

}
