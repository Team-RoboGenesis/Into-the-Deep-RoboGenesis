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
public class specimenParkAutoRight extends LinearOpMode {

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
        Action preloadspecimenScore = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .lineToY(-22)//Score preloaded specimen
                .waitSeconds(1)
                .build();
        Action firstSampleGrab = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .splineTo(new Vector2d(35, -35), 0)
                .splineTo(new Vector2d(53, -10), Math.toRadians(90))//Maneuver to 1st sample push
                .build();
        Action firstSamplePositionPt2 = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .lineToY(-55)
                .build();
        Action parkObservation = drive.actionBuilder(drive.pose)
                .waitSeconds(0.5)
                .splineTo(new Vector2d(47, -53), Math.toRadians(90))//park in observation zone
                .build();
        Action secondSampleGrab = drive.actionBuilder(drive.pose)
                .waitSeconds(0.1)
                .splineTo(new Vector2d(45, -38), Math.toRadians(48))
                .build();
        Action secondSpecimenGrab = drive.actionBuilder(drive.pose)
                .waitSeconds(0.75)
                .splineTo(new Vector2d(34, -51), Math.toRadians(-90))
                .build();
        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(10, -40), Math.toRadians(90))
                .lineToY(-22)
                .build();
        Action fullRoute = drive.actionBuilder(drive.pose)
                .lineToY(-22) //Score preloaded specimen
                .waitSeconds(2)
                .lineToY(-50)
                .waitSeconds(0.1)
                .splineTo(new Vector2d(50, -28), Math.toRadians(35))//Maneuver to 1st sample push
                .waitSeconds(3)
                .turn(Math.toRadians(-135))
                .lineToY(-55)//Push sample to observation zone
                .waitSeconds(0.1)
                .splineTo(new Vector2d(60, -28), Math.toRadians(35))//Maneuver to 2nd sample push
                .waitSeconds(0.1)
                .turn(Math.toRadians(-135))
                .lineToY(-55)//Push sample to observation zone
                .waitSeconds(0.5)
                .splineTo(new Vector2d(34, -51), Math.toRadians(-90))//Grab second specimen
                .waitSeconds(0.5)
                .splineTo(new Vector2d(10, -22), Math.toRadians(90))//score second specimen
                .waitSeconds(1)
                .lineToY(-50)
                .splineTo(new Vector2d(29, -45), 0)
                .splineTo(new Vector2d(47, -53), Math.toRadians(90))//park in observation zone
                .build();
        waitForStart();

        mainIntake.setPosition(0.1);
        sleep(1000);
        setArmPos(775);
        pivot.setPosition(0.6);
        slides.setTargetPosition(400);
        Actions.runBlocking(preloadspecimenScore);
        sleep(10000);
        mainIntake.setPosition(0.75);
        sleep(500);
        pivot.setPosition(0.4);
        slides.setTargetPosition(0);
        sleep(3000);

        pivot.setPosition(0.9);
        sleep(500);
        setArmPos(0);

        sleep(2000);

    }
}
