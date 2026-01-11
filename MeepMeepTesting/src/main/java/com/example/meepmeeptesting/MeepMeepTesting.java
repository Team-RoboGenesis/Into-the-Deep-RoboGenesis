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
                .setConstraints(50, 50, Math.toRadians(180), Math.toRadians(180), 17.5)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(6, -61, Math.toRadians(90)))
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(6, -20), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(6, -40), Math.toRadians(90))

                .splineTo(new Vector2d(23.96, -35.58), Math.toRadians(-12.99))
                .splineToSplineHeading(new Pose2d(53.55, -12.61, Math.toRadians(-45.00)), Math.toRadians(-45.00))
                .splineToSplineHeading(new Pose2d(47.55, -58, Math.toRadians(-90.00)), Math.toRadians(-90.00))
                .waitSeconds(0.2)
                //score

                .splineToSplineHeading(new Pose2d(47, -50, Math.toRadians(-90)), Math.toRadians(-90))
                .setTangent(Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(3, -40, Math.toRadians(90)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(3, -24, Math.toRadians(90)), Math.toRadians(90))
                        .waitSeconds(0.2)

                //third grab
                        .setReversed(true)
                .splineToConstantHeading(new Vector2d(3.48, -38.87), Math.toRadians(-75.00))
                .splineToSplineHeading(new Pose2d(25.47, -50.02, Math.toRadians(-22.17)), Math.toRadians(-22.17))
                .splineToSplineHeading(new Pose2d(47.77, -58.38, Math.toRadians(-90.00)), Math.toRadians(-90.00))
                        .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(39.30, -56.67, Math.toRadians(135.00)), Math.toRadians(135.00))
                .splineToSplineHeading(new Pose2d(3.38, -32.43, Math.toRadians(90.00)), Math.toRadians(90.00))
                .splineToConstantHeading(new Vector2d(3, -20), Math.toRadians(90))
                        .waitSeconds(0.3)
                .splineToConstantHeading(new Vector2d(3.48, -38.87), Math.toRadians(-75.00))
                .splineToSplineHeading(new Pose2d(25.47, -50.02, Math.toRadians(-22.17)), Math.toRadians(-22.17))
                .splineToSplineHeading(new Pose2d(47.77, -58.38, Math.toRadians(90.00)), Math.toRadians(90.00))
                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}