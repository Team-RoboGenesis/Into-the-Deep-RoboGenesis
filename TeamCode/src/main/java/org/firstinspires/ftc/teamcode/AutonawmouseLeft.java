package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "autonomousLeft")
public class AutonawmouseLeft extends OpMode {
    public DcMotor frontLeftWheel;
    public DcMotor frontRightWheel;
    public DcMotor backLeftWheel;
    public DcMotor backRightWheel;
    public Servo mainIntake;
    //public Servo rightPivot = null;
    //public Servo leftPivot = null;
    public DcMotor intakeArm;
    public DcMotor slides;
    public Servo temporaryPivot;
    ElapsedTime timer = new ElapsedTime();


    private double startTime;

    /**
     *
     */
    @Override
    public void init() {
        startTime = getRuntime();
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        //rightPivot = hardwareMap.get(Servo.class,"rightPivot");
        //leftPivot = hardwareMap.get(Servo.class, "leftPivot");
        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");
        temporaryPivot = hardwareMap.get(Servo.class, "goBildaPivot");

        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        intakeArm.setTargetPosition(0);
        slides.setPower(0.75);
        intakeArm.setPower(1);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setSlidePos(int position) {
        if (position>0) {
            slides.setTargetPosition(0);
        } else if (position<-1500) {
            slides.setTargetPosition(-1500);
        } else slides.setTargetPosition(position);
    }

    /**
     *
     */
    @Override
    public void loop() {

        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.addData("backLeftWheel", backLeftWheel.getCurrentPosition());
        telemetry.addData("backRightWheel", backRightWheel.getCurrentPosition());
        telemetry.addData("frontLeftWheel", frontLeftWheel.getCurrentPosition());
        telemetry.addData("frontRightWheel", frontRightWheel.getCurrentPosition());
        telemetry.update();

        if (getRuntime() - startTime > 30) {
            requestOpModeStop();
            return;
        }
        if (timer.time()<= 2) {
            mainIntake.setPosition(0.1);
            intakeArm.setTargetPosition(200);
        }

    }
}

