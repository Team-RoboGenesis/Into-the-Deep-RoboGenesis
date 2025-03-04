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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.25)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(6, -61, Math.toRadians(90)))
                .strafeTo(new Vector2d(6, -22))
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))
                //first sample push
                .splineToConstantHeading(new Vector2d(40, -40), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45, -0),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -5), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58, -45), Math.toRadians(90))
                //second sample push
                .splineToConstantHeading(new Vector2d(58, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(53, -5), Math.toRadians(90), null,
                        new ProfileAccelConstraint(-5, 5))
                .strafeToLinearHeading(new Vector2d(65, -5), 0, null,
                        new ProfileAccelConstraint(-10, 10))
                                .setTangent(Math.toRadians(-90))
//                .stopAndAdd(this::wallGrab)
//                .stopAndAdd(this::openClaw)
                .splineToConstantHeading(new Vector2d(60, -53), 0)
                                .setTangent(0)
                //Score second specimen
                .waitSeconds(0.3)
                        .setReversed(true)
                .splineTo(new Vector2d(5, -45), Math.toRadians(-90))
                        .setReversed(false)
                .splineToConstantHeading(new Vector2d(5, -25), Math.toRadians(90))

                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}