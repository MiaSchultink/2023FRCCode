package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.ctre.phoenix.sensors.SensorTimeBase;

public final class CTREConfigs {
    public TalonFXConfiguration swerveAngleFXConfig;
    public TalonFXConfiguration swerveDriveFXConfig;
    public CANCoderConfiguration swerveCanCoderConfig;


    public CTREConfigs(){
        swerveAngleFXConfig = new TalonFXConfiguration();
        swerveDriveFXConfig = new TalonFXConfiguration();
        swerveCanCoderConfig = new CANCoderConfiguration();

    /* Swerve Angle Motor Configurations */
    SupplyCurrentLimitConfiguration angleSupplyLimit = new SupplyCurrentLimitConfiguration(
        Constants.Swerve.angleEnableCurrentLimit, 
        Constants.Swerve.angleContinuousCurrentLimit, 
        Constants.Swerve.anglePeakCurrentLimit, 
        Constants.Swerve.anglePeakCurrentDuration);
    swerveAngleFXConfig.supplyCurrLimit = angleSupplyLimit;
    swerveAngleFXConfig.initializationStrategy = SensorInitializationStrategy.BootToZero;
    
    SupplyCurrentLimitConfiguration driveSupplyLimit = new SupplyCurrentLimitConfiguration(
        Constants.Swerve.driveEnableCurrentLimit, 
        Constants.Swerve.driveContinuousCurrentLimit, 
        Constants.Swerve.drivePeakCurrentLimit, 
        Constants.Swerve.drivePeakCurrentDuration);
    
    }
}

