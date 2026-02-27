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

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -61, Math.toRadians(90)))
                .waitSeconds(1)
                .lineToY(-24)
                .lineToY(-45)
                .strafeToLinearHeading(new Vector2d(46, -40), Math.toRadians(-90))
                .strafeTo(new Vector2d(46, 0))
                .strafeTo(new Vector2d(55, 0))
                .strafeTo(new Vector2d(55, -55))//move first sample into observation
                .strafeTo(new Vector2d(55, -49.4))
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -50), Math.toRadians(90)) //score second specimen
                .strafeTo(new Vector2d(7, -24))
                .strafeTo(new Vector2d(7, -40))
                .strafeToLinearHeading(new Vector2d(47, -45), Math.toRadians(-90))//score third specimen
                .waitSeconds(0.3)
                .strafeToLinearHeading(new Vector2d(7, -50), Math.toRadians(90)) //score second specimen
                .lineToY(-24)
                .strafeTo(new Vector2d(60, -60))
                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}