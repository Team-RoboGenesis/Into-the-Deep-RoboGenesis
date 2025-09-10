package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "oliverGame")
public class oliverGame extends OpMode {
    public Servo bugGoBrr = null;
    public DcMotor motorGoBr = null;
    public double servoPos = 0;
    public int level = 1;
    public int priorLevel = 1;
    public boolean first = true;
    public int caught = 0;


    @Override
    public void init() {
        bugGoBrr = hardwareMap.get(Servo.class, "bugGoBrr");
        motorGoBr = hardwareMap.get(DcMotor.class, "motorGoBr");
        bugGoBrr.setPosition(0.0);
    }

    @Override
    public void loop() {

            int servo = 0;
            int motorI = 0;
            Thread motor1 = new Thread(() -> {// triggers for rotation
                motorGoBr.setPower(0.1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                motorGoBr.setPower(0.0);
            });

            Thread servo1 = new Thread(() -> {
                servoPos = servoPos + 0.3333334;
                bugGoBrr.setPosition(servoPos);
            });

            int run = (int) (Math.random() * (1000 - ((level * 100) - 99))); //random number generator (constantly looping)
            if (run == 1) { // each has a varying chance of happening every loop based on the level
                motor1.start(); //runs "trigger" (thread) motor1
                motorI = motorI + 1;
            } else if (run == 2) {
                servo1.start();
                servo = servo + 1;
            } else if (run == 3) {

            } else if (run == 4) {

            }

            if (servoPos >= 1) {
                System.exit(1);
            }

            if (gamepad1.x) { //insures that the code at the bottom is run when you release the button after pressing it
                while (gamepad1.x) {
                    try {
                        Thread.sleep(100); // Sleep for 100 milliseconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // avoids busy-waiting (why is it called that?)
                    }
                }
                motorGoBr.setPower(-0.1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                motorGoBr.setPower(0.0);
                motorI = motorI - 1;
            }
            if (gamepad1.a) {
                while (gamepad1.a) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                servoPos = servoPos - 0.3333334;
                if (servoPos < 0) servoPos = 0;
                bugGoBrr.setPosition(servoPos);
                servo = servo - 1;
            }
            if ((servo >= 3) || (motorI >= 5)) {
                System.exit(1);
            }
            if (level != priorLevel) { // level complete
                while (!gamepad1.options) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                priorLevel = level;
            }
        }
}
