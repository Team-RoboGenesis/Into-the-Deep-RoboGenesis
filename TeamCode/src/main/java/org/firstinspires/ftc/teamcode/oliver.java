package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
        telemetry.addData("pressed?", gamepad1.a);
        telemetry.update();
        if(gamepad1.left_stick_button){
            bugGoBrr.setPosition(0.5);
        } else if(gamepad1.b){
            bugGoBrr.setPosition(1);
        } else if(gamepad1.y){
            bugGoBrr.setPosition(0.0);
        } else if(gamepad1.x){
            bugGoBrr.setPosition(0.2);
        } else if (gamepad1.a) {
            while (gamepad1.a) {
                try {
                    Thread.sleep(100); // Sleep for 100 milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // avoids busy-waiting (why is it called that?)
                }
            }
            motorGoBr.setPower(0.5);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            motorGoBr.setPower(0.0);
        }

    }
}
