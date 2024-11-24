package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import dalvik.system.DelegateLastClassLoader;

@Autonomous(name = "AutonomousRight")
public class Autonawmouse extends OpMode {
    public DcMotor frontLeftWheel = null;
    public DcMotor frontRightWheel = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    //public Servo rightPivot = null;
    //public Servo leftPivot = null;
    public DcMotor intakeArm = null;
    public DcMotor slides = null;
    public Servo temporaryPivot = null;
    ElapsedTime timer = new ElapsedTime();


   private double startTime;
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

        backRightWheel.setDirection(DcMotor.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        intakeArm.setTargetPosition(0);
        slides.setPower(0.75);
        intakeArm.setPower(1);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftWheel.setTargetPosition(0);
        backRightWheel.setTargetPosition(0);
        frontLeftWheel.setTargetPosition(0);
        frontRightWheel.setTargetPosition(0);
        backLeftWheel.setPower(0.1);
        backRightWheel.setPower(0.1);
        frontLeftWheel.setPower(0.1);
        frontRightWheel.setPower(0.1);
        backLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightWheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setSlidePos(int position) {
        if (position>0) {
            slides.setTargetPosition(0);
        } else if (position<-1500) {
            slides.setTargetPosition(-1500);
        } else {
            slides.setTargetPosition(position);
        }
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
        if (timer.time()>= 28.5) {
            mainIntake.setPosition(0.1);
            intakeArm.setTargetPosition(200);
            frontRightWheel.setTargetPosition(200);
            frontLeftWheel.setTargetPosition(200);
            backRightWheel.setTargetPosition(200);
            backLeftWheel.setTargetPosition(200);
        } else if (timer.time()<= 1.5) {
            mainIntake.setPosition(0.1);
            intakeArm.setTargetPosition(200);
            frontRightWheel.setTargetPosition(200);
            frontLeftWheel.setTargetPosition(200);
            backRightWheel.setTargetPosition(200);
            backLeftWheel.setTargetPosition(200);
        }
    }
    //opposite of init
    public void stop() {

    }

}