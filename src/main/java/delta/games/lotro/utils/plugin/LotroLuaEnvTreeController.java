package delta.games.lotro.utils.plugin;

import java.util.UUID;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import delta.common.framework.lua.LuaModule;
import delta.common.framework.lua.LuaThreadState;
import delta.common.framework.module.Module;
import delta.common.framework.module.ModuleManager;
import delta.common.framework.plugin.PluginManager;
import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.tree.GenericTreeController;

/**
 * @author MaxThlon
 */
public class LotroLuaEnvTreeController extends AbstractAreaController
{
  // Manager
  private PluginManager _pluginManager;

  //Data
  //private TypedProperties _prefs;
  // UI
  private JTree _tree;
  private GenericTreeController _treeController;

  /**
   * Constructor
   * @param parent .
   * @param pluginManager .
   */
  public LotroLuaEnvTreeController(AreaController parent, PluginManager pluginManager) {
    super(parent);
    _pluginManager=pluginManager;
 
    _treeController=buildTree();
    init();
  }

  private GenericTreeController buildTree()
  {
    GenericTreeController tree=new GenericTreeController();
    _tree=tree.getTree();
    LuaEnvTreeCellRenderer luaEnvTreeCellRenderer=new LuaEnvTreeCellRenderer();
    _tree.setCellRenderer(luaEnvTreeCellRenderer);
    //jTree.setCellEditor(new PanelTreeCellEditor(jTree, panelTreeCellRenderer));

    return tree;
  }
  
  private void buildModuleNode(DefaultTreeModel treeModel,
                               DefaultMutableTreeNode sectionTreeNode,
                               Module module) {
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(module);
    treeModel.insertNodeInto(
        treeNode,
        sectionTreeNode,
        sectionTreeNode.getChildCount()
    );
    if (module instanceof LuaModule) {
      LuaModule luaModule = (LuaModule)module;
      for (LuaThreadState threadState:luaModule.getThreadStates()) {
        buildScriptStateNode(treeModel, treeNode, threadState);
      }
    }
  }
  
  private void buildScriptStateNode(DefaultTreeModel treeModel,
                                    DefaultMutableTreeNode sectionTreeNode,
                                    LuaThreadState threadState) {
    DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(threadState);
    treeModel.insertNodeInto(
        treeNode,
        sectionTreeNode,
        sectionTreeNode.getChildCount()
    );
  }
  
  private void reset()
  {
    if (_tree!=null)
    {
      _treeController.clear();
    }
  }

  /**
   * Refresh accounts table.
   */
  public void refresh()
  {
    reset();
    init();
    if (_tree!=null)
    {
      _treeController.reload();
    }
  }

  private void init()
  {
    ModuleManager moduleManager = ModuleManager.getInstance();
    DefaultTreeModel treeModel = (DefaultTreeModel)_tree.getModel();
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)treeModel.getRoot();
    for(UUID pluginModuleUuid:_pluginManager.getActivesPluginModules())
    {
      Module module = moduleManager.findModule(pluginModuleUuid);
      if (module != null) {
        
        buildModuleNode(treeModel, rootNode, module);
      }
    }
    
    //jTree.expandPath(new TreePath(rootNode.getPath()));
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
    // Manager
    _pluginManager = null;
  }
}
