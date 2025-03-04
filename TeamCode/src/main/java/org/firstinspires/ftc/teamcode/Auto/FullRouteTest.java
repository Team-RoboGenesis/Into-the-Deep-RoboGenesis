package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "FullRoute")
public class FullRouteTest extends LinearOpMode {

    //motors and sensors
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;

    //constants
    int ARM_LIMIT = 3400;

    public void setArmPos(int position) {
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
        } else if (position > ARM_LIMIT) {
            rightIntakeArm.setTargetPosition(ARM_LIMIT);
            leftIntakeArm.setTargetPosition(ARM_LIMIT);
        } else {
            rightIntakeArm.setTargetPosition(position);
            leftIntakeArm.setTargetPosition(position);
        }
    }
    //functions
    public void scoreSpecimen() {
        closeClaw();
        sleep(100);
        setArmPos(710);
        pivot.setPosition(0.1);
        slides.setTargetPosition(545);
    }
    public void wallGrab() {
        slides.setTargetPosition(0);
        setArmPos(355);
        pivot.setPosition(0.3);
        sleep(1000);
        closeClaw();
        sleep(100);
    }
    public void resetArm() {
        setArmPos(0);
        slides.setTargetPosition(0);
        pivot.setPosition(0);
        openClaw();
    }
    public void openClaw () {
        mainIntake.setPosition(0.7);
    }
    public void closeClaw () {
        mainIntake.setPosition(0.05);
    }
    public void pivotMiddlePos () {
        pivot.setPosition(0.4);
    }
    public void pivotTopPos () {
        pivot.setPosition(0.1);
    }
    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        //configuration
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        pivot = hardwareMap.get(Servo.class, "goBildaPivot");
        rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
        leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");
        ascentServo = hardwareMap.get(Servo.class, "revAscent");

        leftIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

//        ftcLib blocks
        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0.9);
        leftIntakeArm.setPower(0.9);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        VelConstraint baseVelConstraint = (robotPose, _path, _disp) -> {
            if (robotPose.position.x.value() > 50.0) {
                return 20.0;
            } else {
                return 50.0;
            }
        };

        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        TrajectoryActionBuilder firstSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(6, -22), Math.toRadians(90));

        TrajectoryActionBuilder fullRoute = firstSpecimenScore.endTrajectory().fresh()
                .waitSeconds(1)
                .strafeTo(new Vector2d(6, -22))
                .stopAndAdd(this::openClaw)
                .stopAndAdd(this::pivotMiddlePos)
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))
                .stopAndAdd(this::resetArm)
                //first sample push
                .splineToConstantHeading(new Vector2d(40, -40), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45, -0),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -45), Math.toRadians(90))
                //second sample push
                .splineToConstantHeading(new Vector2d(58, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, -5), Math.toRadians(90), null,
                        new ProfileAccelConstraint(-5, 5))
                .strafeToLinearHeading(new Vector2d(65, -5), 0, null,
                        new ProfileAccelConstraint(-10, 10))
                .setTangent(Math.toRadians(-90))
//                .stopAndAdd(this::wallGrab)
//                .stopAndAdd(this::openClaw)
                .splineToConstantHeading(new Vector2d(60, -53), 0)
                .setTangent(0)
                //Score second specimen
                .stopAndAdd(this::closeClaw)
                .waitSeconds(0.3)
                .stopAndAdd(this::scoreSpecimen)
                .splineTo(new Vector2d(60, -40), Math.toRadians(180))
                .splineTo(new Vector2d(5, -45), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(5, -25), Math.toRadians(90))
                .stopAndAdd(this::openClaw)
                .stopAndAdd(this::pivotMiddlePos);

        Action firstSpecimen = firstSpecimenScore.build();
        Action FullRoute = fullRoute.build();

        waitForStart();

//        Actions.runBlocking(
//                new SequentialAction(
//                        controllerOfActions.scoreSpecimen(),
//                        firstSpecimen
//                )
//        );
        scoreSpecimen();
        Actions.runBlocking(FullRoute);
    }
}
