package delta.games.lotro.gui.about;

import javax.swing.JComponent;
import javax.swing.JPanel;

import delta.common.ui.swing.DeltaDialog;
import delta.common.ui.swing.DeltaWindow;
import delta.common.ui.swing.windows.DefaultDialogController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.gui.utils.l10n.Labels;

/**
 * Controller for a "about" window.
 * @author DAM
 */
public class AboutDialogController extends DefaultDialogController
{
  /**
   * Window identifier.
   */
  public static final String IDENTIFIER="ABOUT";

  // Controllers
  private AboutPanelController _controller;

  /**
   * Constructor.
   * @param parent Parent controller.
   */
  public AboutDialogController(WindowController parent)
  {
    super(parent);
    _controller=new AboutPanelController();
  }

  @Override
  protected DeltaDialog build()
  {
    DeltaDialog dialog=super.build();
    dialog.setTitle(Labels.getLabel("about.title"));
    dialog.pack();
    WindowController controller=getParentController();
    if (controller!=null)
    {
      DeltaWindow parentWindow=controller.getWindow();
      dialog.setLocationRelativeTo(parentWindow);
    }
    dialog.setResizable(false);
    return dialog;
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
    if (_controller!=null)
    {
      _controller.dispose();
      _controller=null;
    }
    super.dispose();
  }
}
