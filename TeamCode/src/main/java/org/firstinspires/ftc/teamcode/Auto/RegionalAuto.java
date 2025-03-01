package org.firstinspires.ftc.teamcode.Auto;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous (name = "ParallelAuto")
public class RegionalAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        AutoActionController actionController = new AutoActionController(hardwareMap);

        //initialize bot position
        Pose2d beginPose = new Pose2d(6, -61, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        TrajectoryActionBuilder firstSpecimenScore = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(6, -22), Math.toRadians(90));

        TrajectoryActionBuilder clearSubmersible = firstSpecimenScore.fresh()
                .splineTo(new Vector2d(6, -35), Math.toRadians(90));

        TrajectoryActionBuilder firstSamplePush = clearSubmersible.fresh()
                .splineToConstantHeading(new Vector2d(46, -35), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(46, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(55, 0), Math.toRadians(90));

        TrajectoryActionBuilder secondSamplePush = firstSamplePush.fresh()
                .splineToConstantHeading(new Vector2d(58, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, -5), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(63, -5), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(60, -48), Math.toRadians(-90));

        TrajectoryActionBuilder secondSpecimenScore = secondSamplePush.fresh()
                .strafeToLinearHeading(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -21));

        TrajectoryActionBuilder thirdSpecimenGrab = secondSpecimenScore.fresh()
                .splineTo(new Vector2d(56, -49), Math.toRadians(-100));//score third specimen

        TrajectoryActionBuilder thirdSpecimenScore = thirdSpecimenGrab.fresh()
                .splineTo(new Vector2d(3, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -23));

        TrajectoryActionBuilder parkObservation = thirdSpecimenScore.fresh()
                .strafeTo(new Vector2d(4, -50))
                .strafeTo(new Vector2d(60, -60));

        Action firstSpecimen = firstSpecimenScore.build();
        Action escapeSubmersible = clearSubmersible.build();
        Action firstSample = firstSamplePush.build();
        Action secondSpecimen = secondSpecimenScore.build();
        Action thirdSpecGrab = thirdSpecimenGrab.build();
        Action thirdSpecimen = thirdSpecimenScore.build();
        Action secondSample = secondSamplePush.build();
        Action park = parkObservation.build();

        waitForStart();
        Actions.runBlocking(
                new SequentialAction(
                        actionController.scoreSpecimen(),
                        firstSpecimen,
                        new ParallelAction(
                                actionController.depositSpecimen(),
                                escapeSubmersible

                        ),
                        new ParallelAction(
                                actionController.resetArm(),
                                firstSample
                        )
                )
        );


    }
}
