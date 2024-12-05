package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous")
public class Autonomous extends LinearOpMode {
    /**
     * @throws InterruptedException
     */
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Action move = drive.actionBuilder(beginPose)
                .turn(Math.toRadians(180))
                //.lineToY(10)
                .build();
        waitForStart();

        Actions.runBlocking(move);
    }
}
