package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class ArmPID extends OpMode {
public DcMotor slides = null;
double TargetPos = 1000;
double priorDistance = 0;
double currentDistance = 0;
boolean fastLoop = false;

    @Override
    public void init() {
        slides = hardwareMap.get(DcMotor.class, "slides");
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slides.setTargetPosition(0);
        slides.setPower(0.5);
        slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        while (slides.getCurrentPosition() != TargetPos) { /* loops while current position is not equal to target position */
            double CurrentPos = slides.getCurrentPosition();
        if (TargetPos > CurrentPos) { //a rudimentary P in PID
            slides.setPower(0.2);
            boolean forward = true;
        }else if (TargetPos < CurrentPos){
            slides.setPower(-0.2);
            boolean forward = false;
        }
            priorDistance = currentDistance; //a rudimentary I in PID
            currentDistance = TargetPos - CurrentPos;

        if (TargetPos - CurrentPos < 10) fastLoop = true; //almost a function that works like a D in PID

        if (!fastLoop) {

        }

            }
        }
      }

