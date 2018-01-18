package com.vb.ilt.systems.passive.collision;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.vb.ilt.entity.components.AnimationComponent;
import com.vb.ilt.entity.components.BoundsComponent;
import com.vb.ilt.entity.components.PositionComponent;
import com.vb.ilt.entity.components.npc.NPCComponent;
import com.vb.ilt.entity.components.stage.DialogTable;
import com.vb.ilt.systems.active.DialogSystem;
import com.vb.ilt.systems.active.HudRenderSystem;
import com.vb.ilt.systems.active.MovementSystem;
import com.vb.ilt.systems.active.PlayerControlSystem;
import com.vb.ilt.util.Mappers;

public class NPCCollisionSystem extends CollisionBase {
    private static final Logger log = new Logger(WorldWrapUpSystem.class.getName(), Logger.DEBUG);

    private final DialogTable dialogTable;

    private static final Family NPC = Family.all(
            NPCComponent.class,
            BoundsComponent.class
    ).get();

    public NPCCollisionSystem(DialogTable dialogTable) {
        super(NPC);
        this.dialogTable = dialogTable;
    }

    @Override
    public boolean checkCollision(Vector2 velocity) {
        ImmutableArray<Entity> npcs = getEngine().getEntitiesFor(NPC);
        Entity player = getEngine().getEntitiesFor(getPLAYER()).get(0);

        PositionComponent playerPos = Mappers.POSITION.get(player);
        BoundsComponent playerBounds = Mappers.BOUNDS.get(player);

        Polygon tempPolygon = new Polygon(playerBounds.polygon.getVertices());
        tempPolygon.setPosition(playerPos.x + velocity.x, playerPos.y + velocity.y);

        for (Entity npc : npcs) {
            BoundsComponent objectBounds = Mappers.BOUNDS.get(npc);
            if (contains(tempPolygon.getTransformedVertices(), objectBounds.polygon)) {
                Engine engine = getEngine();
                NPCComponent npcComponent = Mappers.NPC.get(npc);
                DialogSystem dialogSystem = engine.getSystem(DialogSystem.class);
                HudRenderSystem hudRenderSystem = engine.getSystem(HudRenderSystem.class);
                PlayerControlSystem playerControlSystem = engine.getSystem(PlayerControlSystem.class);
                MovementSystem movementSystem = engine.getSystem(MovementSystem.class);
                dialogSystem.setStageAndNpcType(dialogTable, npcComponent.type);
                dialogSystem.setProcessing(true);
                hudRenderSystem.setProcessing(false);
                movementSystem.setProcessing(false);
                playerControlSystem.setProcessing(false);
                AnimationComponent animation = Mappers.ANIMATION.get(player);
                animation.setAnimationIndex(0);
                log.debug("POP UP DIALOG FOR: " + npcComponent.type);
                return true;
            }
        }
        return false;
    }
}
