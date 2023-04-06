package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TelescopicArm extends SubsystemBase {
    
    private final CANSparkMax extendMotor;
    XboxController coplilot;
    
    public TelescopicArm(){
        extendMotor = new CANSparkMax(Constants.Arm.extendMotorId, MotorType.kBrushless);
        coplilot = new XboxController(Constants.Cotnrollers.copilotPort);

        extendMotor.restoreFactoryDefaults();
        extendMotor.setInverted(false);
        extendMotor.setSmartCurrentLimit(40);
        extendMotor.setIdleMode(IdleMode.kBrake);
    }



    @ Override
    public void periodic(){
        if(this.getArmEncoderValue() > Constants.Arm.maxRetraction && this.getArmEncoderValue() < Constants.Arm.maxExtention){
            moveArm(coplilot.getRightY());
        }
        Shuffleboard.getTab("Karma").add("Arm Encoder Value", this.getArmEncoderValue());
    }

    public void moveArm(double speed){
       extendMotor.set(speed);
    }
    
    public double getArmEncoderValue(){
        return extendMotor.getEncoder().getPosition();
    }
}
