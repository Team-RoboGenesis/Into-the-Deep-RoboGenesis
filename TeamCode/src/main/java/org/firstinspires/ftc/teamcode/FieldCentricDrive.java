package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

        @TeleOp
        public class FieldCentricDrive extends LinearOpMode {

            public Servo mainIntake = null;
            public DcMotor slides = null;
            public Servo temporaryPivot = null;
            public DcMotor leftIntakeArm = null;
            public DcMotor rightIntakeArm = null;

            public void setSlidePos(int position) {
                if (position<0) {
                    slides.setTargetPosition(0);
                } else if (position>2830) {
                    slides.setTargetPosition(2830);
                } else {
                    slides.setTargetPosition(position);
                }
            }
            public void setArmPos(int position) {
        if (position > 0) {
            rightIntakeArm.setTargetPosition(0);
            leftIntakeArm.setTargetPosition(0);
        } else if (position < -2713) {
            rightIntakeArm.setTargetPosition(-2713);
            leftIntakeArm.setTargetPosition(-2713);
        }
        else {
            rightIntakeArm.setTargetPosition(position);
            leftIntakeArm.setTargetPosition(position);
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        temporaryPivot = hardwareMap.get(Servo.class, "goBildaPivot");
        rightIntakeArm = hardwareMap.get(DcMotor.class, "rightIntakeArm");
        leftIntakeArm = hardwareMap.get(DcMotor.class, "leftIntakeArm");
        slides = hardwareMap.get(DcMotor.class, "slides");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightIntakeArm.setTargetPosition(0);
        leftIntakeArm.setTargetPosition(0);
        rightIntakeArm.setPower(0.75);
        leftIntakeArm.setPower(0.75);
        rightIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftIntakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.75);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Retrieve the IMU from the hardware map
        IMU imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            if (gamepad1.options) {
                imu.resetYaw();
            }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            int slidesPos = (int) (slides.getCurrentPosition()+(-gamepad2.right_stick_y*400));
            int armPos = (int) (rightIntakeArm.getCurrentPosition()+(gamepad2.left_stick_y*100));
            
            setSlidePos(slidesPos);

            telemetry.addData("armAngle", rightIntakeArm.getCurrentPosition());
            telemetry.addData("slides", slides.getCurrentPosition());
            telemetry.update();

            if (gamepad2.left_bumper) {
                mainIntake.setPosition(0.1);
            } else if (gamepad2.right_bumper) {
                mainIntake.setPosition(0.5);
            } else if (gamepad2.a) {
                temporaryPivot.setPosition(0.3);
            } else if (gamepad2.y) {
                temporaryPivot.setPosition(0);
            } else if (gamepad2.b) {
                temporaryPivot.setPosition(0.5);
            } else if (gamepad2.left_stick_y<0) {
                setArmPos(armPos);
            } else if (gamepad2.left_stick_y>0) {
                setArmPos(armPos);
            } else if (gamepad2.x) {
                temporaryPivot.setPosition(1);
            }
//        arm presets
            else if (gamepad2.dpad_up) {
                setArmPos(840);
                temporaryPivot.setPosition(0.1);
                slides.setTargetPosition(2450);
            } else if (gamepad2.dpad_down) {
                setArmPos(600);
                temporaryPivot.setPosition(0.45);
                slides.setTargetPosition(0);
            } else if (gamepad2.dpad_left) {
                setArmPos(1165);
                slides.setTargetPosition(220);
                temporaryPivot.setPosition(0.3);
            }

        }
    }
}