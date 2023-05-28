package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase {
    private CANSparkMax pivotMotor;
    private CANSparkMax slave_pivotMotor;
    private DutyCycleEncoder pivotEncoder;
    XboxController copilot;

    public static Pivot instance;

    public Pivot(){
        pivotMotor = new CANSparkMax(Constants.Pivot.mainPivotMotorID, MotorType.kBrushless);
        slave_pivotMotor = new CANSparkMax(Constants.Pivot.slavePivotMotorID, MotorType.kBrushless);
        pivotEncoder = new DutyCycleEncoder(Constants.Pivot.pivotEncoderChannel);
        copilot = new XboxController(Constants.Cotnrollers.copilotPort);

        pivotMotor.restoreFactoryDefaults();
        pivotMotor.setInverted(false);
        pivotMotor.setSmartCurrentLimit(40);
        pivotMotor.setIdleMode(IdleMode.kBrake);

        slave_pivotMotor.restoreFactoryDefaults();
        slave_pivotMotor.setInverted(false);
        slave_pivotMotor.setSmartCurrentLimit(40);
        slave_pivotMotor.setIdleMode(IdleMode.kBrake);

        zeroPivot();
    
    }

    @Override
    public void periodic(){
        if(this.getPivotEncoderValue() > Constants.Pivot.encoderMinValue &&
        this.getPivotEncoderValue() < Constants.Pivot.encoderMaxValue){
            movePivot(copilot.getRightY());
        }
        if(pivotMotor.getEncoder().getVelocity() < Constants.Pivot.minMoveSpeed){
            this.stop();
        }
        Shuffleboard.getTab("Karma").add("Pivot Encoder", this.getPivotEncoderValue());
    }
    public void movePivot(double speed){
        pivotMotor.set(speed);
        slave_pivotMotor.set(speed);
    }

    public void zeroPivot(){
        pivotEncoder.reset();
    }

    public double getPivotEncoderValue(){
        return pivotEncoder.getAbsolutePosition();
    }

    public void stop(){
        pivotMotor.set(0);
        slave_pivotMotor.set(0);
    }

    public static Pivot getInstance(){
        Pivot inst = null;
        if(instance == null){
            inst = new Pivot();
        }
        else{
            inst = instance;
        }
        return inst;
    }

}
