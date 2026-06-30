package fin.starhud.helper;

import net.minecraft.client.Minecraft;

public class TPSTracker {

    private static final Minecraft CLIENT = Minecraft.getInstance();

    private static final int SAMPLE_SIZE = 20;
    private static final double[] tickTimes = new double[SAMPLE_SIZE];
    private static int tickIndex = 0;
    private static int validSamples = 0;
    private static long lastTickTime = -1;
    private static long lastLevelTime = -1;

    private static double tps = 20;
    private static double mspt = -1;

    public static double getTPS() {
        return tps;
    }

    public static double getMSPT() {
        return mspt;
    }

    public static void onLevelTimeUpdate(long totalLevelTime) {

        if (totalLevelTime == lastLevelTime) {
            return;
        }

        if (CLIENT.getSingleplayerServer() != null) {
            mspt = CLIENT.getSingleplayerServer().getAverageTickTimeNanos();
            tps = Math.clamp(1_000_000_000 / mspt, 0.0F, 20.0F);
            return;
        }

        long currTickTime = System.currentTimeMillis();
        long elapsedTicks = totalLevelTime - lastLevelTime;

        mspt = (double) (currTickTime - lastTickTime) / elapsedTicks;

        lastTickTime = currTickTime;
        lastLevelTime = totalLevelTime;

        tickTimes[tickIndex] = mspt;
        tickIndex = (tickIndex + 1) % SAMPLE_SIZE;
        if (validSamples < SAMPLE_SIZE) validSamples++;

        double total = 0;
        for (int i = 0; i < validSamples; i++) {
            total += tickTimes[i];
        }

        double avg = total / (double) validSamples;
        if (avg > 0)
            tps = (double) Math.round(Math.min(1000.0 / avg, 20.0) * 10) / 10;
    }
}
