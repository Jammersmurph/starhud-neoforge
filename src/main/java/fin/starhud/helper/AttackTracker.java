package fin.starhud.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.UUID;

public class AttackTracker {

    private static double reach = -1;
    private static long combo = -1;

    private static UUID entityUuid;
    private static long lastHitTime = -1;
    private static int lastHurtTime = 0;

    public static double getReach() {
        return reach;
    }

    public static long getCombo() {
        return combo;
    }

    public static void onEndTick(Minecraft client) {
        if (client.level == null) return;
        if (client.player == null) return;

        if (client.hitResult instanceof EntityHitResult ehr) {
            if (client.options.keyAttack.consumeClick()) {
                handleAttack(client.player, ehr);
            }
        }

        int currentHurtTime = client.player.hurtTime;
        if (currentHurtTime > 0 && lastHurtTime == 0) {
            if (combo != -1)
                combo = 0;
        }
        lastHurtTime = currentHurtTime;

        long now = client.level.getGameTime();
        if (lastHitTime != -1 && now - lastHitTime >= 4 * 20) {
            combo = -1;
            reach = -1;
            lastHitTime = now;
            entityUuid = null;
        }
    }

    private static void handleAttack(net.minecraft.world.entity.player.Player player, EntityHitResult hitResult) {
        Entity entity = hitResult.getEntity();
        long now = player.level().getGameTime();

        boolean sameTarget = entity.getUUID().equals(entityUuid);
        boolean cooldownExpired = now - lastHitTime >= 10;

        if (sameTarget && !cooldownExpired) return;

        reach = player.getEyePosition().distanceTo(hitResult.getLocation());

        if (sameTarget) {
            ++combo;
        } else {
            combo = 1;
            entityUuid = entity.getUUID();
        }
        lastHitTime = now;
    }
}
