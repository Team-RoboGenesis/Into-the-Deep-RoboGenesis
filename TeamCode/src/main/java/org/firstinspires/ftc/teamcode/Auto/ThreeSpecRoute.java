package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadRunner.MecanumDrive;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "3SpecRoute")
public class ThreeSpecRoute extends LinearOpMode {

    //motors and sensors
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public Servo ascentServo = null;

    //constants
    int ARM_LIMIT = 3400;
    int SLIDES_EXTEND = 1700;
    int SLIDES_RETRACT = 0;

    public void setArmPos(int position) {
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
        } else if (position > ARM_LIMIT) {
            rightIntakeArm.setTargetPosition(ARM_LIMIT);
            leftIntakeArm.setTargetPosition(ARM_LIMIT);
        } else {
            rightIntakeArm.setTargetPosition(position);
            leftIntakeArm.setTargetPosition(position);
        }
    }
    public void setSlidePos(int position) { // limits for slides
        if (position < SLIDES_RETRACT) {
            slides.setTargetPosition(SLIDES_RETRACT);
        } else if (position > SLIDES_EXTEND) {
            slides.setTargetPosition(SLIDES_EXTEND);
        } else {
            slides.setTargetPosition(position);
        }
    }
    //functions
    public void scoreSpecimen() {
        closeClaw();
        sleep(100);
        setArmPos(720);
        pivot.setPosition(0.1);
        slides.setTargetPosition(585);
    }
    public void subEscape() {
        openClaw();
        pivotMiddlePos();
    }
    public void wallGrab() {
        slides.setTargetPosition(0);
        setArmPos(375);
        pivot.setPosition(0.3);
//        sleep(1000);
//        closeClaw();
//        sleep(100);
    }
    public void resetArm() {
        setArmPos(0);
        slides.setTargetPosition(0);
        pivot.setPosition(0);
        openClaw();
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
        rightIntakeArm.setPower(0.9);
        leftIntakeArm.setPower(0.9);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action fullRoute = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(6, -24), Math.toRadians(90))
                .stopAndAdd(this::subEscape)
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))
                .stopAndAdd(this::resetArm)

                //first push
                .splineTo(new Vector2d(28.96, -35.58), Math.toRadians(-12.99))
                .splineToSplineHeading(new Pose2d(53.55, -17.61, Math.toRadians(-35.00)), Math.toRadians(-45.00))
                .stopAndAdd(this::wallGrab)
                .splineToSplineHeading(new Pose2d(45.55, -57, Math.toRadians(-90.00)), Math.toRadians(-90.00))
                .waitSeconds(0.2)
                .stopAndAdd(this::closeClaw)
                .waitSeconds(0.2)
                .stopAndAdd(this::scoreSpecimen)
                //score

                .splineToSplineHeading(new Pose2d(47, -50, Math.toRadians(-90)), Math.toRadians(-90))
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(3, -40, Math.toRadians(90)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(3, -24, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(0.2)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(3.48, -38.87), Math.toRadians(-75.00))
                .splineToSplineHeading(new Pose2d(25.47, -50.02, Math.toRadians(-22.17)), Math.toRadians(-22.17))
                .splineToSplineHeading(new Pose2d(47.77, -58.38, Math.toRadians(-90.00)), Math.toRadians(-90.00))
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(39.30, -56.67, Math.toRadians(135.00)), Math.toRadians(135.00))
                .splineToSplineHeading(new Pose2d(3.38, -32.43, Math.toRadians(90.00)), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(3, -24), Math.toRadians(90))
                .waitSeconds(0.3)
                .splineToConstantHeading(new Vector2d(3.48, -38.87), Math.toRadians(-75.00))
                .splineToSplineHeading(new Pose2d(25.47, -50.02, Math.toRadians(-22.17)), Math.toRadians(-22.17))
                .splineToSplineHeading(new Pose2d(47.77, -58.38, Math.toRadians(90.00)), Math.toRadians(90.00))
                .build();

//        Action firstSpecimen = firstSpecimenScore.build();
//        Action FullRoute = fullRoute.build();

        waitForStart();

        scoreSpecimen();
        Actions.runBlocking(fullRoute);
    }
}
