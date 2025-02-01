package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class colorSensorTest extends LinearOpMode {

    public Servo mainIntake = null;
    public Servo led1 = null;
    // Define a variable for our color sensor
//    ColorSensor color;
public void isASample (boolean yes) {
//    if (color.blue()>400& color.green()<400&color.red()<400) {
//        yes = true;
//        if (gamepad2.right_bumper) {
//            mainIntake.setPosition(0.7);
//        }
//    }
}
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
//        color = hardwareMap.get(ColorSensor.class, "Color");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");
        led1 = hardwareMap.get(Servo.class, "LED1");

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
//            telemetry.addData("Red", color.red());
//            telemetry.addData("Green", color.green());
//            telemetry.addData("Blue", color.blue());
            telemetry.update();

//            led1.setPosition(0.7);//purple
//            led1.setPosition(0.6);//blue
//            led1.setPosition(0.35);//yellow
//            led1.setPosition(0.28);//red

//            if (color.blue()> 400) {
//                mainIntake.setPosition(0.1);
//                sleep(2000);
//                mainIntake.setPosition(0.4);
//                sleep(2000);
//            } else if (gamepad2.left_bumper) {
//                mainIntake.setPosition(0.05);
//            } else if (gamepad2.right_bumper) {
//                mainIntake.setPosition(0.7);
            }
        }
    }
//}