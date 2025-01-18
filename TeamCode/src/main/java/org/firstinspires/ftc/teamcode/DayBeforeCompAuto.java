package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "specimenParkAutoRight")
public class DayBeforeCompAuto extends LinearOpMode {

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
        Action specimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .lineToY(-24)
                .build();
        Action clearSubmersible = drive.actionBuilder(drive.pose)
                .lineToY(-45)
                .build();
        Action firstSamplePush = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(46, -40), Math.toRadians(-90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))//move first sample into observation
                .strafeTo(new Vector2d(55, -50))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -50), Math.toRadians(90)) //score second specimen
                .build();
        Action thirdSpecimenGrab = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(47, -45), Math.toRadians(-90))//score third specimen
                .build();
        Action thirdSpecimenScore = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(4, -45), Math.toRadians(90))
                .strafeTo(new Vector2d(4, -25))
                .waitSeconds(1)
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();
        Action fullRoute = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .lineToY(-24)
                .lineToY(-45)
                .strafeToLinearHeading(new Vector2d(46, -40), Math.toRadians(-90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))//move first sample into observation
                .strafeTo(new Vector2d(55, -49.4))
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -50), Math.toRadians(180)) //score second specimen
                .lineToY(-24)
                .lineToY(-40)
                .strafeToLinearHeading(new Vector2d(47, -45), Math.toRadians(-90))//score third specimen
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -50), Math.toRadians(180)) //score second specimen
                .lineToY(-24)
                .strafeTo(new Vector2d(60, -60))
                .build();
        waitForStart();

        mainIntake.setPosition(0.05);
        sleep(1000);
        setArmPos(772);
        pivot.setPosition(0.1);
        slides.setTargetPosition(500);
        Actions.runBlocking(specimenScore);
        mainIntake.setPosition(0.6);
        sleep(300);
        pivot.setPosition(0.4);
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
        setArmPos(1500);
        Actions.runBlocking(firstSamplePush);
        setArmPos(505);
        pivot.setPosition(0.3);
        sleep(500);
        mainIntake.setPosition(0.05);
        sleep(300);
        setArmPos(772);
        pivot.setPosition(0.1);
        slides.setTargetPosition(500);
        Actions.runBlocking(secondSpecimenScore);
        sleep(300);
        Actions.runBlocking(specimenScore);
        sleep(300);
        mainIntake.setPosition(0.6);
        //reference
        setArmPos(840);
        pivot.setPosition(0.1);
        slides.setTargetPosition(2450);
        Actions.runBlocking(secondSpecimenScore);
        sleep(300);
        mainIntake.setPosition(0.75);
        sleep(1000);
        slides.setTargetPosition(0);
        Actions.runBlocking(clearSubmersible);
        sleep(1000);

        pivot.setPosition(0);
        sleep(500);
        setArmPos(0);

        sleep(2000);

    }
}
