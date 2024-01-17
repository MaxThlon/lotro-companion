package delta.games.lotro.gui.translation;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import delta.common.framework.translation.LocalizedEntity;
import delta.common.ui.swing.tree.PanelTreeCellRenderer;

/**
 * TableTreeViewCellRenderer library for lua scripts.
 * @author MaxThlon
 */
public class TranslationPanelTreeCellRenderer extends PanelTreeCellRenderer {

  /**
   * 
   */
  public TranslationPanelTreeCellRenderer() {
    super();
  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean xselected,
                                                boolean expanded, boolean leaf, int row, boolean xhasFocus) {
    Component component;
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
    if (node.getUserObject() instanceof LocalizedEntity) {
      LocalizedEntity localizedEntity=(LocalizedEntity)node.getUserObject();
      component=super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
      setText(localizedEntity._name);
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
