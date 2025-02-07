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

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "MeanAuto")
public class specimenAuto extends LinearOpMode {

    //motors and sensors
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;
    public Servo led1 = null;
    ColorRangeSensor color;

    //constants
    int grabDistance = 30;
    int maxColor = 500;
    int armLimit = 3400;

    //color definitions
    public boolean yellow() { // see if yellow is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.green() > color.blue() & color.green() > color.red() & color.green() > maxColor;
    } public boolean red() { // see if red is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.red() > color.green() & color.red() > color.blue() & color.red() > maxColor;
    } public boolean blue() { // see if blue is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.blue() > color.red() & color.blue() > color.green() & color.blue() > maxColor;
    } public boolean purple() { // run on purple
        return color.getDistance(DistanceUnit.MM) > grabDistance;
    }

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
        slides.setTargetPosition(530);
    }
    public void wallGrab() {
        slides.setTargetPosition(0);
        setArmPos(370);
        pivot.setPosition(0.3);
        sleep(300);
        closeClaw();
        sleep(200);
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
        color = hardwareMap.get(ColorRangeSensor.class, "color");
        led1 = hardwareMap.get(Servo.class, "LED1");

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
        rightIntakeArm.setPower(0.75);
        leftIntakeArm.setPower(0.75);
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
                .lineToY(-23)
                .build();
        Action clearSubmersible = drive.actionBuilder(drive.pose)
                .lineToY(-46)
                .build();
        Action firstSamplePush = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(6, -46))
                .strafeTo(new Vector2d(46, -40))
//                .splineTo(new Vector2d(46, 0), Math.toRadians(90))
//                .splineTo(new Vector2d(55, 0), Math.toRadians(90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))//move first sample into observation
                .strafeTo(new Vector2d(55, -46.5))
                .turn(Math.toRadians(-180))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -21))
                .build();
        Action thirdSpecimenGrab = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(56, -47.5), Math.toRadians(-100))//score third specimen
                .build();
        Action thirdSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(3, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -23))
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();

        if (yellow()) {
            led1.setPosition(0.35);//yellow
        } else if (red()) {
            led1.setPosition(0.28);//red
        } else if (blue()) {
            led1.setPosition(0.6);//blue
        } else if (purple()) {
            led1.setPosition(0.71);//purple
        }
        waitForStart();

        scoreSpecimen();
        Actions.runBlocking(firstSpecimenScore);
        openClaw();
        sleep(200);
        pivotMiddlePos();
        slides.setTargetPosition(0);
//        Actions.runBlocking(clearSubmersible);
//        pivotTopPos();
//        setArmPos(385);
        sleep(200);
        Actions.runBlocking(firstSamplePush);
        wallGrab();
        scoreSpecimen();
        Actions.runBlocking(secondSpecimenScore);
        sleep(300);
        openClaw();
        sleep(200);
        pivotMiddlePos();
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
        Actions.runBlocking(thirdSpecimenGrab);
        wallGrab();
        scoreSpecimen();
        Actions.runBlocking(thirdSpecimenScore);
        pivotMiddlePos();
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
//        Actions.runBlocking(parkObservation);
    }
}
