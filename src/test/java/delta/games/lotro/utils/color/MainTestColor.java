package delta.games.lotro.utils.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * Test class for string rendering.
 * @author DAM
 */
public class MainTestColor
{
  private static final Logger LOGGER=Logger.getLogger(MainTestColor.class);

  private static Map<String, Color> _lotroColors;

  private static Map<String, Color> getLotroColors() {
    if (_lotroColors == null) {
      _lotroColors = new HashMap<String, Color>() {{
        put("Wheat", new Color(0.961f, 0.871f, 0.702f, 1f));
        put("DarkKhaki", new Color(0.741f, 0.718f, 0.42f, 1f));
        put("DarkGoldenrod", new Color(0.722f, 0.525f, 0.043f, 1f));
        put("YellowGreen", new Color(0.604f, 0.804f, 0.196f, 1f));
        put("PaleGreen", new Color(0.596f, 0.984f, 0.596f, 1f));
        put("SpringGreen", new Color(0f, 1f, 0.498f, 1f));
        put("AliceBlue", new Color(0.941f, 0.973f, 1f, 1f));
        put("OrangeRed", new Color(1f, 0.271f, 0f, 1f));
        put("SaddleBrown", new Color(0.545f, 0.271f, 0.075f, 1f));
        put("SlateBlue", new Color(0.416f, 0.353f, 0.804f, 1f));
        put("Aquamarine", new Color(0.498f, 1f, 0.831f, 1f));
        put("OliveDrab", new Color(0.42f, 0.557f, 0.137f, 1f));
        put("PapayaWhip", new Color(1f, 0.937f, 0.835f, 1f));
        put("LemonChiffon", new Color(1f, 0.98f, 0.804f, 1f));
        put("OldLace", new Color(0.992f, 0.961f, 0.902f, 1f));
        put("FloralWhite", new Color(1f, 0.98f, 0.941f, 1f));
        put("Maroon", new Color(0.502f, 0f, 0f, 1f));
        put("Linen", new Color(0.98f, 0.941f, 0.902f, 1f));
        put("DarkSlateGray", new Color(0.184f, 0.31f, 0.31f, 1f));
        put("Turquoise", new Color(0.251f, 0.878f, 0.816f, 1f));
        put("NavajoWhite", new Color(1f, 0.871f, 0.678f, 1f));
        put("Violet", new Color(0.933f, 0.51f, 0.933f, 1f));
        put("PaleVioletRed", new Color(0.859f, 0.439f, 0.576f, 1f));
        put("LightCoral", new Color(0.941f, 0.502f, 0.502f, 1f));
        put("Cornsilk", new Color(1f, 0.973f, 0.863f, 1f));
        put("Silver", new Color(0.753f, 0.753f, 0.753f, 1f));
        put("DarkCyan", new Color(0f, 0.545f, 0.545f, 1f));
        put("DarkOrchid", new Color(0.6f, 0.196f, 0.8f, 1f));
        put("AntiqueWhite", new Color(0.98f, 0.922f, 0.843f, 1f));
        put("Tomato", new Color(1f, 0.388f, 0.278f, 1f));
        put("IndianRed", new Color(0.804f, 0.361f, 0.361f, 1f));
        put("DarkBlue", new Color(0f, 0f, 0.545f, 1f));
        put("Sienna", new Color(0.627f, 0.322f, 0.176f, 1f));
        put("ForestGreen", new Color(0.133f, 0.545f, 0.133f, 1f));
        put("LightSlateGray", new Color(0.467f, 0.533f, 0.6f, 1f));
        put("BlanchedAlmond", new Color(1f, 0.922f, 0.804f, 1f));
        put("Crimson", new Color(0.863f, 0.078f, 0.235f, 1f));
        put("Moccasin", new Color(1f, 0.894f, 0.71f, 1f));
        put("LimeGreen", new Color(0.196f, 0.804f, 0.196f, 1f));
        put("HotPink", new Color(1f, 0.412f, 0.706f, 1f));
        put("PeachPuff", new Color(1f, 0.855f, 0.725f, 1f));
        put("DimGray", new Color(0.412f, 0.412f, 0.412f, 1f));
        put("Purple", new Color(0.502f, 0f, 0.502f, 1f));
        put("MediumSlateBlue", new Color(0.482f, 0.408f, 0.933f, 1f));
        put("Coral", new Color(1f, 0.498f, 0.314f, 1f));
        put("MediumBlue", new Color(0f, 0f, 0.804f, 1f));
        put("Red", new Color(1f, 0f, 0f, 1f));
        put("Teal", new Color(0f, 0.502f, 0.502f, 1f));
        put("MintCream", new Color(0.961f, 1f, 0.98f, 1f));
        put("Khaki", new Color(0.941f, 0.902f, 0.549f, 1f));
        put("DodgerBlue", new Color(0.118f, 0.565f, 1f, 1f));
        put("DarkOrange", new Color(1f, 0.549f, 0f, 1f));
        put("White", new Color(1f, 1f, 1f, 1f));
        put("DeepSkyBlue", new Color(0f, 0.749f, 1f, 1f));
        put("Orchid", new Color(0.855f, 0.439f, 0.839f, 1f));
        put("Ivory", new Color(1f, 1f, 0.941f, 1f));
        put("DarkMagenta", new Color(0.545f, 0f, 0.545f, 1f));
        put("LightCyan", new Color(0.878f, 1f, 1f, 1f));
        put("DeepPink", new Color(1f, 0.078f, 0.576f, 1f));
        put("MediumPurple", new Color(0.576f, 0.439f, 0.859f, 1f));
        put("LightSteelBlue", new Color(0.69f, 0.769f, 0.871f, 1f));
        put("Green", new Color(0f, 0.502f, 0f, 1f));
        put("DarkSeaGreen", new Color(0.561f, 0.737f, 0.545f, 1f));
        put("LightYellow", new Color(1f, 1f, 0.878f, 1f));
        put("Bisque", new Color(1f, 0.894f, 0.769f, 1f));
        put("Cyan", new Color(0f, 1f, 1f, 1f));
        put("Thistle", new Color(0.847f, 0.749f, 0.847f, 1f));
        put("LightPink", new Color(1f, 0.714f, 0.757f, 1f));
        put("SandyBrown", new Color(0.957f, 0.643f, 0.376f, 1f));
        put("LightSkyBlue", new Color(0.529f, 0.808f, 0.98f, 1f));
        put("Beige", new Color(0.961f, 0.961f, 0.863f, 1f));
        put("Aqua", new Color(0f, 1f, 1f, 1f));
        put("DarkGray", new Color(0.663f, 0.663f, 0.663f, 1f));
        put("PowderBlue", new Color(0.69f, 0.878f, 0.902f, 1f));
        put("Gainsboro", new Color(0.863f, 0.863f, 0.863f, 1f));
        put("Lime", new Color(0f, 1f, 0f, 1f));
        put("MediumVioletRed", new Color(0.78f, 0.082f, 0.522f, 1f));
        put("Salmon", new Color(0.98f, 0.502f, 0.447f, 1f));
        put("Firebrick", new Color(0.698f, 0.133f, 0.133f, 1f));
        put("RosyBrown", new Color(0.737f, 0.561f, 0.561f, 1f));
        put("Lavender", new Color(0.902f, 0.902f, 0.98f, 1f));
        put("Honeydew", new Color(0.941f, 1f, 0.941f, 1f));
        put("DarkSlateBlue", new Color(0.282f, 0.239f, 0.545f, 1f));
        put("WhiteSmoke", new Color(0.961f, 0.961f, 0.961f, 1f));
        put("Fuchsia", new Color(1f, 0f, 1f, 1f));
        put("DarkViolet", new Color(0.58f, 0f, 0.827f, 1f));
        put("Gold", new Color(1f, 0.843f, 0f, 1f));
        put("BlueViolet", new Color(0.541f, 0.169f, 0.886f, 1f));
        put("Chartreuse", new Color(0.498f, 0f, 1f, 1f));
        put("Tan", new Color(0.824f, 0.706f, 0.549f, 1f));
        put("LightGoldenrodYellow", new Color(0.98f, 0.98f, 0.824f, 1f));
        put("SkyBlue", new Color(0.529f, 0.808f, 0.922f, 1f));
        put("BurlyWood", new Color(0.871f, 0.722f, 0.529f, 1f));
        put("LavenderBlush", new Color(1f, 0.941f, 0.961f, 1f));
        put("MediumOrchid", new Color(0.729f, 0.333f, 0.827f, 1f));
        put("Magenta", new Color(1f, 0f, 1f, 1f));
        put("Blue", new Color(0f, 0f, 1f, 1f));
        put("DarkOliveGreen", new Color(0.333f, 0.42f, 0.184f, 1f));
        put("Brown", new Color(0.647f, 0.165f, 0.165f, 1f));
        put("Navy", new Color(0f, 0f, 0.502f, 1f));
        put("Black", new Color(0f, 0f, 0f, 1f));
        put("Pink", new Color(1f, 0.753f, 0.796f, 1f));
        put("Chocolate", new Color(0.824f, 0.412f, 0.118f, 1f));
        put("MediumTurquoise", new Color(0.282f, 0.82f, 0.8f, 1f));
        put("Indigo", new Color(0.294f, 0f, 0.51f, 1f));
        put("Orange", new Color(1f, 0.647f, 0f, 1f));
        put("Peru", new Color(0.804f, 0.522f, 0.247f, 1f));
        put("LightSalmon", new Color(1f, 0.627f, 0.478f, 1f));
        put("Yellow", new Color(1f, 1f, 0f, 1f));
        put("LightBlue", new Color(0.678f, 0.847f, 0.902f, 1f));
        put("PaleGoldenrod", new Color(0.933f, 0.91f, 0.667f, 1f));
        put("SteelBlue", new Color(0.275f, 0.51f, 0.706f, 1f));
        put("SeaShell", new Color(1f, 0.961f, 0.933f, 1f));
        put("Goldenrod", new Color(0.855f, 0.647f, 0.125f, 1f));
        put("DarkSalmon", new Color(0.914f, 0.588f, 0.478f, 1f));
        put("LightSeaGreen", new Color(0.125f, 0.698f, 0.667f, 1f));
        put("DarkGreen", new Color(0f, 0.392f, 0f, 1f));
        put("LightGreen", new Color(0.565f, 0.933f, 0.565f, 1f));
        put("MediumAquamarine", new Color(0.4f, 0.804f, 0.667f, 1f));
        put("MediumSeaGreen", new Color(0.235f, 0.702f, 0.443f, 1f));
        put("Plum", new Color(0.867f, 0.627f, 0.867f, 1f));
        put("DarkRed", new Color(0.545f, 0f, 0f, 1f));
        put("SlateGray", new Color(0.439f, 0.502f, 0.565f, 1f));
        put("GhostWhite", new Color(0.973f, 0.973f, 1f, 1f));
        put("DarkTurquoise", new Color(0f, 0.808f, 0.82f, 1f));
        put("Snow", new Color(1f, 0.98f, 0.98f, 1f));
        put("LawnGreen", new Color(0.486f, 0.988f, 0f, 1f));
        put("Gray", new Color(0.502f, 0.502f, 0.502f, 1f));
        put("Azure", new Color(0.941f, 1f, 1f, 1f));
        put("MidnightBlue", new Color(0.098f, 0.098f, 0.439f, 1f));
        put("Transparent", new Color(1f, 1f, 1f, 0f));
        put("MistyRose", new Color(1f, 0.894f, 0.882f, 1f));
        put("LightGray", new Color(0.827f, 0.827f, 0.827f, 1f));
        put("CornflowerBlue", new Color(0.392f, 0.584f, 0.929f, 1f));
        put("Olive", new Color(0.502f, 0.502f, 0f, 1f));
        put("RoyalBlue", new Color(0.255f, 0.412f, 0.882f, 1f));
        put("CadetBlue", new Color(0.373f, 0.62f, 0.627f, 1f));
        put("SeaGreen", new Color(0.18f, 0.545f, 0.341f, 1f));
        put("PaleTurquoise", new Color(0.686f, 0.933f, 0.933f, 1f));
        put("MediumSpringGreen", new Color(0f, 0.98f, 0.604f, 1f));
        put("GreenYellow", new Color(0f, 1f, 0.184f, 1f));
      }};
    }
    return _lotroColors;
  }

  private void doIt()
  {
    toFlatLaf();
  }
  
  /**
   * Write FlatLaf color definition to console.
   */
  public static void toFlatLaf()
  {
    String flatLafDefinition = getLotroColors().entrySet().stream().map(entry -> {
      int color = entry.getValue().getRGB();
      color = ((color << 8) & 0xffffff00) | ((color >> 24) & 0xff);
      return "@" + entry.getKey() + " = " + String.format("#%08x", Integer.valueOf(color));
    }).collect(Collectors.joining("\n"));
    
    LOGGER.error(flatLafDefinition);
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestColor().doIt();
  }
}
