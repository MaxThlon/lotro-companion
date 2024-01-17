package delta.games.lotro.gui.translation;

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
public class TranslationWindowController extends DefaultWindowController
{
  /**
   * Window identifier.
   */
  public static final String IDENTIFIER="TRANSLATION";

  // Controllers
  private TranslationPanelController _controller;

  /**
   * Constructor.
   */
  public TranslationWindowController()
  {
    super();
    _controller=new TranslationPanelController(this);
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    // Title
    String title="Translation"; // I18n
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
    ModuleManager.getInstance().dispose();
    if (_controller!=null)
    {
      _controller.dispose();
      _controller=null;
    }
    super.dispose();
  }
}
