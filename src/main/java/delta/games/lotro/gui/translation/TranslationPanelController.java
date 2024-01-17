package delta.games.lotro.gui.translation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import delta.common.framework.plugin.PluginManager;
import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.multicheckbox.MultiCheckboxController;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.panels.PanelController;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.gui.utils.l10n.Labels;
import delta.games.lotro.lua.turbine.TurbineLanguage;

/**
 * Controller for a panel that displays translations.
 * @author MaxThlon
 */
public class TranslationPanelController extends AbstractPanelController
{
  // Controllers
  private TranslationTreeController _translationTreeController;
  private ComboBoxController<TurbineLanguage> _translateOrigin;
  private MultiCheckboxController<TurbineLanguage> _translateTarget;
  private MultiCheckboxController<String> _translateSource;
  // Data
  private List<String> _translateOrigins;
  // UI

  /**
   * Constructor.
   * @param parent .
   */
  public TranslationPanelController(WindowController parent)
  {
    super(parent);
    _translateOrigins=Arrays.asList(new String[] {
        "Lotro-Companion",
        "Plugins",
        "lotro",
        "Google"
    });
    JPanel panel=build(parent);
    setPanel(panel);
  }

  private JPanel build(WindowController parent)
  {
    //JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    JPanel translationPanel=buildTranslationPanel();

    return translationPanel;
  }

  private JPanel buildTranslationPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    
    JPanel panelTree = GuiFactory.buildPanel(new BorderLayout());
    // Tree
    _translationTreeController=new TranslationTreeController(this, PluginManager.getInstance());
    JTree tree=_translationTreeController.getTree();
    // Whole panel
    TitledBorder border=GuiFactory.buildTitledBorder("Main");
    panelTree.setBorder(border);
    JScrollPane jScrollPane = GuiFactory.buildScrollPane(tree);
    jScrollPane.addComponentListener(new ComponentListener() {

      public void resizePanels(TreeNode root) {
        Dimension dimension=jScrollPane.getSize();
        dimension.width-=jScrollPane.getVerticalScrollBar().getPreferredSize().width * 2;
        dimension.width=(int)Math.round(jScrollPane.getViewport().getWidth() * 0.95);
        dimension.height=500;
        _translationTreeController.getPanelTreeCellRenderer().setPanelPreferredSize(dimension);

        Enumeration<?> children = root.children();
        if (children != null) {
          while (children.hasMoreElements()) {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)children.nextElement();
            if (node.getUserObject() instanceof PanelController) {
              if (tree.isEditing())tree.stopEditing();
              ((DefaultTreeModel)tree.getModel()).nodeChanged(node);
            } else {
              resizePanels(node);
            }
          }
        }
      }
      
      @Override
      public void componentResized(ComponentEvent e) {
        resizePanels((TreeNode)tree.getModel().getRoot());
      }

      @Override
      public void componentShown(ComponentEvent e) {
        // unused
      }

      @Override
      public void componentMoved(ComponentEvent e) {
        // unused
      }

      @Override
      public void componentHidden(ComponentEvent e) {
        // unused
      }
    });
    panelTree.add(jScrollPane);
    GridBagConstraints c=new GridBagConstraints(0,0,2,1,1.,1.,GridBagConstraints.WEST,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
    panel.add(panelTree, c);

    GridBagConstraints controlPanelConstraints=new GridBagConstraints(0,0,1,1,.0,.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,5,5),0,0);
    JPanel controlPanel=GuiFactory.buildPanel(new GridBagLayout());
    JLabel label=GuiFactory.buildLabel("translate from:");
    controlPanel.add(label, controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    _translateOrigin=buildTranslateOriginCombo();
    controlPanel.add(_translateOrigin.getComboBox(), controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    
    controlPanelConstraints.gridx=0;
    ++controlPanelConstraints.gridy;
    label=GuiFactory.buildLabel("to");
    controlPanel.add(label, controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    _translateTarget=buildTranslateTargetUI();
    controlPanel.add(_translateTarget.getPanel(), controlPanelConstraints);

    controlPanelConstraints.gridx=0;
    ++controlPanelConstraints.gridy;
    label=GuiFactory.buildLabel("Get translations from:");
    controlPanel.add(label, controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    _translateSource=buildTranslateSourceUI();
    controlPanel.add(_translateSource.getPanel(), controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    JButton translate=GuiFactory.buildButton(Labels.getLabel("translate"));
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        TranslationManager.getInstance().translate(
            _translationTreeController.getSelectedLLocalizedEntity(),
            _translateOrigin.getSelectedItem(),
            _translateTarget.getSelectedItems(),
            _translateSource.getSelectedItems()
        );
      }
    };
    translate.addActionListener(al);
    controlPanel.add(translate, controlPanelConstraints);
    ++controlPanelConstraints.gridx;
    c=new GridBagConstraints(3,0,1,1,.0,1.,GridBagConstraints.LINE_END,GridBagConstraints.VERTICAL,new Insets(0,0,0,0),0,0);
    panel.add(controlPanel, c);
    return panel;
  }
  
  /**
   * Build a controller for a combo box to choose an translate origin.
   * @return A new controller.
   */
  private ComboBoxController<TurbineLanguage> buildTranslateOriginCombo()
  {
    ComboBoxController<TurbineLanguage> ctrl=new ComboBoxController<TurbineLanguage>();
    ctrl.addEmptyItem("");
    for(TurbineLanguage turbineLanguage : TurbineLanguage.values())
    {
      ctrl.addItem(turbineLanguage, turbineLanguage.getLabel());
    }
    ctrl.selectItem(TurbineLanguage.ENGLISH);
    return ctrl;
  }
  
  private MultiCheckboxController<TurbineLanguage> buildTranslateTargetUI()
  {
    final MultiCheckboxController<TurbineLanguage> ret=new MultiCheckboxController<TurbineLanguage>();
    for(TurbineLanguage turbineLanguage : TurbineLanguage.values())
    {
      ret.addItem(turbineLanguage,turbineLanguage.getLabel());
      ret.setItemState(turbineLanguage,true);
    }
    return ret;
  }
  
  private MultiCheckboxController<String> buildTranslateSourceUI()
  {
    final MultiCheckboxController<String> ret=new MultiCheckboxController<String>();
    for (String source:_translateOrigins) {
      ret.addItem(source,source);
      ret.setItemState(source,true);
    }
    return ret;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    // Controllers
    // Inherited
    super.dispose();
  }
}
