package org.firstinspires.ftc.teamcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class AutoActionController {
    private Servo mainIntake = null;
    private DcMotor slides = null;
    private Servo pivot = null;
    private DcMotor leftIntakeArm = null;
    private DcMotor rightIntakeArm = null;

    //constant for arm position
    int ARM_LIMIT = 3400;
    int ARM_SCORE_POSITION = 710;
    int LOWERED = 0;
    int WALL_GRAB = 355;

    //constant for slide position
    int SCORE_POSITION = 490;
    int RETRACTED = 0;


    public AutoActionController(HardwareMap hardwaremap) {

        mainIntake = hardwaremap.get(Servo.class, "mainIntake");
        pivot = hardwaremap.get(Servo.class, "goBildaPivot");
        rightIntakeArm = hardwaremap.get(DcMotor.class, "rightIntakeArm");
        leftIntakeArm = hardwaremap.get(DcMotor.class, "leftIntakeArm");
        slides = hardwaremap.get(DcMotor.class, "slides");
        this.setUpMotor();
    }

    public void setUpMotor() {
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
    }

    public void setArmPos(int position) {
        if (position < 0) {
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

    public void openClaw () {
        mainIntake.setPosition(0.7);
    }

    public void closeClaw () {
        mainIntake.setPosition(0.05);
    }

    public void pivotMiddlePos () {
        pivot.setPosition(0.4);
    }

    public void pivotTopPos () {
        pivot.setPosition(0.1);
    }



    public class scoreSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            closeClaw();
            setArmPos(ARM_SCORE_POSITION);
            slides.setTargetPosition(SCORE_POSITION);
            pivotTopPos();
            return slides.getCurrentPosition()>= slides.getTargetPosition()-20;
        }
    }

    public class depositSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            openClaw();
            pivotMiddlePos();
            slides.setTargetPosition(RETRACTED);
            return true;
        }
    }
    public class resetArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            pivotTopPos();
            slides.setTargetPosition(RETRACTED);
            setArmPos(LOWERED);
            return slides.getCurrentPosition()<=slides.getTargetPosition()+20;
        }
    }

    public Action scoreSpecimen () {
        return new scoreSpecimen();
    }

    public Action depositSpecimen () {
        return new depositSpecimen();
    }
    public Action resetArm () {
        return new resetArm();
    }
}