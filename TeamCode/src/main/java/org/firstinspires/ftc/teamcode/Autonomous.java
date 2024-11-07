package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous (name = "Autonomous (sigma)")
public class Autonomous extends OpMode {
    public DcMotor frontLeftWheel   = null;
    public DcMotor frontRightWheel  = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    public Servo rightPivot = null;
    public Servo leftPivot = null;
    public DcMotor intakeArm = null;
    @Override
    public void init() {
        intakeArm = hardwareMap.get(DcMotor.class,"intakeArm");
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        rightPivot = hardwareMap.get(Servo.class,"rightPivot");
        leftPivot = hardwareMap.get(Servo.class, "leftPivot");

        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);

    }


    public void setServoPos (double position)  {
    leftPivot.setPosition(position);
    rightPivot.setPosition(position);

    }

    public void loop() {
        setServoPos(0.5);
        setServoPos(0.1);
    }
}
