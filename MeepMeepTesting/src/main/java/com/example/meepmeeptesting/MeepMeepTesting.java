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
                        .lineToY(-30)//score first specimen
                        .waitSeconds(0.5)
                        .lineToY(-40)//line up for first sample push
                        .splineTo(new Vector2d(37, -38), Math.toRadians(90))
                        .lineToY(-17)
                        .strafeTo(new Vector2d(46, -14))
                        .strafeTo(new Vector2d(46, -55))//move first sample into observation
                        .strafeTo(new Vector2d(46, -14))
                        .strafeTo(new Vector2d(55, -14))
                        .strafeTo(new Vector2d(55, -55))//move second sample into observation
                        .strafeTo(new Vector2d(55, -14))
                        .strafeTo(new Vector2d(64, -14))
                        .strafeTo(new Vector2d(64, -55))//move third sample into observation



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}