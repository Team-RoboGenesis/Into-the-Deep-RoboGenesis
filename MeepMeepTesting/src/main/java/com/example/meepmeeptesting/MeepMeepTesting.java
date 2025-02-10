package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
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
                .waitSeconds(1)
                .splineTo(new Vector2d(6, -22), Math.toRadians(90))
                .lineToY(-40)
                .strafeTo(new Vector2d(6, -46))
                .strafeTo(new Vector2d(46, -40))
//                .splineTo(new Vector2d(46, 0), Math.toRadians(90))
//                .splineTo(new Vector2d(55, 0), Math.toRadians(90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))
                .strafeTo(new Vector2d(55, -46))
                .turn(Math.toRadians(-180))
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(0, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(0, -21))
                .lineToY(-40)
                .splineTo(new Vector2d(56, -48.3), Math.toRadians(-100))//score third specimen
                        .waitSeconds(1)
                .splineTo(new Vector2d(3, -40), Math.toRadians(90))
                .strafeTo(new Vector2d(3, -23))
                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}