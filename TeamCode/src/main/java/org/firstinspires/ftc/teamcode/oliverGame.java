package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "oliverGame?")
public class oliverGame extends OpMode {
    public Servo bugGoBrr = null;
    public DcMotor motorGoBr = null;
    public double servoPos = 0;
    @Override
    public void init() {
        bugGoBrr = hardwareMap.get(Servo.class, "bugGoBrr");
        motorGoBr = hardwareMap.get(DcMotor.class, "motorGoBr");
        bugGoBrr.setPosition(0.0);
    }

    @Override
    public void loop() {
         servoPos = servoPos + 0.0001;
         bugGoBrr.setPosition(servoPos);
         telemetry.addData("servoPos", String.valueOf(servoPos));
         telemetry.update();
         if(servoPos >= 1) {
             System.exit(1);
         }
        if(gamepad1.a){
            servoPos = servoPos-0.3;
            if(servoPos < 0) servoPos = 0;
        }

    }
}

