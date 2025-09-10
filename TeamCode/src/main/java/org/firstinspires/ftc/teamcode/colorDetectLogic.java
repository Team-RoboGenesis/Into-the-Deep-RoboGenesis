package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class colorDetectLogic extends OpMode {
    private double red = 0;
    private double blue = 0;
    private double green = 0;
    private boolean isGreenBall = false;
    private boolean isPurpleBall = false;
    private String motif = null;
    ColorSensor sort;
    private Servo turnTable;
    @Override
    public void init() {
        sort = hardwareMap.get(ColorSensor.class, "sort");
        turnTable = hardwareMap.get(Servo.class, "turnTable");
    }

    @Override
    public void loop() {
        if(green > red + 100) {
            isGreenBall = true;
            turnTable.setPosition(0.833333);
        } else {
            isGreenBall = false;
        }

        if(blue > 900 && blue < 2000 && green < 1000) {
            isPurpleBall = true;
            turnTable.setPosition(0.5);
        } else {
            isPurpleBall = false;
        }
        motif = "PGP";
        telemetry.addData("red: ", sort.red());
        telemetry.addData("blue: ", sort.blue());
        telemetry.addData("green: ", sort.green());
        telemetry.addData("Green ball? ", isGreenBall);
        telemetry.addData("purple ball? ", isPurpleBall);
        telemetry.addData("motif pattern:", motif);
        telemetry.update();
        red = sort.red();
        blue = sort.blue();
        green = sort.green();
    }
}
