package frc.robot.commands;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.fasterxml.jackson.databind.node.IntNode;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;

public class VisionProcessing extends CommandBase {

    private NetworkTableInstance networkTable = NetworkTableInstance.getDefault();
    private PhotonCamera intakeCam;
    private Swerve swerve;
    private AprilTagFieldLayout aprilTagLayout;
    private PhotonPoseEstimator poseEstimator;
    private Transform3d robotToCamera;

    public VisionProcessing() throws IOException {
        intakeCam = new PhotonCamera(networkTable, "intakecamera");
        swerve = new Swerve();
        aprilTagLayout = AprilTagFields.k2023ChargedUp.loadAprilTagLayoutField();
        robotToCamera = new Transform3d(new Translation3d(Constants.PhotonVision.cameraHeight, 0,
                Constants.PhotonVision.cameraHorizontalOffset), new Rotation3d());
        poseEstimator = new PhotonPoseEstimator(aprilTagLayout,
                PoseStrategy.AVERAGE_BEST_TARGETS,
                intakeCam,
                robotToCamera);
    }


    public PhotonPipelineResult getLatestResult(PhotonCamera cam) {
        return cam.getLatestResult();
    }

    public List<PhotonTrackedTarget> getTargets() {
        PhotonPipelineResult result = this.getLatestResult(intakeCam);
        List<PhotonTrackedTarget> targets = result.getTargets();
        return targets;
    }

    public PhotonTrackedTarget getBestTarget() {
        PhotonPipelineResult result = this.getLatestResult(intakeCam);
        return result.getBestTarget();
    }

    public double getDistanceToTargetWithPitch() {
        PhotonPipelineResult result = this.getLatestResult(intakeCam);
        double distance = -1;
        if (result.hasTargets()) {
            double range = PhotonUtils.calculateDistanceToTargetMeters(Constants.PhotonVision.cameraHeight,
                    Constants.PhotonVision.targetHeight,
                    Constants.PhotonVision.cameraPitch,
                    Units.degreesToRadians(result.getBestTarget().getPitch()));
            distance = range;
        }
        return distance;
    }

    public double getDistanceToTargetWithPoses(Pose2d targetPose) {
        Pose2d currnet = Swerve.getRobotPose();
        double distance = PhotonUtils.getDistanceToPose(currnet, targetPose);
        return distance;
    }

    public Translation2d getTranslationToTarget(Pose2d targetPose) {
        PhotonTrackedTarget target = this.getBestTarget();
        return PhotonUtils.estimateCameraToTargetTranslation(
                this.getDistanceToTargetWithPoses(targetPose), Rotation2d.fromDegrees(-target.getYaw()));
    }

    public Pose2d getFieldRobotPoseTraditional(Pose2d targetPose) {
        PhotonTrackedTarget target = this.getBestTarget();
        Transform2d pivotCamPos = new Transform2d(
                new Translation2d(70.9, 23.4),
                swerve.getGyroAngle());
        Pose2d fieldPose = PhotonUtils.estimateFieldToRobot(
                Constants.PhotonVision.cameraHeight,
                Constants.PhotonVision.targetHeight,
                Constants.PhotonVision.cameraPitch,
                Units.degreesToRadians(target.getPitch()),
                Rotation2d.fromDegrees(-target.getYaw()),
                swerve.getGyroAngle(),
                targetPose,
                pivotCamPos);
        return fieldPose;
    }

    public Pose3d getFieldPoseWithAprilTag() {
        PhotonTrackedTarget target = this.getBestTarget();
        Transform3d camToTarget = target.getBestCameraToTarget();
        Pose3d cameraToRobot = aprilTagLayout.getTagPose(target.getFiducialId()).get();
        Pose3d robotPose = PhotonUtils.estimateFieldToRobotAprilTag(target.getBestCameraToTarget(), cameraToRobot,
                camToTarget);
        return robotPose;
    }

    public Rotation2d getYawToTarget(Pose2d targetPose) {
        Pose2d robotPose = this.getFieldRobotPoseTraditional(targetPose);
        Rotation2d yawToTarget = PhotonUtils.getYawToPose(robotPose, targetPose);
        return yawToTarget;
    }

    public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
        poseEstimator.setReferencePose(prevEstimatedRobotPose);
        Optional<EstimatedRobotPose> pose  = poseEstimator.update();
        return pose;
    }

    public void setCameraToDriverMode(){
       if(!intakeCam.getDriverMode()){
        intakeCam.setDriverMode(true);
       }
    }

    public void setCameraPipelineIndex(int index){
        intakeCam.setPipelineIndex(index);
    }

    public void controlLED(){
        intakeCam.setLED(VisionLEDMode.kBlink);
    }
    

    public void moveToTarget(){
        double distance = this.getDistanceToTargetWithPitch();
        DriveDistance driveDistance = new DriveDistance(swerve, this, distance);
        driveDistance.execute();
    }

}
