package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class DriveDistance extends CommandBase {
    private Swerve swerve;
    private VisionProcessing vision;
    private PIDController pid;
    private double distance;

    public DriveDistance(Swerve s, VisionProcessing v, double distance) {
        swerve = s;
        vision = v;
        pid = new PIDController(1, 1, 1);
    }


    @Override
    public void execute() {
        Pose2d targetPose = new Pose2d(new Translation2d(distance, swerve.getGyroAngle()), swerve.getGyroAngle());
        Pose2d robotPose = vision.getFieldRobotPoseTraditional(targetPose);
        Translation2d translation2d = robotPose.getTranslation();
        double angle = swerve.getGyroAngle().getDegrees();

        if (!robotPose.equals(targetPose)) {
            swerve.drive(translation2d, angle, true);
        }

    }
}
