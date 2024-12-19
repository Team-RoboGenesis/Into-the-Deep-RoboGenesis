package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous")
public class Autonomous extends LinearOpMode {

    Servo mainIntake = hardwareMap.get(Servo.class, "mainIntake");
    Servo pivot = hardwareMap.get(Servo.class, "goBildaPivot");
    DcMotor rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
    DcMotor leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
    DcMotor slides = hardwareMap.get(DcMotor.class, "slides");


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

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0.5);
        leftIntakeArm.setPower(0.5);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Pose2d beginPose = new Pose2d(10, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action preloadspecimenScore = drive.actionBuilder(drive.pose)
                .lineToY(-38)//Score preloaded specimen
                .waitSeconds(1)
                        .build();
        Action firstSampleGrab = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -40), 0)
                .waitSeconds(3)
                .splineTo(new Vector2d(30, -38), Math.toRadians(35))//Maneuver to 1st sample push
                        .build();
        Action fullRoute = drive.actionBuilder(drive.pose)
                .lineToY(-40) //Score preloaded specimen
                .waitSeconds(2)
                .splineTo(new Vector2d(15, -45), 0)
                .splineTo(new Vector2d(30, -38), Math.toRadians(35))//Maneuver to 1st sample push
                .waitSeconds(3)//lower arm and grab sample
                .splineTo(new Vector2d(40, -50), Math.toRadians(-45))//Deliver sample to observation zone
                .waitSeconds(3)//let go of sample and move arm out of way
                .splineTo(new Vector2d(45, -38), Math.toRadians(48))//Maneuver to 2nd sample push
                .waitSeconds(3)//lower arm and grab sample
                .splineTo(new Vector2d(50, -47), Math.toRadians(-45))//Deliver sample to observation zone
                .waitSeconds(1)//let go of sample
                .splineTo(new Vector2d(47.1, -47), Math.toRadians(-90))//grab first specimen
                .waitSeconds(0.5)
                .splineTo(new Vector2d(10, -40), Math.toRadians(90))//score first specimen
                .waitSeconds(2)
                .splineTo(new Vector2d(47, -53), Math.toRadians(90))//park in ascent zone
                .build();
        waitForStart();

//        mainIntake.setPosition(0.1);
//        setArmPos(750);
//        pivot.setPosition(0.6);
//        Actions.runBlocking(preloadspecimenScore);
//        slides.setTargetPosition(400);
//          setArmPos(600);
//        Actions.runBlocking(firstSampleGrab);
        Actions.runBlocking(fullRoute);

    }
}
