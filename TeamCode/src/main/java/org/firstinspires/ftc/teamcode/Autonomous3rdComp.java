package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto3rdComp")
public class Autonomous3rdComp extends LinearOpMode {

    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;

    public void setSlidePos(int position) {
        if (position>0) {
            slides.setTargetPosition(0);
        } else if (position<-1500) {
            slides.setTargetPosition(-1500);
        } else {
            slides.setTargetPosition(position);
        }
    }
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

        leftIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);

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

        Pose2d beginPose = new Pose2d(10, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action firstSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .lineToY(-25)
                .build();
        Action firstSamplePush = drive.actionBuilder(drive.pose)
                .lineToY(-40)//line up for first sample push
                .splineTo(new Vector2d(37, -38), Math.toRadians(90))
                .splineTo(new Vector2d(37, 0), Math.toRadians(90))
                .strafeTo(new Vector2d(40, 3))
                .strafeTo(new Vector2d(55, 3))
                .strafeTo(new Vector2d(55, -60))//move first sample into observation
                .strafeTo(new Vector2d(55, -45))
                .turn(Math.toRadians(-180))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -40), Math.toRadians(90)) //score second specimen
                .strafeTo(new Vector2d(7, -25))
                .build();
        Action thirdSpecimenGrab = drive.actionBuilder(drive.pose)
                .lineToY(-40)
                .strafeToLinearHeading(new Vector2d(47, -55), Math.toRadians(-90))//score third specimen
                .build();
        Action thirdSpecimenScore = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(4, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(4, -25))
                .waitSeconds(1)
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();
        Action fullRoute = drive.actionBuilder(drive.pose)
                .lineToY(-30)//score first specimen
                .lineToY(-40)//line up for first sample push
                .splineTo(new Vector2d(40, -38), Math.toRadians(90))
                .splineTo(new Vector2d(40, 5), Math.toRadians(90))
                .strafeTo(new Vector2d(55, 5))
                .strafeTo(new Vector2d(55, -60))//move first sample into observation
                .strafeTo(new Vector2d(55, -45))
                .strafeToLinearHeading(new Vector2d(7, -40), Math.toRadians(90)) //score second specimen
                .strafeTo(new Vector2d(7, -30))
                .lineToY(-40)
//                        .splineTo(new Vector2d(46, -38), Math.toRadians(90))
//                        .splineTo(new Vector2d(46, -14), Math.toRadians(90))
//                        .strafeTo(new Vector2d(57, -14))
//                        .strafeTo(new Vector2d(57, -55))//move second sample into observation
                .strafeToLinearHeading(new Vector2d(47, -45), Math.toRadians(-90))//score third specimen
                .strafeToLinearHeading(new Vector2d(4, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(4, -30))
                .lineToY(-40)
                .strafeTo(new Vector2d(60, -60))
                .build();

        waitForStart();

//        Actions.runBlocking(fullRoute);
//        mainIntake.setPosition(0.05);
//        sleep(200);
//        setArmPos(772);
//        pivot.setPosition(0.1);
//        slides.setTargetPosition(530);
        Actions.runBlocking(firstSpecimenScore);
        sleep(3000);
//        mainIntake.setPosition(0.75);
        sleep(1000);
//        pivot.setPosition(0.5);
//        slides.setTargetPosition(0);
        Actions.runBlocking(firstSamplePush);
        sleep(1000);
//        setArmPos(1000);
        sleep(5500);
//        setArmPos(515);
//        pivot.setPosition(0.45);
//        slides.setTargetPosition(0);
        sleep(1000);
//        mainIntake.setPosition(0.05);
        sleep(200);
//        setArmPos(772);
//        pivot.setPosition(0.1);
//        slides.setTargetPosition(530);
        Actions.runBlocking(secondSpecimenScore);
        sleep(3000);
//        mainIntake.setPosition(0.75);
        sleep(200);
//        pivot.setPosition(0.5);
//        slides.setTargetPosition(0);
        Actions.runBlocking(thirdSpecimenGrab);
        sleep(400);
//        setArmPos(515);
//        pivot.setPosition(0.45);
//        slides.setTargetPosition(0);
        sleep(1500);
//        mainIntake.setPosition(0.05);
//        sleep(100);
//        setArmPos(772);
//        pivot.setPosition(0.1);
//        slides.setTargetPosition(530);
        Actions.runBlocking(thirdSpecimenScore);
        sleep(3000);
//        mainIntake.setPosition(0.75);
        sleep(200);
//        pivot.setPosition(0.5);
//        slides.setTargetPosition(0);
        Actions.runBlocking(parkObservation);
        sleep(2000);
//        slides.setTargetPosition(0);
//        pivot.setPosition(0);
//        setArmPos(0);
    }
}