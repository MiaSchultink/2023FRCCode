package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Conversions;
import frc.lib.util.CTREModuleState;
import frc.lib.util.SwerveModuleConstants;
import frc.robot.Constants;
import frc.robot.Robot;

public class SwerveModule extends SubsystemBase{
    public int moduleNumber;
    private double offsetAngle;
    private TalonFX driveMotor;
    private TalonFX turnMotor;
    private CANCoder canCoder; //angle encoder - covered
    private double lastAngle;

    public SwerveModule(int mNumber, SwerveModuleConstants consts){
        mNumber = moduleNumber;
    
        driveMotor = new TalonFX(consts.driveMotorID);
        //configure
        turnMotor = new TalonFX(consts.angleMotorID);
        //configure

        offsetAngle = consts.angleOffset;
        canCoder = new CANCoder(consts.cancoderID);
        //configure

        lastAngle = getState().angle.getDegrees();
    }

    @Override
    public void periodic(){
        String driveTitle = this.moduleNumber + " drive vel";
        String turnTitle = this.moduleNumber + " turn pos";
        String canCoderTitle = this.moduleNumber + " encoder val";

        Shuffleboard.getTab("Swerve").add(canCoderTitle, this.getCanCoderValue());
        Shuffleboard.getTab("Swerve").add(driveTitle, driveMotor.getSelectedSensorVelocity());
        Shuffleboard.getTab("Swerve").add(turnTitle, turnMotor.getSelectedSensorPosition());
    }

    public void setDesiredState(SwerveModuleState desiredState){
        desiredState = CTREModuleState.optimize(desiredState, this.getState().angle);
        double desiredPrecentOutput = desiredState.speedMetersPerSecond/Constants.Swerve.maxSpeed;
        double desiredAngle = (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01)) ? lastAngle : desiredState.angle.getDegrees();
        double angleInDeg = Conversions.degreesToFalcon(desiredAngle, Constants.Swerve.angleGearRatio);
        this.driveMotor.set(ControlMode.PercentOutput, desiredPrecentOutput);
        this.turnMotor.set(ControlMode.Position, angleInDeg);
        lastAngle = angleInDeg;
    }

    public SwerveModuleState getState(){ //state is funtion of velocity and angle
        double velocity = Conversions.falconToMPS(driveMotor.getSelectedSensorVelocity(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio);
        Rotation2d angle = Rotation2d.fromDegrees(Conversions.falconToDegrees(turnMotor.getSelectedSensorPosition(), Constants.Swerve.angleGearRatio));
        SwerveModuleState state = new SwerveModuleState(velocity, angle);
        return state;
    }

    private void resetToAbsolute(){
        double absolutePosition = Conversions.degreesToFalcon(getCanCoderValue().getDegrees() - this.offsetAngle, Constants.Swerve.angleGearRatio);
        turnMotor.setSelectedSensorPosition(absolutePosition);
    }

    private Rotation2d getCanCoderValue(){
        Rotation2d val = Rotation2d.fromDegrees(canCoder.getAbsolutePosition());
        return val;
    }

    private void configureTurnMotor(){
        turnMotor.configFactoryDefault();
        turnMotor.configAllSettings(Robot.ctreConfigs.swerveAngleFXConfig);
        turnMotor.setInverted(false);
        turnMotor.setNeutralMode(NeutralMode.Coast);
        resetToAbsolute();
    }

    private void configureDriveMotor(){
       driveMotor.configFactoryDefault();
       driveMotor.configAllSettings(Robot.ctreConfigs.swerveDriveFXConfig);
       driveMotor.setNeutralMode(NeutralMode.Coast);
       if(driveMotor.getDeviceID() == 2 || driveMotor.getDeviceID() == 4){
           driveMotor.setInverted(true);
       }
       else{
           driveMotor.setInverted(false);
       }
    }

    private Rotation2d getAngle(){
        return Rotation2d.fromDegrees(Conversions.falconToDegrees(turnMotor.getSelectedSensorPosition(), Constants.Swerve.angleGearRatio));
    }

    // public SwerveModulePosition getPosition(){
    //     return new SwerveModulePosition(
    //         Conversions.falconToMeters(mDriveMotor.getSelectedSensorPosition(), Constants.Swerve.wheelCircumference, Constants.Swerve.driveGearRatio), 
    //         getAngle());
    // }

    public void stop(){
        driveMotor.set(ControlMode.PercentOutput, 0);
        turnMotor.set(ControlMode.PercentOutput, 0);
    }
}
