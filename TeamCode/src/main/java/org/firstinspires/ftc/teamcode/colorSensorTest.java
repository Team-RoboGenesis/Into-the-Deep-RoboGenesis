package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class colorSensorTest extends LinearOpMode {

    public Servo mainIntake = null;
    // Define a variable for our color sensor
    ColorSensor color;

    @Override
    public void runOpMode() {
        // Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "Color");
        mainIntake = hardwareMap.get(Servo.class, "mainIntake");

        // Wait for the Play button to be pressed
        waitForStart();

        // While the Op Mode is running, update the telemetry values.
        while (opModeIsActive()) {
            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.update();

            if (color.blue()> 400) {
                mainIntake.setPosition(0.1);
                sleep(2000);
                mainIntake.setPosition(0.4);
                sleep(2000);
            } else if (gamepad2.left_bumper) {
                mainIntake.setPosition(0.05);
            } else if (gamepad2.right_bumper) {
                mainIntake.setPosition(0.7);
            }
        }
    }
}