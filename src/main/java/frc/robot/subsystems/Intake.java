package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.hal.MatchInfoData;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private CANSparkMax intakeMotor;
    private CANSparkMax chinMotor;
    private DigitalInput limitSwitch1;
    private DigitalInput limitSwitch2;
    private Relay intakeRelay;
    XboxController copilot;

    public Intake(){
        intakeMotor = new CANSparkMax(Constants.Intake.intakeMotorID, MotorType.kBrushless);
        chinMotor = new CANSparkMax(Constants.Intake.chinMotorID, MotorType.kBrushless);
        limitSwitch1 = new DigitalInput(Constants.Intake.limitSwitch1Channel);
        limitSwitch2 = new DigitalInput(Constants.Intake.limitSwitch2Channel);
        intakeRelay = new Relay(Constants.Intake.relayChannel);
        copilot = new XboxController(Constants.Cotnrollers.copilotPort);

        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setInverted(false);
        intakeMotor.setSmartCurrentLimit(40);
        intakeMotor.setIdleMode(IdleMode.kCoast);

        chinMotor.restoreFactoryDefaults();
        chinMotor.setInverted(false);
        chinMotor.setSmartCurrentLimit(40);
        chinMotor.setIdleMode(IdleMode.kCoast);
    }

    @Override
    public void periodic(){
        if(limitSwitch1.get() || limitSwitch2.get()){
            this.stopIntake();
            intakeRelay.set(Value.kOn);
        }
        else{
            intakeRelay.set(Value.kOff);
           if(copilot.getRightBumper()){
               moveIntake(0.3);
           }
           else if(copilot.getLeftBumper()){
               moveIntake(-0.3);
           }
        }
        
        if (copilot.getAButton()){
            moveChin(0.3);
        }
        else if(copilot.getYButton()){
            moveChin(-0.3);
        }

        Shuffleboard.getTab("Karma").add("Intake Motor Speed", intakeMotor.getEncoder().getVelocity());
        Shuffleboard.getTab("Karma").add("Chin Position", chinMotor.getEncoder().getPosition());
        
        }

    public void moveIntake(double speed){
        intakeMotor.set(speed);
    }

    public void moveChin(double speed){
        chinMotor.set(speed);
    }

    public void stopIntake(){
        intakeMotor.set(0);
    }

}
