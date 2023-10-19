package delta.games.lotro.ui.pattern;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import delta.common.ui.swing.pattern.FlatLafGuiPattern;
import delta.common.ui.swing.theme.Theme;

/**
 * LotroGuiPattern.
 * @author MaxThlon
 */
public class LotroGuiPattern extends FlatLafGuiPattern {
  @Override
  protected boolean setupFlatLaf(String themeName) {
    return FlatDarkLaf.setup();
  }
  
  @Override
  public void initialize(Theme theme) {
    FlatLaf.registerCustomDefaultsSource("delta.games.lotro.gui.themes");
    super.initialize(theme);
  }
  
  @Override
  public void uninitialize() {
    super.uninitialize();
    FlatLaf.unregisterCustomDefaultsSource("delta.games.lotro.gui.themes");
  }
}

