package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    public DcMotor frontLeftWheel   = null;
    public DcMotor frontRightWheel  = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    public Servo rightPivot = null;
    public Servo leftPivot = null;
    public DcMotor intakeArm = null;

    //public Servo jointServo = null;

    @Override
    public void init() {
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        rightPivot = hardwareMap.get(Servo.class,"rightPivot");
        leftPivot = hardwareMap.get(Servo.class, "leftPivot");
        intakeArm = hardwareMap.get(DcMotor.class,"intakeArm");
        //jointServo = hardwareMap.get(Servo.class, "joint");
        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

    }
    public void setServoPos(double position) {
    rightPivot.setPosition(position);
    leftPivot.setPosition(position);


    }
    @Override
    public void loop() {
        double y = -gamepad1.left_stick_y; // Remember, Y stick is reversed!
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;
        double user2y = -gamepad2.left_stick_y;
        double pivotUp = gamepad2.right_trigger;
        double pivotDown = gamepad2.left_trigger;
        double user2ry = gamepad2.right_stick_y;

        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);
        //intakeArm.setPower(user2y/2);


        //jointServo.setPosition(user2ry);
        if (gamepad2.left_bumper) {
            //mainIntake.setPosition(0.2);
        } else if (gamepad1.right_bumper) {
            //mainIntake.setPosition(0.7);
        } else if (gamepad1.a) {
            setServoPos(0.5);
        } else if (gamepad1.y) {
            setServoPos(0.6);
        }


    }
}
