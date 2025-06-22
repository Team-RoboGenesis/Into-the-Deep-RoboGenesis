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
        Thread thread1 = new Thread(() -> {
            motorGoBr.setPower(0.1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            motorGoBr.setPower(0.0);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            if (gamepad1.x){
                motorGoBr.setPower(-0.1);
            }
        });

        thread1.start();
        thread2.start();

         servoPos = servoPos + 0.00001;
         bugGoBrr.setPosition(servoPos);
         telemetry.addData("servoPos", String.valueOf(servoPos));
         telemetry.update();
         if(servoPos >= 1) {
             System.exit(1);
         }
        if(gamepad1.a){
            servoPos = servoPos-0.0006;
            if(servoPos < 0) servoPos = 0;
        }

    }
}

