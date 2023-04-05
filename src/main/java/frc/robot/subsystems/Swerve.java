package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Swerve extends SubsystemBase {
    
    private SwerveModule [] swerveModules;
    private AHRS navx;
    static Swerve instance = new Swerve();

    public Swerve(){
        swerveModules = new SwerveModule[]{
            new SwerveModule(0, Constants.Swerve.Mod1.consts),
            new SwerveModule(1, Constants.Swerve.Mod2.consts),
            new SwerveModule(2, Constants.Swerve.Mod3.consts),
            new SwerveModule(3, Constants.Swerve.Mod4.consts)
        };
        navx = new AHRS(SPI.Port.kMXP);
        this.zeroGyro();
    }

    @Override
    public void periodic(){
        
    }
    public void drive(Translation2d translation, double rotation, boolean fieldOriented){
        ChassisSpeeds speeds = null;
        if(fieldOriented){
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation, getGyroAngle());
        }
        else{
            speeds = new ChassisSpeeds(translation.getX(), translation.getY(), rotation);
        }
        SwerveModuleState [] swerveModuleStates = Constants.Swerve.kinematics.toSwerveModuleStates(speeds);
        
        for(SwerveModule mod: swerveModules){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber]);
        }
       
    }

    public void zeroGyro(){
        navx.reset();
    }
    public Rotation2d getGyroAngle(){ // z axis rotation
       return Rotation2d.fromDegrees(360 - navx.getYaw());
        
    }

    public Rotation2d getRoll(){ 
        return Rotation2d.fromDegrees(navx.getRoll());
    }
    public Rotation2d getPitch(){
        return Rotation2d.fromDegrees(navx.getPitch());
    }

    public void setModuleStates(SwerveModuleState [] desiredStates){
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, Constants.Swerve.maxSpeed);
        for(SwerveModule mod : swerveModules){
            mod.setDesiredState(desiredStates[mod.moduleNumber]); ;//set desired auto state originally
        }
    }

    public SwerveModuleState[] getStates(){
        SwerveModuleState[] states = new SwerveModuleState[4];
        for(SwerveModule mod : swerveModules){
            states[mod.moduleNumber] = mod.getState();
        }
        return states;
    }

    // public SwerveModulePosition[] getModulePositions(){
    //     SwerveModulePosition[] positions = new SwerveModulePosition[4];
    //     for(SwerveModule mod : mSwerveMods){
    //         positions[mod.moduleNumber] = mod.getPosition();
    //     }
    //     return positions;
    // }
    public void setModuleRoation(Rotation2d rotation){
        for(SwerveModule mod: swerveModules){
            mod.setDesiredState(new SwerveModuleState(0, rotation));
        }
    }
    public SwerveModule[] getModules(){
        return this.swerveModules;
    }

    public AHRS getAhrs(){
        return navx;
    }
    public void stop(){        
        for(SwerveModule mod : swerveModules){
            mod.stop();
        }
    }
}
