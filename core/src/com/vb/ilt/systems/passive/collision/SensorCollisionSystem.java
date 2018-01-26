package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.common.TiledMapManager;
import com.vb.ilt.common.TiledMapObjectsProvider;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.world.PortalSensorComponent;
import com.vb.ilt.entity.components.world.PortalSensorSpawnComponent;
import com.vb.ilt.entity.components.world.TiledMapComponent;
import com.vb.ilt.entity.components.world.WorldObjectComponent;
import com.vb.ilt.systems.passive.CleanUpSystem;
import com.vb.ilt.systems.passive.EntityFactorySystem;
import com.vb.ilt.util.Mappers;

import java.util.Map;

public class SensorCollisionSystem extends CollisionBase{

    private static final Logger log = new Logger(SensorCollisionSystem.class.getName(), Logger.DEBUG);

    private final TiledMapManager mapManager;

    private static final Family SENSORS_FAMILY = Family.all(
            WorldObjectComponent.class,
            PortalSensorComponent.class,
            BoundsComponent.class,
            PositionComponent.class
    ).get();

    private static final Family SENSOR_SPAWN = Family.all(
            WorldObjectComponent.class,
            PortalSensorSpawnComponent.class,
            PositionComponent.class
    ).get();

    private static final Family MAP_FAMILY = Family.all(
            TiledMapComponent.class
    ).get();


    public SensorCollisionSystem(TiledMapManager mapManager) {
        super(SENSORS_FAMILY);
        this.mapManager = mapManager;
    }

    @Override
    public boolean checkCollision(Vector2 velocity) {
        ImmutableArray<Entity> sensors = getEngine().getEntitiesFor(SENSORS_FAMILY);
        Entity player = getEngine().getEntitiesFor(getPLAYER()).get(0);

        PositionComponent playerPos = Mappers.POSITION.get(player);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);

        Polygon tempPolygon = new Polygon(playerBounds.polygon.getVertices());
        tempPolygon.setPosition(playerPos.x + velocity.x, playerPos.y + velocity.y);

        for(Entity sensor : sensors){
            BoundsComponent objectBounds = Mappers.BOUNDS.get(sensor);
            if (contains(tempPolygon.getTransformedVertices(), objectBounds.polygon)){

                PortalSensorComponent portalSensor = Mappers.PORTAL_SENSOR.get(sensor);
                String portalSensorName = portalSensor.name;
                String currentMapName = mapManager.getCurrentMap();
                log.debug(currentMapName);
                CleanUpSystem cleanUp = getEngine().getSystem(CleanUpSystem.class);
                EntityFactorySystem factory = getEngine().getSystem(EntityFactorySystem.class);

                TiledMapObjectsProvider provider = this.mapManager.getMapProvider(portalSensorName);

                log.debug(portalSensorName);

                cleanUp.cleanUp();

                log.debug("PROVIDER: " + provider);

                Map<Vector2, String> sensorSpawnPoints = provider.getSpawnsNearSensors();

                factory.createMap(provider.getMap());
                factory.createPortalSensors(provider.getSensors());
                factory.createPortalSensorSpawns(sensorSpawnPoints);
                factory.createNPCs(provider.getNpcSpawnPoints());
                factory.createCollisionObjects(provider.getCollisionObjects());

                for (Map.Entry<Vector2, String> point : sensorSpawnPoints.entrySet()){
                    log.debug("CURRENT MAP NAME: " + currentMapName);
                    if (point.getValue().equals(currentMapName)){
                        log.debug("PRESUMPTIVE POSITION: X= " + point.getKey().x + "Y= " + point.getKey().y);
                        playerPos.x = point.getKey().x;
                        playerPos.y = point.getKey().y;
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
