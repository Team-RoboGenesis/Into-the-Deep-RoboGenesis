package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "drive?")
public class oliver extends OpMode {
    public Servo bugGoBrr = null;
    public DcMotor motorGoBr = null;
    @Override
    public void init() {
        bugGoBrr = hardwareMap.get(Servo.class, "bugGoBrr");
        motorGoBr = hardwareMap.get(DcMotor.class, "motorGoBr");
        bugGoBrr.setPosition(0.2);
    }

    @Override
    public void loop() {
        if(gamepad1.a){
            bugGoBrr.setPosition(0.5);
        } else if(gamepad1.b){
            bugGoBrr.setPosition(1);
        } else if(gamepad1.y){
            bugGoBrr.setPosition(0.0);
        } else if(gamepad1.x){
            bugGoBrr.setPosition(0.2);
        }

    }
}
