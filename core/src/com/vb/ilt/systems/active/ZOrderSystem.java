package com.vb.ilt.systems.active;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.PlayerComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.ZOrderComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.util.Mappers;


public class ZOrderSystem extends EntitySystem{

    private static final Logger log = new Logger(ZOrderSystem.class.getName(), Logger.DEBUG);

    private static final int BEHIND_PLAYER = 0;
    private static final int IN_FRONT_PLAYER = 2;

    private static final Family PLAYER_FAMILY = Family.all(
            PositionComponent.class,
            ZOrderComponent.class,
            PlayerComponent.class
    ).get();

    private static final Family NPC_FAMILY = Family.all(
            PositionComponent.class,
            ZOrderComponent.class,
            NPCComponent.class
    ).get();

    @Override
    public void update(float deltaTime) {
        Entity player = getEngine().getEntitiesFor(PLAYER_FAMILY).first();
        ImmutableArray<Entity> npcs = getEngine().getEntitiesFor(NPC_FAMILY);

        PositionComponent playerPos = Mappers.POSITION.get(player);

        for(Entity npc : npcs){
            PositionComponent npcPos = Mappers.POSITION.get(npc);
            ZOrderComponent npcZ = Mappers.Z_ORDER.get(npc);

            if(npcPos.y > playerPos.y){
                npcZ.z = BEHIND_PLAYER;
            }else{
                npcZ.z = IN_FRONT_PLAYER;
            }
        }
    }
}
