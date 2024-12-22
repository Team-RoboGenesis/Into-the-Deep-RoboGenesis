package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "bucketAuto")
public class bucketAuto extends LinearOpMode {

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

        Pose2d beginPose = new Pose2d(-8, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action preloadSpecimenScore = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(0, -60))
                .waitSeconds(0.5)
                .lineToY(-19)//Score preloaded specimen
                .waitSeconds(1)
                .build();
        Action pushFirstSample = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .lineToY(-45)
                .strafeTo(new Vector2d(-54.5, -45))
                .build();
        Action pushFirstSamplePt2 = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .lineToY(-35)
                .build();


        waitForStart();

        sleep(5000);
        mainIntake.setPosition(0.1);
        sleep(1000);
        setArmPos(775);
        pivot.setPosition(0.6);
        slides.setTargetPosition(400);
        Actions.runBlocking(preloadSpecimenScore);
        mainIntake.setPosition(0.75);
        sleep(1000);
        pivot.setPosition(0.4);
        slides.setTargetPosition(0);
        Actions.runBlocking(pushFirstSample);
        setArmPos(100);
        pivot.setPosition(0.55);
        sleep(500);
        Actions.runBlocking(pushFirstSamplePt2);
        sleep(1000);
        mainIntake.setPosition(0.1);

//        pivot.setPosition(0.9);
//        sleep(500);
//        setArmPos(0);

        sleep(2000);

    }
}
