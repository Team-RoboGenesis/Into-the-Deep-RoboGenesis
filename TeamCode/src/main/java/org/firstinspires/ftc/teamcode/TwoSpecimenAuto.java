package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "2SpecAuto")
public class TwoSpecimenAuto extends LinearOpMode {

    //motors and sensors
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;

    //constants
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
        setArmPos(350);
        pivot.setPosition(0.3);
        sleep(400);
        closeClaw();
        sleep(100);
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
     *
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
        Action firstSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .splineTo(new Vector2d(6, -22), Math.toRadians(90))
                .build();
        Action clearSubmersible = drive.actionBuilder(drive.pose)
                .strafeToConstantHeading(new Vector2d(6, -40))
                .build();
        Action secondSpecimenGrab = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(56, -40), Math.toRadians(-90))
                .strafeTo(new Vector2d(56, -47))
                .build();
        Action firstSamplePush = drive.actionBuilder(drive.pose)
//                .strafeTo(new Vector2d(46, -40))
                .splineToConstantHeading(new Vector2d(6, -46), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(46, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.3)
                .splineTo(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -21))
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();
        waitForStart();

        scoreSpecimen();
        Actions.runBlocking(firstSpecimenScore);
        openClaw();
        sleep(200);
        pivotMiddlePos();
        slides.setTargetPosition(0);
        sleep(200);
        Actions.runBlocking(clearSubmersible);
        setArmPos(355);
        pivot.setPosition(0.3);
        Actions.runBlocking(secondSpecimenGrab);
        sleep(200);
        closeClaw();
        sleep(500);
        scoreSpecimen();
        Actions.runBlocking(secondSpecimenScore);
        sleep(300);
        openClaw();
        pivotMiddlePos();
        Actions.runBlocking(firstSamplePush);
        sleep(500);

//        Actions.runBlocking(parkObservation);
    }
}
