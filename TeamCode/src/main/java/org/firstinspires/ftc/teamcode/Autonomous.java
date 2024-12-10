package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous")
public class Autonomous extends LinearOpMode {

//    Servo mainIntake = hardwareMap.get(Servo.class, "mainIntake");
//    Servo pivot = hardwareMap.get(Servo.class, "goBildaPivot");
//    DcMotor rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
//    DcMotor leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
//    DcMotor slides = hardwareMap.get(DcMotor.class, "slides");

//    public void setSlidePos(int position) {
//        if (position>0) {
//            slides.setTargetPosition(0);
//        } else if (position<-1500) {
//            slides.setTargetPosition(-1500);
//        } else {
//            slides.setTargetPosition(position);
//        }
//    }
//    public void setArmPos(int position) {
//        if (position < 0) {
//            rightIntakeArm.setTargetPosition(0);
//            leftIntakeArm.setTargetPosition(0);
//        } else if (position > 2713) {
//            rightIntakeArm.setTargetPosition(2713);
//            leftIntakeArm.setTargetPosition(2713);
//        }
//        else {
//            rightIntakeArm.setTargetPosition(position);
//            leftIntakeArm.setTargetPosition(position);
//        }
//    }

    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

//        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightIntakeArm.setTargetPosition(0);
//        leftIntakeArm.setTargetPosition(0);
//        rightIntakeArm.setPower(0.5);
//        leftIntakeArm.setPower(0.5);
//        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        slides.setTargetPosition(0);
//        slides.setPower(0.75);
//        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

//        mainIntake.setPosition(0.1);
//        wait();
//        setArmPos(1000);
//        temporaryPivot.setPosition(0.5);
        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action move = drive.actionBuilder(beginPose)

                .lineToY(30)
//                setArmPos(800);
//      mainIntake.setPosition(0.75);
                .lineToY(20)
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(25, 20))
                .turn(Math.toRadians(45))
                .lineToY(40)
                .turn(Math.toRadians(-135))
                .lineToY(5)
                .lineToY(50)

                .build();


        Actions.runBlocking(move);
    }
}
