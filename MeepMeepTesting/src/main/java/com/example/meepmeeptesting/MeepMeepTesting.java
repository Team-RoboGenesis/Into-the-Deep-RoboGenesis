package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(650);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.25)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(10, -61, Math.toRadians(90)))
                .lineToY(-22) //Score preloaded specimen
                .waitSeconds(2)
                .lineToY(-50)
                .waitSeconds(0.1)
                .splineTo(new Vector2d(50, -28), Math.toRadians(35))//Maneuver to 1st sample push
                .waitSeconds(3)
                .turn(Math.toRadians(-135))
                .lineToY(-55)//Push sample to observation zone
                .waitSeconds(0.1)
                .splineTo(new Vector2d(60, -28), Math.toRadians(35))//Maneuver to 2nd sample push
                .waitSeconds(0.1)
                .turn(Math.toRadians(-135))
                .lineToY(-55)//Push sample to observation zone
                .waitSeconds(0.5)
                .splineTo(new Vector2d(34, -51), Math.toRadians(-90))//Grab second specimen
                .waitSeconds(0.5)
                .splineTo(new Vector2d(10, -40), Math.toRadians(90))//score second specimen
                .waitSeconds(1)
                .lineToY(-50)
                .splineTo(new Vector2d(29, -45), 0)
                .splineTo(new Vector2d(47, -53), Math.toRadians(90))//park in observation zone
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}