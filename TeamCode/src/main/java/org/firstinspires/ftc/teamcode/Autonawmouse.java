package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "AutonomousRight")
public class Autonawmouse extends OpMode {

    //motors
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

    //odometryWheels
    public DcMotor odometryLeft;
    public DcMotor odometryRight;
    public DcMotor odometryPerp;


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

        odometryLeft = frontRightWheel;
        odometryPerp = frontLeftWheel;
        odometryRight = backLeftWheel;

        backLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        frontLeftWheel.setDirection(DcMotor.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        odometryRight.setDirection(DcMotorSimple.Direction.REVERSE);
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
//        backLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontRightWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontLeftWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontLeftWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontRightWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backLeftWheel.setTargetPosition(0);
//        backRightWheel.setTargetPosition(0);
//        frontLeftWheel.setTargetPosition(0);
//        frontRightWheel.setTargetPosition(0);
//        backLeftWheel.setPower(0.1);
//        backRightWheel.setPower(0.1);
//        frontLeftWheel.setPower(0.1);
//        frontRightWheel.setPower(0.1);
        odometryLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometryRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        odometryPerp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    final static double L = 30.2; //Space between parallel odometry pods in cm
    final static double B = 13.1; //Space between the midpoint of L and perpendicular odometry pod
    final static double R = 2.5; //Radius of odometry pod
    final static double N = 2040; //number of ticks in a full revolution of odometry wheel
    final static double cm_per_tick = 0.5 * Math.PI * R / N;

    public int currentRightPosition = 0;
    public int currentLeftPosition = 0;
    public int currentPerpPosition = 0;

    public int oldRightPosition = 0;
    public int oldLeftPosition = 0;
    public int oldPerpPosition = 0;

    public void setSlidePos(int position) {
        if (position > 0) {
            slides.setTargetPosition(0);
        } else if (position < -1500) {
            slides.setTargetPosition(-1500);
        } else {
            slides.setTargetPosition(position);
        }
    }
    public class XyhVector {
        private double h;
        private double y;
        public double x; 
        // X position public double y; 
        // Y position public double heading; 
        // Heading (orientation) in radians 
        public XyhVector(double x, double y, double h) { 
            this.x = x; this.y = y; this.h = h;
        }

        public XyhVector(XyhVector startPos) {
        }
        // Method to set new values
        public void set(double x, double y, double h) {
            this.x = x; this.y = y; this.h = h;
        } @Override public String toString() {
            return "XyhVector{" + "x=" + x + ", y=" + y + ", heading=" + h + '}'; }
    }
    public XyhVector START_POS = new XyhVector(213,215, Math.toRadians(-174));
    public XyhVector pos = new XyhVector(START_POS);
        public void odometry() {

            oldLeftPosition = currentLeftPosition;
            oldPerpPosition = currentPerpPosition;
            oldRightPosition = currentRightPosition;

            currentLeftPosition = -odometryLeft.getCurrentPosition();
            currentPerpPosition = odometryPerp.getCurrentPosition();
            currentRightPosition = -odometryRight.getCurrentPosition();

            int dn1 = currentLeftPosition - oldLeftPosition;
            int dn2 = currentRightPosition - oldRightPosition;
            int dn3 = currentPerpPosition - oldPerpPosition;

            double dtheta = cm_per_tick * (dn2 - dn1) / L;
            double dx = cm_per_tick * (dn1 + dn2) / 2;
            double dy = cm_per_tick * (dn3 - (dn2 - dn1) * B / L);

        double theta = pos.h + (dtheta / 2.0);
        pos.x += dx * Math.cos(theta) - dy * Math.sin(theta);
        pos.y += dx * Math.sin(theta) + dy * Math.cos(theta);
        pos.h += dtheta;
        }
        /**
         *
         */
        @Override
        public void loop() {

            telemetry.addData("slides", slides.getCurrentPosition());
            telemetry.addData("armAngle", intakeArm.getCurrentPosition());
            telemetry.addData("heading", pos.h);
            telemetry.addData("heading", pos.x);
            telemetry.addData("heading", pos.y);
            telemetry.update();

            if (getRuntime() - startTime > 30) {
                requestOpModeStop();
                return;
            }

             if (timer.time() <=25) {
                mainIntake.setPosition(0.1);
                intakeArm.setTargetPosition(200);
//                frontRightWheel.setPower(0.1);
//                frontLeftWheel.setPower(-0.1);
//                backRightWheel.setPower(0.1);
//                backLeftWheel.setPower(-0.1);
            }
        }

        //opposite of init
        public void stop() {

        }

}