package delta.games.lotro.gui.translation;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.tree.GenericTreeController;
import delta.common.ui.swing.tree.PanelTreeCellEditor;
import delta.common.ui.swing.tree.PanelTreeCellRenderer;
import delta.common.utils.i18n.MultilocalesTranslator;
import delta.common.utils.i18n.Translator;

/**
 * @author MaxThlon
 */
public class TranslationTreeController extends AbstractAreaController
{
  

  //Data
  //private TypedProperties _prefs;
  private TranslationManager _translationManager;
  private MultilocalesTranslator _multilocalesTranslator;
  

  //private List<MobDescription> _mobs;
  // UI
  private JTree _tree;
  private GenericTreeController _treeController;

  /**
   * Constructor
   * @param parent 
   */
  public TranslationTreeController(AreaController parent) {
    super(parent);
    _translationManager=TranslationManager.getInstance();
 
    _multilocalesTranslator=_translationManager.getApacheMultilocalesTranslator();

    _treeController=buildTree();
  }
  
  private void buildTranslationTableNode(DefaultTreeModel treeModel,
                                         DefaultMutableTreeNode sectionTreeNode,
                                         Set<Locale> locales,
                                         Set<String> translationKeys) {
    TranslationTableController translationTableController=
        new TranslationTableController(
            this,
            locales,
            translationKeys.stream()
                           .map((key) -> new Translation(_multilocalesTranslator, key))
                           .collect(Collectors.toList())
        );
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(
        //translations
        translationTableController
    );
    treeModel.insertNodeInto(
        treeNode,
        sectionTreeNode,
        sectionTreeNode.getChildCount()
    );
  }

  private GenericTreeController buildTree()
  {
    GenericTreeController tree=new GenericTreeController();
    JTree jTree=tree.getTree();
    PanelTreeCellRenderer panelTreeCellRenderer=new PanelTreeCellRenderer();
    jTree.setCellRenderer(panelTreeCellRenderer);
    jTree.setCellEditor(new PanelTreeCellEditor(jTree, panelTreeCellRenderer));

    DefaultTreeModel treeModel = (DefaultTreeModel)jTree.getModel();
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)treeModel.getRoot();

    Set<Locale> locales=_multilocalesTranslator.getLocales();

    Locale defaultLocal=locales.iterator().next();

    Translator translator=_multilocalesTranslator.getTranslator(defaultLocal);
    TranslatorEx translatorEx=(TranslatorEx)translator;

    DefaultMutableTreeNode sectionTreeNode=null;
    Set<String> sectionKeys=null;
    for (String key:translator.getKeys()) {
      String comment=  translatorEx.getComment(key);
      if ((sectionTreeNode == null) || (comment != null)) {
        
        if (sectionKeys != null) {
          buildTranslationTableNode(treeModel, sectionTreeNode, locales, sectionKeys);
        }
        
        sectionKeys=new LinkedHashSet<String>();
        
        sectionTreeNode = new DefaultMutableTreeNode(
            (comment == null)?"empty":comment
        );
        
        treeModel.insertNodeInto(
            sectionTreeNode,
            rootNode,
            rootNode.getChildCount()
        );
      }
      
      sectionKeys.add(key);
    }
    
    if ((sectionTreeNode != null) && (sectionKeys != null)) {
      buildTranslationTableNode(treeModel, sectionTreeNode, locales, sectionKeys);
    }
    
    jTree.expandPath(new TreePath(rootNode.getPath()));
    return tree;
  }
  
  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTreeController getTreeController()
  {
    return _treeController;
  }
  
  /**
   * Get the managed tree.
   * @return the managed tree.
   */
  public JTree getTree()
  {
    if (_tree==null)
    {
      _tree=_treeController.getTree();
    }
    return _tree;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    super.dispose();
    // Preferences
    /*if (_prefs!=null)
    {
      List<String> columnIds=_tableController.getColumnsManager().getSelectedColumnsIds();
      _prefs.setStringList(ItemChooser.COLUMNS_PROPERTY,columnIds);
      _prefs=null;
    }*/
    // GUI
    _tree=null;
    if (_treeController!=null)
    {
      _treeController.dispose();
      _treeController=null;
    }
    // Data
    _multilocalesTranslator=null;
    _translationManager=null;
  }
}
