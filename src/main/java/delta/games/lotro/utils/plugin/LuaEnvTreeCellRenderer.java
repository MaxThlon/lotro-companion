package delta.games.lotro.utils.plugin;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.lua.LotroLuaModule;
import delta.games.lotro.lua.turbine.LotroLuaScriptState;

/**
 * TableTreeViewCellRenderer library for lua environement.
 * @author MaxThlon
 */
public class LuaEnvTreeCellRenderer extends DefaultTreeCellRenderer {
  JPanel _scriptStatePanel;

  /**
   * 
   */
  public LuaEnvTreeCellRenderer() {
    super();
    _scriptStatePanel = buildScriptStatePanel();
  }

  private JPanel buildScriptStatePanel() {
    JPanel scriptStatePanel = GuiFactory.buildPanel(new BorderLayout());
    
    JLabel label = GuiFactory.buildLabel("scriptState name");
    scriptStatePanel.add(label);
    
    JButton unloadButton = GuiFactory.buildButton("unload");
    scriptStatePanel.add(unloadButton);
    
    return scriptStatePanel;
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean xselected,
                                                boolean expanded, boolean leaf, int row, boolean xhasFocus) {
    Component component = this;
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
    Object object = node.getUserObject();

    if (object instanceof LotroLuaModule) {
      LotroLuaModule module = (LotroLuaModule)node.getUserObject();

      setText(module.getName());
    } else if (object instanceof LotroLuaScriptState){
      LotroLuaScriptState scriptState = (LotroLuaScriptState)object;
      setText(String.join(".", scriptState.getNamespace()));
    } else {
      component=super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
      /*JLabel l = (JLabel)super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
      l.setFont(tree.getFont().deriveFont(leaf ? 16f : 48f));*/
      //height = tree.getRowHeight();
      //height = leaf ? 20 : 60;
    }
    return component;
  }
}
