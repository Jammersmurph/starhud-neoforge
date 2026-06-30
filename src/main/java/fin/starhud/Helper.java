package fin.starhud;

import fin.starhud.config.GeneralSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class Helper {

    private static final Minecraft CLIENT = Minecraft.getInstance();
    private static final GeneralSettings.HUDSettings HUD_SETTINGS = Main.settings.generalSettings.hudSettings;

    private static final char[] superscripts = "\u2070\u00B9\u00B2\u00B3\u2074\u2075\u2076\u2077\u2078\u2079".toCharArray();
    private static final char[] subscripts = "\u2080\u2081\u2082\u2083\u2084\u2085\u2086\u2087\u2088\u2089".toCharArray();

    public static String toSuperscript(String str) {
        char[] chars = str.toCharArray();

        int len = str.length();
        for (int i = 0; i < len; ++i) {
            char c = chars[i];

            if (c >= '0' && c <= '9')
                chars[i] = superscripts[c - '0'];
        }

        return new String(chars);
    }

    public static String toSubscript(String str) {
        char[] chars = str.toCharArray();

        int len = str.length();
        for (int i = 0; i < len; ++i) {
            char c = chars[i];

            if (c >= '0' && c <= '9')
                chars[i] = subscripts[c - '0'];
        }

        return new String(chars);
    }

    public static String idNameFormatter(String id) {
        id = id.substring(id.indexOf(':') + 1);

        char[] chars = id.toCharArray();

        if (chars.length == 0) return "-";

        chars[0] = Character.toUpperCase(chars[0]);
        for (int i = 1; i < chars.length; ++i) {
            if (chars[i] != '_') continue;

            chars[i] = ' ';

            if (i + 1 < chars.length) {
                chars[i + 1] = Character.toUpperCase(chars[i + 1]);
            }
        }

        return new String(chars);
    }

    public static boolean withinRange(int u, int v, int range) {
        int left = v - range;
        int right = v + range;

        return left <= u && u <= right;
    }

    public static int getItemBarColor(int stackStep, int maxStep) {
        return Mth.hsvToRgb(0.35F * stackStep / (float) maxStep, 0.45F, 0.95F);
    }

    public static float getGlobalScale() {
        if (HUD_SETTINGS.getGlobalScale() == 0) {
            return (float) CLIENT.getWindow().getGuiScale();
        }
        return HUD_SETTINGS.getGlobalScale();
    }

    public static String buildMinecraftTime24String(int hours, int minutes) {
        StringBuilder timeBuilder = new StringBuilder();

        if (hours < 10) timeBuilder.append('0');
        timeBuilder.append(hours).append(':');

        if (minutes < 10) timeBuilder.append('0');
        timeBuilder.append(minutes);

        return timeBuilder.toString();
    }

    public static String buildMinecraftTime12String(int hours, int minutes) {
        StringBuilder timeBuilder = new StringBuilder();

        String period = hours >= 12 ? " PM" : " AM";

        hours %= 12;
        if (hours == 0) hours = 12;

        timeBuilder.append(buildMinecraftTime24String(hours, minutes)).append(period);

        return timeBuilder.toString();
    }

    public static String getModName(net.minecraft.resources.ResourceLocation id) {
        return id.getNamespace();
    }

    public static int getStep(int value, int maxValue, int segments) {
        if (maxValue <= 0 || value <= 0) return 0;
        return Math.min(segments, (int) ((double) value / maxValue * segments));
    }
}
