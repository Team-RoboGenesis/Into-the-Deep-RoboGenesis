package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

public class RegionalAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        AutoActionController actionController = new AutoActionController(hardwareMap);

        //initialize bot position
        Pose2d postScorePose = new Pose2d(6, -22, Math.toRadians(90));
        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Action firstSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(6, -22), Math.toRadians(90))
                .build();

        Action clearSubmersible = drive.actionBuilder(drive.pose)
                .strafeToConstantHeading(new Vector2d(6, -40))
                .build();

        Action firstSamplePush = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(46, -40))
//                .splineTo(new Vector2d(46, 0), Math.toRadians(90))
//                .splineTo(new Vector2d(55, 0), Math.toRadians(90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))
                .strafeTo(new Vector2d(55, -46.2))
                .build();


        Action secondSpecimenScore = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -21))
                .build();

        Action thirdSpecimenGrab = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(56, -49), Math.toRadians(-100))//score third specimen
                .build();

        Action thirdSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(3, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -23))
                .build();

        Action parkObservation = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60))
                .build();

        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        actionController.scoreSpecimen(),
                        firstSpecimenScore,
                        new ParallelAction(
                                actionController.depositSpecimen(),
                                clearSubmersible

                        ),
                        new ParallelAction(
                                actionController.resetArm(),
                                firstSamplePush
                        )
                )
        );


    }
}
