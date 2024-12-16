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
    public Action setArmPos(int position) {
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
//            leftIntakeArm.setTargetPosition(0);
        } else if (position > 2713) {
            rightIntakeArm.setTargetPosition(2713);
//            leftIntakeArm.setTargetPosition(2713);
        }
        else {
            rightIntakeArm.setTargetPosition(position);
//            leftIntakeArm.setTargetPosition(position);
        }
        return null;
    }
    public Action setClawPos(double position) {
        mainIntake.setPosition(position);
        return null;
    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
//        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0.5);
//        leftIntakeArm.setPower(0.5);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        mainIntake.setPosition(0.1);
        wait();
        setArmPos(1000);
        pivot.setPosition(0.5);
        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action move = drive.actionBuilder(beginPose)
                .lineToY(30)
                .build();
                Actions.runBlocking(setArmPos(800));
                Actions.runBlocking(setClawPos(0.75));
                move = drive.actionBuilder(beginPose)
                .lineToY(20)
                .strafeTo(new Vector2d(25, 20))
                .strafeTo(new Vector2d(50, 40))
                .turn(Math.toRadians(-180))
                .lineToY(5)
                .lineToY(60)
                        .strafeTo(new Vector2d(65, 60))
                        .lineToY(5)
                        .lineToY(60)
                        .strafeTo(new Vector2d(80, 60))
                        .lineToY(5)

                .build();
                setArmPos(0);


        Actions.runBlocking(move);
    }
}
