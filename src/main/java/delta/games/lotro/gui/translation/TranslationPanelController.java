package delta.games.lotro.gui.translation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.panels.AbstractPanelController;
import delta.common.ui.swing.panels.PanelController;
import delta.common.ui.swing.windows.WindowController;

/**
 * Controller for a panel that displays translations.
 * @author MaxThlon
 */
public class TranslationPanelController extends AbstractPanelController
{
  // Controllers
  // Data
  // UI
  private TranslationTreeController _translationTreeController;
  
  // Child windows manager

  /**
   * Constructor.
   * @param parent .
   */
  public TranslationPanelController(WindowController parent)
  {
    super(parent);
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
    JPanel panel = GuiFactory.buildPanel(new BorderLayout());
    // Tree
    _translationTreeController=new TranslationTreeController(this);
    JTree tree=_translationTreeController.getTree();
    // Whole panel
    TitledBorder border=GuiFactory.buildTitledBorder("Main");
    panel.setBorder(border);
    JScrollPane jScrollPane = GuiFactory.buildScrollPane(tree);
    jScrollPane.addComponentListener(new ComponentListener() {

      public void resizePanels(TreeNode root) {
        Enumeration<?> children = root.children();
        if (children != null) {
          while (children.hasMoreElements()) {
            DefaultMutableTreeNode node=(DefaultMutableTreeNode)children.nextElement();
            if (node.getUserObject() instanceof PanelController) {
              PanelController panelController=(PanelController)node.getUserObject();
              Dimension dimension=jScrollPane.getSize();
              dimension.width-=jScrollPane.getVerticalScrollBar().getPreferredSize().width * 2;
              dimension.height-=jScrollPane.getHorizontalScrollBar().getPreferredSize().height * 2;
              panelController.getPanel().setPreferredSize(dimension);
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
      public void componentShown(ComponentEvent e) {}

      @Override
      public void componentMoved(ComponentEvent e) {}

      @Override
      public void componentHidden(ComponentEvent e) {}
  });
    panel.add(jScrollPane);
    return panel;
  }

  /**
   * Set translations to display.
   */
  public void updateTranslations()
  {
  }

  /**
   * Update values.
   */
  public void update()
  {
    /*DefaultTreeModel treeModel = (DefaultTreeModel)jTree.getModel();
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)treeModel.getRoot();
    
    /int languageId = language.checkInteger();
    if (languageId > 268435456) languageId -= 268435456;/
    

    //LuaValue[] translations = new LuaValue[columnCount];
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(
        //translations
        _availableCfgs.getAppLabels()
    );
    treeModel.insertNodeInto(
        treeNode,
        rootNode,
        rootNode.getChildCount()
    );
    jTree.expandPath(new TreePath(rootNode.getPath()));*/
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
