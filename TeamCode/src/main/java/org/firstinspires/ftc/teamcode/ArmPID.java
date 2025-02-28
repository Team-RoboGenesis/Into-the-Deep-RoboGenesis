package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ArmPID extends OpMode {
public DcMotor slides = null;
double TargetPos = 0;
double CurrentPos = slides.getCurrentPosition();
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
        if (TargetPos > CurrentPos) {
            slides.setPower(0.2);
            boolean forward = true;
        }else if (TargetPos < CurrentPos){
            slides.setPower(-0.2);
            boolean forward = false;
        }

    }
}
