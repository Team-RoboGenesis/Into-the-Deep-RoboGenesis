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
    //public Servo rightPivot = null;
    //public Servo leftPivot = null;
    public DcMotor intakeArm = null;
    public DcMotor slides = null;
    public Servo temporaryPivot = null;

    @Override
    public void init() {
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        //rightPivot = hardwareMap.get(Servo.class,"rightPivot");
        //leftPivot = hardwareMap.get(Servo.class, "leftPivot");
        intakeArm = hardwareMap.get(DcMotor.class,"intakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");
        temporaryPivot = hardwareMap.get(Servo.class, "goBildaPivot");

        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
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

    }
    public void setServoPos(double position) {
        //rightPivot.setPosition(position);
        //leftPivot.setPosition(position);
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
    public void setArmPos(int position) {
        if (position<0) {
            intakeArm.setTargetPosition(0);
        } else if (position>2713){
            intakeArm.setTargetPosition(2713);
        } else {
            intakeArm.setTargetPosition(position);
        }
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y/1.35; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x/1.35;
        double rx = -gamepad1.right_stick_x/1.35;
        int slidesPos = (int) (slides.getCurrentPosition()+(gamepad2.right_stick_y*100));
        int armPos = (int) (intakeArm.getCurrentPosition()+(-gamepad2.left_stick_y*100));




        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);
        setSlidePos(slidesPos);
        setArmPos(armPos);

        telemetry.addData("armAngle", intakeArm.getCurrentPosition());
        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.update();
//make claw open wider and close tighter
        if (gamepad2.left_bumper) {
            mainIntake.setPosition(0.1);
        } else if (gamepad2.right_bumper) {
            mainIntake.setPosition(0.75);
        } else if (gamepad2.a) {
     //       setServoPos(0.8);
            temporaryPivot.setPosition(0.1);
        } else if (gamepad2.y) {
     //       setServoPos(0.2);
            temporaryPivot.setPosition(0.75);
        } else if (gamepad2.b) {
      //      setServoPos(0.5);
            temporaryPivot.setPosition(0.4);
        } else if (gamepad2.x) {
            temporaryPivot.setPosition(1);
//            setServoPos(0.2);
//            setSlidePos(1000);
//            setArmPos(1400);
        }


    }
}
