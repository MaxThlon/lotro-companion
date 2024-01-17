package delta.games.lotro;

import java.util.Locale;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.JFrame;
import delta.common.utils.l10n.L10nConfiguration;
import delta.common.utils.l10n.LocalizedFormats;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.dat.misc.Context;
import delta.games.lotro.gui.LotroIconsManager;
import delta.games.lotro.gui.main.MainFrameController;
import delta.games.lotro.utils.cfg.ApplicationConfiguration;
import delta.games.lotro.utils.gui.LotroGuiPatternManager;

/**
 * Main for LOTRO companion.
 * @author DAM
 */
public class Main
{
  /**
   * Main method of LOTRO companion.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    // Init preferences
    GuiFactory.setPreferences(Config.getInstance().getPreferences());
    // Init GuiPatternManager
    GuiFactory.setGuiPatternManager(LotroGuiPatternManager.getInstance());
    GuiFactory.init();
    // Init l10n
    L10nConfiguration l10nCfg=ApplicationConfiguration.getInstance().getL10nConfiguration();
    LocalizedFormats.init(l10nCfg);
    Locale.setDefault(Locale.US);
    // Init app context
    Context.init(LotroCoreConfig.getMode());
    LotroCore.init();
    LotroIconsManager.initApplicationIcons();
    // Build main window
    MainFrameController controller=new MainFrameController();
    JFrame frame=controller.getFrame();
    frame.pack();
    frame.setVisible(true);
  }
}
