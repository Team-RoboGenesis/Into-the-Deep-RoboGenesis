package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    //motors, servos', and sensors
    private DcMotor frontLeftWheel = null;
    private DcMotor frontRightWheel = null;
    private DcMotor backLeftWheel = null;
    private DcMotor backRightWheel = null;
    private Servo mainIntake = null;
    private DcMotor slides = null;
    private Servo pivot = null;
    private DcMotor leftIntakeArm = null;
    private DcMotor rightIntakeArm = null;
    private DcMotor hangArm = null;
    private Servo ascentServo = null;
    private Servo led1 = null;
    ColorRangeSensor color;

    TouchSensor armLimit;

    //constants
    int GRAB_DISTANCE = 30;
    int MAX_COLOR = 500;
    int HIGH_SPEED = 400;
    int ROUND_TO_INT = 100;
    int HANG_EXTEND = 2830;
    int SLIDES_EXTEND = 1700;
    int ARM_LIMIT = 3400;
    int ARM_SCORE_POS = 727;
    int ARM_WALL_POS = 365;
    int ARM_BUCKET_POS = 1450;
    int SLIDES_RETRACT = 0;

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
        armLimit = hardwareMap.get(TouchSensor.class, "touchSensorArm");

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
        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightIntakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);

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
        } else if (position > HANG_EXTEND) {
            hangArm.setTargetPosition(HANG_EXTEND);
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
        if (armLimit.isPressed() & gamepad2.left_stick_y>=0) {
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
    } public boolean yellow() { // see if yellow is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < GRAB_DISTANCE & color.green() > color.blue() & color.green() > color.red() & color.green() > MAX_COLOR;
    } public boolean red() { // see if red is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < GRAB_DISTANCE & color.red() > color.green() & color.red() > color.blue() & color.red() > MAX_COLOR;
    } public boolean blue() { // see if blue is the color of sample in claw
        return color.getDistance(DistanceUnit.MM) < GRAB_DISTANCE & color.blue() > color.red() & color.blue() > color.green() & color.blue() > MAX_COLOR;
    } public boolean purple() { // run on purple
        return color.getDistance(DistanceUnit.MM) > GRAB_DISTANCE;
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
        int slidesPos = (int) (slides.getCurrentPosition()+(-gamepad2.right_stick_y* ROUND_TO_INT));
        int armPos = (int) (rightIntakeArm.getCurrentPosition()+(-gamepad2.left_stick_y* ROUND_TO_INT*2));
        int hangPos = (int) (hangArm.getCurrentPosition()+(-gamepad2.left_trigger* HIGH_SPEED +gamepad2.right_trigger* HIGH_SPEED));

        // mecanum drive
        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);

        //telemetry
        telemetry.addData("armAngle", rightIntakeArm.getCurrentPosition());
        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.addData("Y", gamepad2.left_stick_y);
        telemetry.update();

        // intake controls
        setArmPos(armPos);
        setSlidePos(slidesPos);
        setHangPos(hangPos);
//        slides.setPower(gamepad2.right_stick_y);
        if (gamepad2.left_bumper) {
            closeClaw();
        } else if (gamepad2.right_bumper) {
            openClaw();
        } else if (gamepad2.a & slides.getCurrentPosition()>50) {//The slides have to be slightly extended for the pivot to clear the pulley for the slides
            pivot.setPosition(1);
        } else if (gamepad2.y) {
            pivot.setPosition(0);
        } else if (gamepad2.b) {
            pivotMiddlePos();
        }
        if (gamepad1.b) {
            ascentServo.setPosition(0);
        } else if (gamepad1.y) {
            ascentServo.setPosition(0.7);
        }
//        arm presets
        else if (gamepad2.dpad_up) { //score specimen preset
            setArmPos(ARM_SCORE_POS);
            pivotTopPos();
            slides.setTargetPosition(300);
        } else if (gamepad2.dpad_down) { //wall grab preset
            setArmPos(ARM_WALL_POS);
            pivotWallPos();
            slides.setTargetPosition(SLIDES_RETRACT);
        } else if (gamepad2.dpad_left) { //high bucket preset
            setArmPos(ARM_BUCKET_POS);
            slides.setTargetPosition(SLIDES_EXTEND);
            pivotMiddlePos();
        } else if (gamepad2.dpad_right) {
            setSlidePos(SLIDES_RETRACT);
            pivotTopPos();
            setHangPos(HANG_EXTEND);
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
