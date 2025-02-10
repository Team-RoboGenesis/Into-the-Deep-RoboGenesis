package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    //motors, servos', and sensors
    public DcMotor frontLeftWheel = null;
    public DcMotor frontRightWheel = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo pivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public DcMotor hangArm = null;
    public Servo ascentServo = null;
    public Servo led1 = null;
    ColorRangeSensor color;

    //constants
    int grabDistance = 30;
    int maxColor = 500;
    int highSpeed = 400;
    int roundToInt = 100;
    int hangExtend = 2830;
    int slidesExtend = 1700;
    int armLimit = 3400;
    int armScorePos = 750;
    int armWallPos = 425;
    int armBucketPos = 1450;
    int slidesRetract = 0;

    @Override
    public void init() {

        //configuration
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        slides = hardwareMap.get(DcMotor.class, "slides");
        pivot = hardwareMap.get(Servo.class, "goBildaPivot");
        leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
        rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
        hangArm = hardwareMap.get(DcMotor.class, "hangArm");
        ascentServo = hardwareMap.get(Servo.class, "revAscent");
        color = hardwareMap.get(ColorRangeSensor.class, "color");
        led1 = hardwareMap.get(Servo.class, "LED1");

        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        leftIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightIntakeArm.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        //ftc lib blocks
        FTCLibBlocks();
    }
    public void FTCLibBlocks () {
//        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        rightIntakeArm.setTargetPosition(0);
//        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(1);
        leftIntakeArm.setPower(1);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.5);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hangArm.setTargetPosition(0);
        hangArm.setPower(0.5);
        hangArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    /*
    * setHangPos is a handy function for controlling the movement
    * of our linear actuator and preventing the the actuator extending past
    * its physical limitations
    *
    *parameter "int position" is a measurement of ticks that the actuator can run to
     */
    public void setHangPos(int position) { // limits for hang arm
        if (position < 0) {
            hangArm.setTargetPosition(0);
        } else if (position > hangExtend) {
            hangArm.setTargetPosition(hangExtend);
        } else {
            hangArm.setTargetPosition(position);
        }
    }
    /*
    setArmPos is a helpful function for handling the pitching arm movement and
    preventing the arm from moving above or below its physical limitations
    *
    parameter "int position" is a measurement for ticks that the arm can run to
    */
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
    public void setSlidePos(int position) { // limits for slides
        if (position < slidesRetract) {
            slides.setTargetPosition(slidesRetract);
        } else if (position > slidesExtend) {
            slides.setTargetPosition(slidesExtend);
        } else {
            slides.setTargetPosition(position);
        }
    } public boolean yellow() { // see if yellow is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.green() > color.blue() & color.green() > color.red() & color.green() > maxColor;
    } public boolean red() { // see if red is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.red() > color.green() & color.red() > color.blue() & color.red() > maxColor;
    } public boolean blue() { // see if blue is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < grabDistance & color.blue() > color.red() & color.blue() > color.green() & color.blue() > maxColor;
    } public boolean purple() { // run on purple
        return color.getDistance(DistanceUnit.MM) > grabDistance;
    }

    //functions
    public void openClaw () {
        mainIntake.setPosition(0.7);
    }
    public void closeClaw () {
        mainIntake.setPosition(0.05);
    }
    public void pivotTopPos () {
        pivot.setPosition(0.1);
    }
    public void pivotMiddlePos () {
        pivot.setPosition(0.5);
    }
    public void pivotWallPos () {
        pivot.setPosition(0.3);
    }


    @Override
    public void loop() {

        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;
        int slidesPos = (int) (slides.getCurrentPosition()+(-gamepad2.right_stick_y*roundToInt));
        int armPos = (int) (rightIntakeArm.getCurrentPosition()+(-gamepad2.left_stick_y*roundToInt));
        int hangPos = (int) (hangArm.getCurrentPosition()+(-gamepad2.left_trigger*highSpeed+gamepad2.right_trigger*highSpeed));

        // mecanum drive
        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);

        //telemetry
        telemetry.addData("armAngle", rightIntakeArm.getCurrentPosition());
        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.update();

        // intake controls
        setArmPos(armPos);
        setSlidePos(slidesPos);
        setHangPos(hangPos);
        if (gamepad2.left_bumper) {
            closeClaw();
        } else if (gamepad2.right_bumper) {
            openClaw();
        } else if (gamepad2.a & slides.getCurrentPosition()>50) {//The slides have to be slightly extended for the pivot to clear the pulley for the slides
            pivot.setPosition(1);
        } else if (gamepad2.y) {
            pivot.setPosition(0);
        } else if (gamepad2.b) {
            pivot.setPosition(0.5);
        }
        if (gamepad1.b) {
            ascentServo.setPosition(0);
        } else if (gamepad1.y) {
            ascentServo.setPosition(0.7);
        }
//        arm presets
        else if (gamepad2.dpad_up) { //score specimen preset
            setArmPos(armScorePos);
            pivotTopPos();
            slides.setTargetPosition(300);
        } else if (gamepad2.dpad_down) { //wall grab preset
            setArmPos(armWallPos);
            pivotWallPos();
            slides.setTargetPosition(slidesRetract);
        } else if (gamepad2.dpad_left) { //high bucket preset
            setArmPos(armBucketPos);
            slides.setTargetPosition(slidesExtend);
            pivotMiddlePos();
        }
        //LED control
        if (yellow()) {
            led1.setPosition(0.35);//yellow
        } else if (red()) {
            led1.setPosition(0.28);//red
        } else if (blue()) {
            led1.setPosition(0.6);//blue
        } else if (purple()) {
            led1.setPosition(0.71);//purple
        }
    }
}
