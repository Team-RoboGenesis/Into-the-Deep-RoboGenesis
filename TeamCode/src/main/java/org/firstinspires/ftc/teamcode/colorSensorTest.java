package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


@TeleOp
public class colorSensorTest extends LinearOpMode {

    public Servo mainIntake = null;
    public Servo led1 = null;
    ColorRangeSensor color;
public boolean isASample() {
    if (color.blue()>400& color.green()>400&color.red()>400&color.getDistance(DistanceUnit.MM)<30) {
       return true;
    }
    return false;
}
//int yellow = color.green();
//int red = color.red();
//int blue = color.blue();
public boolean yellow() { // see if yellow is the color of sample in claw
    if (color.getDistance(DistanceUnit.MM) < 30 & color.green() > color.blue() & color.green()> color.red() & color.green()>500) {
        return true;

    }
    return false;

} public boolean red() { // see if red is the color of sample in claw
    if (color.getDistance(DistanceUnit.MM) < 30 & color.red() > color.green() & color.red() > color.blue() & color.red() > 500) {
        return true;
    }
    return false;

    } public boolean blue() { // see if blue is the color of sample in claw
    if (color.getDistance(DistanceUnit.MM) < 30 & color.blue() > color.red() & color.blue() > color.green() & color.blue() > 500) {
        return true;
    }
    return false;
    } public boolean purple() {
    if (color.getDistance(DistanceUnit.MM)>30) {
        return true;
    }
    return false;
    }
    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorRangeSensor.class, "color");
        led1 = hardwareMap.get(Servo.class, "LED1");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.update();

//            led1.setPosition(0.7);//purple
//            led1.setPosition(0.6);//blue
//            led1.setPosition(0.35);//yellow
//            led1.setPosition(0.28);//red

            if (gamepad2.left_bumper) {
                mainIntake.setPosition(0.05);
            } else if (gamepad2.right_bumper) {
                mainIntake.setPosition(0.7);
            }
//            if (isASample()) {
//                mainIntake.setPosition(0.05);
//            }
            if (yellow()) {
                led1.setPosition(0.35);//yellow
            } else if (red()) {
                led1.setPosition(0.28);//red
            } else if (blue()) {
                led1.setPosition(0.6);//blue
            } else if (purple()) {
                led1.setPosition(0.71);
            }
        }
    }
}