package org.firstinspires.ftc.teamcode.Actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;

public class ScoreAction {
    public DcMotor left;
    public DcMotor right;
    public ScoreAction(DcMotor left, DcMotor right) {
        this.left = left;
        this.right = right;
    }
    public class scoreSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            left.setTargetPosition(710);
            right.setTargetPosition(710);
            return true;
        }
    }
    public Action scoreSpecimen () {
        return new scoreSpecimen();
    }
}