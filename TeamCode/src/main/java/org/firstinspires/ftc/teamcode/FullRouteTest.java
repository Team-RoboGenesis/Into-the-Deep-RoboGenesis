package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Auto.AutoActionController;

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
    int grabDistance = 30;
    int maxColor = 500;
    int armLimit = 3400;

    public void setArmPos(int position) {
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
        } else if (position > armLimit) {
            rightIntakeArm.setTargetPosition(armLimit);
            leftIntakeArm.setTargetPosition(armLimit);
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

        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action fullRoute = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(6, -23), Math.toRadians(90))
                .stopAndAdd(this::openClaw)
                .stopAndAdd(this::pivotMiddlePos)
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))
                .stopAndAdd(this::resetArm)
                .splineToConstantHeading(new Vector2d(40, -40), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45, -0),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -45), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, -5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(63, -5), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(60, -48), Math.toRadians(-90))
                .stopAndAdd(this::wallGrab)
                .waitSeconds(0.3)
                .stopAndAdd(this::scoreSpecimen)
                .splineTo(new Vector2d(60, -40), Math.toRadians(180))
                .splineTo(new Vector2d(5, -45), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(5, -25), Math.toRadians(90))
                .stopAndAdd(this::openClaw)
                .stopAndAdd(this::pivotMiddlePos)

                        .build();
        waitForStart();

        scoreSpecimen();
        Actions.runBlocking(fullRoute);
    }
}
