package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name = "teleop")
public class teleop extends OpMode {

    public DcMotor frontLeftWheel   = null;
    public DcMotor frontRightWheel  = null;
    public DcMotor backLeftWheel = null;
    public DcMotor backRightWheel = null;
    public Servo mainIntake = null;
    public DcMotor intakeArm = null;
    public DcMotor slides = null;
    public Servo temporaryPivot = null;
    public DcMotor leftIntakeArm = null;
    public DcMotor rightIntakeArm = null;

    @Override
    public void init() {
        frontLeftWheel = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightWheel = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftWheel = hardwareMap.get(DcMotor.class, "backLeft");
        backRightWheel = hardwareMap.get(DcMotor.class, "backRight");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        intakeArm = hardwareMap.get(DcMotor.class,"intakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");
        temporaryPivot = hardwareMap.get(Servo.class, "goBildaPivot");
        leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
        rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");

        backRightWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0);
        leftIntakeArm.setPower(0);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        if (position < 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
//            intakeArm.setTargetPosition(0);
        } else if (position > 2713) {
            rightIntakeArm.setTargetPosition(2713);
            leftIntakeArm.setTargetPosition(2713);
//            intakeArm.setTargetPosition(2713);
        } else {
//            intakeArm.setTargetPosition(position);
        }
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y/1.35; // Remember, Y stick is reversed!
        double x = -gamepad1.left_stick_x/1.35;
        double rx = -gamepad1.right_stick_x/1.35;
        int slidesPos = (int) (slides.getCurrentPosition()+(gamepad2.right_stick_y*100));
        int armPos = (int) (rightIntakeArm.getCurrentPosition()+(-gamepad2.left_stick_y*100));

        frontLeftWheel.setPower(y + x + rx);
        backLeftWheel.setPower(y - x + rx);
        frontRightWheel.setPower(y - x - rx);
        backRightWheel.setPower(y + x - rx);
        setSlidePos(slidesPos);
        setArmPos(armPos);

        telemetry.addData("armAngle", intakeArm.getCurrentPosition());
        telemetry.addData("slides", slides.getCurrentPosition());
        telemetry.update();

        if (gamepad2.left_bumper) {
            mainIntake.setPosition(0.1);
        } else if (gamepad2.right_bumper) {
            mainIntake.setPosition(0.75);
        } else if (gamepad2.a) {
            temporaryPivot.setPosition(0.4);
        } else if (gamepad2.y) {
            temporaryPivot.setPosition(0.9);
        } else if (gamepad2.b) {
            temporaryPivot.setPosition(0.5);
        } else if (gamepad2.x) {
          //  temporaryPivot.setPosition(1);
            temporaryPivot.setPosition(0.4);
            setArmPos(1400);
        }


    }
}
