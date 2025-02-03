package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "SpecimenAuto")
public class specimenAuto extends LinearOpMode {

    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;
    ColorRangeSensor color;

    public void setArmPos(int position) {
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
        } else if (position > 2713) {
            rightIntakeArm.setTargetPosition(2713);
            leftIntakeArm.setTargetPosition(2713);
        }
        else {
            rightIntakeArm.setTargetPosition(position);
            leftIntakeArm.setTargetPosition(position);
        }
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
        color = hardwareMap.get(ColorRangeSensor.class, "color");

        leftIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(1);
        leftIntakeArm.setPower(1);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action specimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .lineToY(-24)
                .build();
        Action clearSubmersible = drive.actionBuilder(drive.pose)
                .lineToY(-46)
                .build();
        Action firstSamplePush = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(46, -40))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))//move first sample into observation
                .strafeTo(new Vector2d(55, -46.5))
                .turn(Math.toRadians(-180))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.3)
                .splineTo(new Vector2d(30, -45), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -40))
//                .splineTo(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -24))
                .build();
        Action thirdSpecimenGrab = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(55, -46.8), Math.toRadians(-90))//score third specimen
                .build();
        Action thirdSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(30, -45), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -40))
//                .splineTo(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -24))
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();
        waitForStart();

        mainIntake.setPosition(0.05);
        sleep(100);
        setArmPos(740);
        pivot.setPosition(0.1);
        slides.setTargetPosition(500);
        sleep(300);
        Actions.runBlocking(specimenScore);
        mainIntake.setPosition(0.7);
        sleep(200);
        pivot.setPosition(0.4);
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
        pivot.setPosition(0.1);
        setArmPos(405);
        sleep(200);
//        Actions.runBlocking(firstSamplePushV2);
        Actions.runBlocking(firstSamplePush);
        slides.setTargetPosition(0);
        pivot.setPosition(0.3);
        sleep(300);
        mainIntake.setPosition(0.05);
        sleep(200);
        setArmPos(740);
        pivot.setPosition(0.1);
        slides.setTargetPosition(500);
        Actions.runBlocking(secondSpecimenScore);
//        sleep(300);
        mainIntake.setPosition(0.7);
        sleep(300);
        pivot.setPosition(0.4);
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
        sleep(200);
        pivot.setPosition(0.1);
        setArmPos(405);
        Actions.runBlocking(thirdSpecimenGrab);
        pivot.setPosition(0.3);
        sleep(300);
        mainIntake.setPosition(0.05);
        sleep(300);
        setArmPos(740);
        pivot.setPosition(0.1);
        slides.setTargetPosition(500);
        Actions.runBlocking(thirdSpecimenScore);
//        Actions.runBlocking(parkObservation);


    }
}
