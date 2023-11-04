package delta.games.lotro.utils.plugin;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import delta.common.framework.module.ModuleManager;
import delta.common.ui.swing.JFrame;
import delta.common.ui.swing.windows.DefaultWindowController;

/**
 * Controller for a "about" window.
 * @author DAM
 */
public class PluginWindowController extends DefaultWindowController
{
  /**
   * Window identifier.
   */
  public static final String IDENTIFIER="PLUGIN";

  // Controllers
  private PluginPanelController _controller;

  /**
   * Constructor.
   */
  public PluginWindowController()
  {
    super();
    _controller=new PluginPanelController(this);
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    // Title
    String title="Plugins"; // I18n
    frame.setTitle(title);
    frame.pack();
    frame.setMinimumSize(new Dimension(500,380));
    frame.setSize(new Dimension(700,500));
    return frame;
  }

  @Override
  public String getWindowIdentifier()
  {
    return IDENTIFIER;
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=_controller.getPanel();
    return panel;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    ModuleManager.getInstance().closeJobExecutor();
    if (_controller!=null)
    {
      _controller.dispose();
      _controller=null;
    }
    super.dispose();
  }
}
