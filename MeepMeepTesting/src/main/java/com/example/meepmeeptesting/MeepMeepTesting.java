package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(600, 600, Math.toRadians(180), Math.toRadians(180), 17.25)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(6, -61, Math.toRadians(90)))
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(6, -20), Math.toRadians(90))
//                .stopAndAdd(this::openClaw)
//                .stopAndAdd(this::pivotMiddlePos)
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))
//                .stopAndAdd(this::resetArm)

                //first sample push
//                TrajectoryActionBuilder firstsample = clearSubmersible.endTrajectory().fresh()
                        .splineTo(new Vector2d(24.50, -35.01), Math.toRadians(36.38))
                        .splineToSplineHeading(new Pose2d(44.05, -12.49, Math.toRadians(53.75)), Math.toRadians(53.75))
                        .turn(Math.toRadians(-90))
                        .splineToSplineHeading(new Pose2d(49.16, -58.81, Math.toRadians(-90.00)), Math.toRadians(-90.00))
                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}