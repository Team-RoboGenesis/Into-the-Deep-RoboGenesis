package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    private static final Logger log = LoggerFactory.getLogger(teleop.class);
    public DcMotor frontLeftWheel = null;
    public DcMotor frontRightWheel = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    public DcMotor slides = null;
    public Servo temporaryPivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;
    public DcMotor hangArm = null;
    public Servo ascentServo = null;
    public Servo led1 = null;
    ColorRangeSensor color;

    @Override
    public void init() {
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        slides = hardwareMap.get(DcMotor.class, "slides");
        temporaryPivot = hardwareMap.get(Servo.class, "goBildaPivot");
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
    public void setHangPos(int position) {
        if (position<0) {
            hangArm.setTargetPosition(0);
        } else if (position>2830) {
            hangArm.setTargetPosition(2830);
        } else {
            hangArm.setTargetPosition(position);
        }
    }
    public void setArmPos(int position) {
        if (position > 3400) {
            rightIntakeArm.setTargetPosition(3400);
            leftIntakeArm.setTargetPosition(3400);
        } else {
            rightIntakeArm.setTargetPosition(position);
            leftIntakeArm.setTargetPosition(position);
        }
    }
    public void setSlidePos(int position) {
        if (position<0) {
            slides.setTargetPosition(0);
        } else if (position>1700) {
            slides.setTargetPosition(1700);
        } else {
            slides.setTargetPosition(position);
        }
    } public boolean yellow() { // see if yellow is the color of sample in claw
        if (color.getDistance(DistanceUnit.MM) < 30 & color.green() > color.blue() & color.green()> color.red() & color.green()>500) {
            return true;
        }
        return false;

    } public boolean red() { // see if red is the color of sample in claw
        if (color.getDistance(DistanceUnit.MM) < 30 & color.red() > color.green() & color.red() > color.blue() & color.red() > 500) {
            return true;
        }
        return false;

    } public boolean blue() { // see if blue is the color of sample in claw
        if (color.getDistance(DistanceUnit.MM) < 30 & color.blue() > color.red() & color.blue() > color.green() & color.blue() > 500) {
            return true;
        }
        return false;
    } public boolean purple() {
        if (color.getDistance(DistanceUnit.MM)>30) {
            return true;
        }
        return false;
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = -gamepad1.left_stick_x;
        double rx = -gamepad1.right_stick_x;
        int slidesPos = (int) (slides.getCurrentPosition()+(-gamepad2.right_stick_y*100));
        int armPos = (int) (rightIntakeArm.getCurrentPosition()+(-gamepad2.left_stick_y*100));
        int hangPos = (int) (hangArm.getCurrentPosition()+(-gamepad2.left_trigger*400+gamepad2.right_trigger*400));
        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);
        setSlidePos(slidesPos);
        setHangPos(hangPos);

        telemetry.addData("armAngle", rightIntakeArm.getCurrentPosition());
        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.update();

        if (gamepad2.left_bumper) {
            mainIntake.setPosition(0.1);
        } else if (gamepad2.right_bumper) {
            mainIntake.setPosition(0.75);
        } else if (gamepad2.a & slides.getCurrentPosition()>50) {
            temporaryPivot.setPosition(1);
        } else if (gamepad2.y) {
            temporaryPivot.setPosition(0);
        } else if (gamepad2.b) {
            temporaryPivot.setPosition(0.5);
        } else if (gamepad2.left_stick_y<0) {
            setArmPos(armPos);
        } else if (gamepad2.left_stick_y>0) {
            setArmPos(armPos);
        } else if (gamepad1.b) {
            ascentServo.setPosition(0);
        } else if (gamepad1.y) {
            ascentServo.setPosition(0.7);
        }
//        arm presets
        else if (gamepad2.dpad_up) {
            setArmPos(750);
            temporaryPivot.setPosition(0.1);
            slides.setTargetPosition(300);
        } else if (gamepad2.dpad_down) {
            setArmPos(425);
            temporaryPivot.setPosition(0.3);
            slides.setTargetPosition(0);
        } else if (gamepad2.dpad_left) {
            setArmPos(1165);
            slides.setTargetPosition(220);
            temporaryPivot.setPosition(0.3);
        }
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
