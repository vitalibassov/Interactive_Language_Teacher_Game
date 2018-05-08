package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.vb.ilt.common.TiledMapManager;
import com.vb.ilt.common.TiledMapObjectsProvider;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.SoundComponent;
import com.vb.ilt.entity.components.world.PortalSensorComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;
import com.vb.ilt.systems.active.SoundSystem;
import com.vb.ilt.systems.passive.CleanUpSystem;
import com.vb.ilt.systems.passive.EntityFactorySystem;
import com.vb.ilt.util.Mappers;

import java.util.Map;

public class SensorCollisionSystem extends CollisionBase{

    private final TiledMapManager mapManager;

    private static final Family SENSORS_FAMILY = Family.all(
            WorldObjectComponent.class,
            PortalSensorComponent.class,
            BoundsComponent.class,
            SoundComponent.class
    ).get();

    public SensorCollisionSystem(TiledMapManager mapManager) {
        super(SENSORS_FAMILY);
        this.mapManager = mapManager;
    }

    @Override
    public boolean checkCollision(Vector2 velocity) {
        ImmutableArray<Entity> sensors = getEngine().getEntitiesFor(SENSORS_FAMILY);
        Entity player = getEngine().getEntitiesFor(getPLAYER()).first();

        PositionComponent playerPos = Mappers.POSITION.get(player);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);

        Polygon tempPolygon = new Polygon(playerBounds.polygon.getVertices());
        tempPolygon.setPosition(playerPos.x + velocity.x, playerPos.y + velocity.y);

        for(Entity sensor : sensors){
            BoundsComponent objectBounds = Mappers.BOUNDS.get(sensor);
            if (contains(tempPolygon.getTransformedVertices(), objectBounds.polygon)){
                getEngine().getSystem(SoundSystem.class).playSound(sensor);
                String currentName = mapManager.getCurrentMap();
                TiledMapObjectsProvider provider = this.mapManager.getMapProvider(Mappers.PORTAL_SENSOR.get(sensor).name);
                cleanUpAndRepopulate(provider);
                setPlayerPosOnNewMap(playerPos, currentName, provider.getSpawnsNearSensors());
                return true;
            }
        }
        return false;
    }

    private void cleanUpAndRepopulate(TiledMapObjectsProvider provider) {
        EntityFactorySystem factory = getEngine().getSystem(EntityFactorySystem.class);
        CleanUpSystem cleanUp = getEngine().getSystem(CleanUpSystem.class);
        cleanUp.cleanUp();
        factory.createMap(provider.getMap());
        factory.createPortalSensors(provider.getSensors());
        factory.createPortalSensorSpawns(provider.getSpawnsNearSensors());
        factory.createNPCs(provider.getNpcSpawnPoints());
        factory.createCollisionObjects(provider.getCollisionObjects());
    }

    private void setPlayerPosOnNewMap(PositionComponent playerPos, String currentMapName, Map<Vector2, String> sensorSpawnPoints) {
        for (Map.Entry<Vector2, String> point : sensorSpawnPoints.entrySet()){
            if (point.getValue().equals(currentMapName)){
                playerPos.x = point.getKey().x;
                playerPos.y = point.getKey().y;
                break;
            }
        }
    }
}
