package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.25)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -61, Math.toRadians(90)))
                .lineToY(-40) //Score preloaded specimen
                .waitSeconds(2)
                .splineTo(new Vector2d(30, -38), Math.toRadians(35))//Maneuver to 1st sample push
                .waitSeconds(3)//lower arm and grab sample
                .splineTo(new Vector2d(40, -50), Math.toRadians(-45))//Deliver sample to observation zone
                .waitSeconds(3)//let go of sample and move arm out of way
                .splineTo(new Vector2d(45, -38), Math.toRadians(48))//Maneuver to 2nd sample push
                .waitSeconds(3)//lower arm and grab sample
                .splineTo(new Vector2d(50, -47), Math.toRadians(-45))//Deliver sample to observation zone
                .waitSeconds(1)//let go of sample
                .splineTo(new Vector2d(47.1, -47), Math.toRadians(-90))//grab first specimen
                .waitSeconds(0.5)
                .splineTo(new Vector2d(10, -40), Math.toRadians(90))//score first specimen
                .waitSeconds(2)
                .splineTo(new Vector2d(10, -40), Math.toRadians(120))
                .splineTo(new Vector2d(-35, -35), Math.toRadians(180))//park in ascent zone
                .splineTo(new Vector2d(-26, -9), 0)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}