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
                .lineToY(-38) //Score preloaded specimen
                        .build();
        Action firstSampleGrab = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(15, -38), 0)
                .splineTo(new Vector2d(30, -38), Math.toRadians(35))//Maneuver to 1st sample push
                        .build();
        waitForStart();

        mainIntake.setPosition(0.1);
        setArmPos(800);
        pivot.setPosition(0.5);
        Actions.runBlocking(preloadspecimenScore);
        setArmPos(600);
        Actions.runBlocking(firstSampleGrab);

    }
}
