
package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.lib.util.SwerveModuleConstants;

public final class Constants {

    public static final class Swerve {

        public static final double trackWidth = 0.517;
        public static final double wheelBase = 0.517;
        public static final double wheelDiameter = Units.inchesToMeters(4);
        public static final double wheelCircumference = wheelDiameter * Math.PI;

        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        public static final double driveGearRatio = (8.14 / 1.0); //6.86:1
        public static final double angleGearRatio = (12.8 / 1.0); //12.8:1

        public static double maxSpeed = 6.4531;
        
        public static final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0), //FR
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0), //BR
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0), //FL
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0) // BL
        ); 

        public static final int angleContinuousCurrentLimit = 25;
        public static final int anglePeakCurrentLimit = 40;
        public static final double anglePeakCurrentDuration = 0.1;
        public static final boolean angleEnableCurrentLimit = true;

        public static final int driveContinuousCurrentLimit = 35;
        public static final int drivePeakCurrentLimit = 60;
        public static final double drivePeakCurrentDuration = 0.1;
        public static final boolean driveEnableCurrentLimit = true;


        public static final class Mod1 {
            public static final int driveMotorId = 1;
            public static final int turnMotorId = 7;
            public static final double offset = 45.2322;
            public static final int CANcoderId = 3;

            public static final SwerveModuleConstants consts = new SwerveModuleConstants(driveMotorId, turnMotorId,
                    CANcoderId, offset);
        }

        public static final class Mod2 {
            public static final int driveMotorId = 1;
            public static final int turnMotorId = 7;
            public static final double offset = 45.2322;
            public static final int CANcoderId = 3;

            public static final SwerveModuleConstants consts = new SwerveModuleConstants(driveMotorId, turnMotorId,
                    CANcoderId, offset);
        }

        public static final class Mod3 {
            public static final int driveMotorId = 1;
            public static final int turnMotorId = 7;
            public static final double offset = 45.2322;
            public static final int CANcoderId = 3;

            public static final SwerveModuleConstants consts = new SwerveModuleConstants(driveMotorId, turnMotorId,
                    CANcoderId, offset);
        }

        public static final class Mod4 {
            public static final int driveMotorId = 1;
            public static final int turnMotorId = 7;
            public static final double offset = 45.2322;
            public static final int CANcoderId = 3;

            public static final SwerveModuleConstants consts = new SwerveModuleConstants(driveMotorId, turnMotorId,
                    CANcoderId, offset);
        }

    }

    public static final class Arm{
        public static final int extendMotorId = 24;
        public static final double maxSpeed = 6.34345;
        public static final double maxExtention = 113.0;
        public static final double maxRetraction = 25.6;
    }
    public static final class Intake{
        public static final int intakeMotorID = 34;
        public static final int chinMotorID = 54;
        public static final int limitSwitch1Channel = 1;
        public static final int limitSwitch2Channel = 2;
        public static final int relayChannel = 3;
    }
    public static final class Pivot{
        public static final int mainPivotMotorID = 2;
        public static final int slavePivotMotorID = 3;
        public static final int pivotEncoderChannel = 0;
        public static final double encoderMaxValue = 345.5;
        public static final double encoderMinValue =-234.4;
        public static final double minMoveSpeed = 0.04;
    }

    public static final class Cotnrollers{
        public static final int copilotPort = 3;
    }

}
