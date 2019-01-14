package com.game.ai;

import com.game.gameworld.GameObject;
import com.game.generics.Renderable;
import com.game.generics.Updateable;
import com.helper.BoundingBox;
import com.helper.Vector2f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Sensors implements Updateable, Renderable {
    private Map<SensorPosition, Sensor> sensorMap;
    public Sensors(BoundingBox prototype) {
        sensorMap = new HashMap<>();
        sensorMap.put(SensorPosition.ABYSSLEFT, new Sensor(prototype, SensorPosition.ABYSSLEFT));
        sensorMap.put(SensorPosition.ABYSSRIGHT, new Sensor(prototype, SensorPosition.ABYSSRIGHT));
    }

    @Override
    public void update() {
        for(Sensor sensor:sensorMap.values()) {
            sensor.update();
        }
    }

    @Override
    public void paint(Graphics g) {
        for(Sensor sensor : sensorMap.values()) {
            sensor.getBox().paint(g);
        }
    }


    public class Sensor implements Updateable {
        public BoundingBox getBox() {
            return box;
        }
        private BoundingBox other;
        private BoundingBox box;
        private SensorPosition sensorPosition;
        public Sensor(BoundingBox other, SensorPosition sensorPosition) {
            this.other = other;
            this.sensorPosition = sensorPosition;
        }

        @Override
        public void update() {
            switch(sensorPosition) {
                case ABYSSLEFT:
                    box = new BoundingBox(other.left() - 10, other.bottom(), 5, 10);
                    break;
                case ABYSSRIGHT:
                    box = new BoundingBox(other.right() + 5, other.bottom(), 5, 10);
                    break;
            }
        }
    }

    public enum SensorPosition {
        ABYSSLEFT, ABYSSRIGHT
    }
}
