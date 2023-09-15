package delta.games.lotro.gui;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import delta.common.ui.swing.DeltaDialog;
import delta.common.ui.swing.DeltaFrame;
import delta.common.ui.swing.DeltaWindow;
import delta.common.ui.swing.pattern.DefaultGuiPattern;
import delta.common.ui.swing.pattern.GuiPattern;
import delta.common.ui.swing.windows.DeltaJFrame;

/**
 * LotroGuiPattern.
 * @author MaxThlon
 */
public class LotroGuiPattern extends DefaultGuiPattern {

  /**
   * LotroCustomGuiFactory.
   * @author MaxThlon
   */
  public class LotroCustomGuiFactory implements GuiPattern.GuiPatternFactory {
    
    @Override
    public DeltaFrame buildFrame() {
      DeltaFrame frame = new DeltaJFrame();
      JInternalFrame jInternalFrame = new JInternalFrame("General", true);
      jInternalFrame.setPreferredSize(new Dimension(600, 200));
      jInternalFrame.setOpaque(false);
      jInternalFrame.pack();
      jInternalFrame.setVisible(true);
      frame.setContentPane(jInternalFrame);
      return frame;
    }
  
    @Override
    public DeltaDialog buildDialog(DeltaWindow owner) {
      // TODO Auto-generated method stub
      return null;
    }
  
    @Override
    public JPanel buildBackgroundPanel(LayoutManager layout) {
      // TODO Auto-generated method stub
      return null;
    }
  
    @Override
    public Border createBevelBorder() {
      // TODO Auto-generated method stub
      return null;
    }
  }

  @Override
  public Class<? extends GuiPatternFactory> getGuiPatternFactoryClass() {
    return LotroCustomGuiFactory.class;
  }

  @Override
  public void initialize()
  {
    FlatLotroLaf.setup();
  }
}

