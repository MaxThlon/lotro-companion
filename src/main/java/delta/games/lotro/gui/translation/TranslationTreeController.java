package delta.games.lotro.gui.translation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import delta.common.framework.i18n.ApacheTranslator;
import delta.common.framework.i18n.event.LocalizedEntityUpdatedEvent;
import delta.common.framework.plugin.LocalizedPlugin;
import delta.common.framework.plugin.PluginManager;
import delta.common.framework.translation.LocalizedEntity;
import delta.common.ui.swing.area.AbstractAreaController;
import delta.common.ui.swing.area.AreaController;
import delta.common.ui.swing.tree.ExpandableMutableTreeNode;
import delta.common.ui.swing.tree.GenericTreeController;
import delta.common.ui.swing.tree.PanelTreeCellEditor;
import delta.common.ui.swing.tree.PanelTreeCellRenderer;
import delta.common.utils.i18n.MultilocalesTranslator;
import delta.common.utils.i18n.Translator;
import delta.games.lotro.client.plugin.Plugin;
import delta.games.lotro.utils.events.EventsManager;
import delta.games.lotro.utils.events.GenericEventsListener;

/**
 * @author MaxThlon
 */
public class TranslationTreeController extends AbstractAreaController 
                                       implements TreeWillExpandListener,
                                                  TreeSelectionListener,
                                                  GenericEventsListener<LocalizedEntityUpdatedEvent>
{
  // Manager
  PluginManager _pluginManager;

  //Data
  //private TypedProperties _prefs;
  //private TranslationManager _translationManager;
  private ArrayList<LocalizedEntity> _localizedEntities;
  private LocalizedEntity _selectedLLocalizedEntity;
  
  // UI
  private GenericTreeController _treeController;
  private PanelTreeCellRenderer _panelTreeCellRenderer;
  private JTree _tree;

  /**
   * Constructor
   * @param parent .
   * @param pluginManager .
   */
  public TranslationTreeController(AreaController parent, PluginManager pluginManager) {
    super(parent);
    _selectedLLocalizedEntity=null;

    //_translationManager=TranslationManager.getInstance();
    _localizedEntities = new ArrayList<LocalizedEntity>();
    _localizedEntities.add(new LocalizedApp("LotroCompanion"));

    _pluginManager=pluginManager;
    if (_pluginManager != null) {
      _pluginManager.refreshLocalizedPluginList();
      for (Plugin plugin:_pluginManager.getLocalizedPlugins()) {
        _localizedEntities.add(new LocalizedPlugin(plugin));
      }
    }
    _treeController=buildTree();
    EventsManager.addListener(LocalizedEntityUpdatedEvent.class, this);
  }
  
  private void buildLocalizedEntityTreeNode(DefaultTreeModel treeModel,
                                            DefaultMutableTreeNode treeNode,
                                            LocalizedEntity localizedEntity) {
    DefaultMutableTreeNode localizedEntityTreeNode = new ExpandableMutableTreeNode(localizedEntity);
    treeModel.insertNodeInto(
        localizedEntityTreeNode,
        treeNode,
        treeNode.getChildCount()
    );
  }
  
  private void updateLocalizedEntityTreeNode(DefaultTreeModel treeModel,
                                             DefaultMutableTreeNode localizedEntityTreeNode,
                                             LocalizedEntity localizedEntity) {
    MultilocalesTranslator multilocalesTranslator=localizedEntity.getMultilocalesTranslator();
    if (multilocalesTranslator != null) {
      Set<Locale> locales=multilocalesTranslator.getLocales();
      
      // Find the translator with most keys
      Translator translator=null;
      Set<String> keys=null;
      for (Locale locale:locales) {
        Translator currentTranslator=multilocalesTranslator.getTranslator(locale);
        Set<String> currentKeys=currentTranslator.getKeys();
        if ((translator == null) || (keys.size() < currentKeys.size())) {
          translator=currentTranslator;
          keys=currentKeys;
        }
      }

      buildSectionTreeNodes(
          (DefaultTreeModel)_tree.getModel(),
          localizedEntityTreeNode,
          localizedEntity,
          multilocalesTranslator,
          translator,
          locales
      );
    }
  }

  private void buildSectionTreeNodes(DefaultTreeModel treeModel,
                                     DefaultMutableTreeNode localizedEntityTreeNode,
                                     LocalizedEntity localizedEntity,
                                     MultilocalesTranslator multilocalesTranslator,
                                     Translator translator,
                                     Set<Locale> locales) {
    ApacheTranslator apacheTranslator=(ApacheTranslator)translator;
    DefaultMutableTreeNode sectionTreeNode=null;
    Set<String> sectionKeys=null;

    for (String key:translator.getKeys()) {
      String comment=apacheTranslator.getComment(key);
      if ((sectionTreeNode == null) || (comment != null)) {
        
        if (sectionKeys != null) {
          buildTranslationTableNode(treeModel, sectionTreeNode, localizedEntity, multilocalesTranslator, locales, sectionKeys);
        }
        
        sectionKeys=new LinkedHashSet<String>();
        sectionTreeNode = new DefaultMutableTreeNode(
            (comment == null)?"translations":comment
        );     
        treeModel.insertNodeInto(
            sectionTreeNode,
            localizedEntityTreeNode,
            localizedEntityTreeNode.getChildCount()
        );
      }
      
      sectionKeys.add(key);
    }

    if ((sectionTreeNode != null) && (sectionKeys != null)) {
      buildTranslationTableNode(treeModel, sectionTreeNode, localizedEntity, multilocalesTranslator, locales, sectionKeys);
    }
  }

  private void buildTranslationTableNode(DefaultTreeModel treeModel,
                                         DefaultMutableTreeNode sectionTreeNode,
                                         LocalizedEntity localizedEntity,
                                         MultilocalesTranslator multilocalesTranslator,
                                         Set<Locale> locales,
                                         Set<String> translationKeys) {
    TranslationTableController translationTableController=
        new TranslationTableController(
            this,
            localizedEntity,
            locales,
            translationKeys.stream()
                           .map((key) -> new Translation(multilocalesTranslator, key))
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
    _panelTreeCellRenderer=new TranslationPanelTreeCellRenderer();
    jTree.setCellRenderer(_panelTreeCellRenderer);
    jTree.setCellEditor(new PanelTreeCellEditor(jTree, _panelTreeCellRenderer));
    jTree.setRowHeight(0);
    jTree.addTreeWillExpandListener(this);
    jTree.addTreeSelectionListener(this);
    
    DefaultTreeModel treeModel = (DefaultTreeModel)jTree.getModel();
    DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)treeModel.getRoot();

    for (LocalizedEntity entity:_localizedEntities) {
      buildLocalizedEntityTreeNode(treeModel, rootNode, entity);
    }

    jTree.expandPath(new TreePath(rootNode.getPath()));
    return tree;
  }
  
  @Override
  public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();

    if ((node.getChildCount() == 0) && (node.getUserObject() instanceof LocalizedEntity)) {
      LocalizedEntity localizedEntity=(LocalizedEntity)node.getUserObject();
      
      if (localizedEntity.getMultilocalesTranslator() != null) {
        updateLocalizedEntityTreeNode((DefaultTreeModel)_tree.getModel(), node, localizedEntity);
      } else if (localizedEntity instanceof LocalizedPlugin) {
        LocalizedPlugin localizedPlugin=(LocalizedPlugin)localizedEntity;

        PluginManager.getInstance().loadPluginMultilocalesTranslator(localizedPlugin);
      }
    }
  }

  @Override
  public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException
  {
    // unused
  }
  
  @Override
  public void valueChanged(TreeSelectionEvent e)
  {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)_tree.getLastSelectedPathComponent();
    if (node != null) {
      if (node.getUserObject() instanceof LocalizedEntity) {
        _selectedLLocalizedEntity=(LocalizedEntity)node.getUserObject();
        return;
      } else if (node.getUserObject() instanceof TranslationTableController) {
        _selectedLLocalizedEntity=((TranslationTableController)node.getUserObject()).getLocalizedEntity();
        return;
      }
    }
    _selectedLLocalizedEntity=null;
  }
  
  @Override
  public void eventOccurred(LocalizedEntityUpdatedEvent event) {
    LocalizedEntity localizedEntity=event.getLocalizedEntity();
    DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)_tree.getModel().getRoot();
    DefaultMutableTreeNode node=null;
    
    // Find corresponding node
    Enumeration<?> children = rootNode.children();
    if (children != null) {
      while (((node == null) || node.getUserObject() != localizedEntity) && children.hasMoreElements()) {
        node=(DefaultMutableTreeNode)children.nextElement();
      }
      if ((node != null) && (node.getUserObject() == localizedEntity)) {
        DefaultTreeModel model=(DefaultTreeModel)_tree.getModel();
        updateLocalizedEntityTreeNode(model, node, localizedEntity);
        model.nodeChanged(node);
      }
    }
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
   * @return PanelTreeCellRenderer.
   */
  public PanelTreeCellRenderer getPanelTreeCellRenderer()
  {
    return _panelTreeCellRenderer;
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

  public LocalizedEntity getSelectedLLocalizedEntity()
  {
    return _selectedLLocalizedEntity;
  }
  

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    super.dispose();
    EventsManager.removeListener(LocalizedEntityUpdatedEvent.class, this);
    
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
    _localizedEntities=null;
  }
}
