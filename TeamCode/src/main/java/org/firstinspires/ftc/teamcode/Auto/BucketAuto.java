package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@Autonomous(name = "2BucketAuto")
public class BucketAuto extends LinearOpMode {

    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;

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
    public void bucketScore () {
        setArmPos(1400);
        pivot.setPosition(0.5);
        slides.setTargetPosition(1700);
    }
    public void openClaw () {
        mainIntake.setPosition(0.7);
    }
    public void closeClaw () {
        mainIntake.setPosition(0.05);
    }
    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {


        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        pivot = hardwareMap.get(Servo.class, "goBildaPivot");
        rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
        leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");
        ascentServo = hardwareMap.get(Servo.class, "revAscent");

        leftIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0.75);
        leftIntakeArm.setPower(0.75);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Pose2d beginPose = new Pose2d(-45, -55, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        TrajectoryActionBuilder scoreBasket = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(-72, -45), Math.toRadians(-135));

        TrajectoryActionBuilder sampleGrab = scoreBasket.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-62, -24), Math.toRadians(90));

        TrajectoryActionBuilder scoreSecondBasket = sampleGrab.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-72, -45), Math.toRadians(-135));

        TrajectoryActionBuilder secondSampleGrab = scoreSecondBasket.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(-75, -24), Math.toRadians(90));

        Action parkAscent = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(-55, 5), Math.toRadians(180))
                .strafeTo(new Vector2d(-30, 5))
                .build();

        Action firstScore = scoreBasket.build();
        Action firstGrab = sampleGrab.build();
        Action secondScore = scoreSecondBasket.build();
        Action mean = secondSampleGrab.build();


        waitForStart();



        //        Actions.runBlocking(fullRoute);
        closeClaw();
        sleep(400);
        bucketScore();
        sleep(1500);
        Actions.runBlocking(firstScore);
        ascentServo.setPosition(0.55);
        sleep(500);
        openClaw();
        sleep(500);
        Actions.runBlocking(firstGrab);
        sleep(500);
        slides.setTargetPosition(100);
        pivot.setPosition(0.9);
        sleep(500);
        setArmPos(300);
        sleep(1000);
        closeClaw();
        sleep(400);
        bucketScore();
//        sleep(300);
        Actions.runBlocking(secondScore);
        sleep(200);
        openClaw();
        sleep(500);
        Actions.runBlocking(mean);
//        sleep(500);
        slides.setTargetPosition(100);
        pivot.setPosition(0.9);
        sleep(500);
        setArmPos(300);
        pivot.setPosition(0);
//        sleep(500);
//        slides.setTargetPosition(0);
//        Actions.runBlocking(parkAscent);
//        setArmPos(0);
//        sleep(1000);

    }
}

