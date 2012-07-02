package delta.games.lotro.items.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.common.Money;
import delta.games.lotro.common.money.io.xml.MoneyXMLWriter;
import delta.games.lotro.items.Item;
import delta.games.lotro.items.ItemBinding;
import delta.games.lotro.items.ItemCategory;
import delta.games.lotro.items.ItemSturdiness;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Writes LOTRO items to XML files.
 * @author DAM
 */
public class ItemXMLWriter
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static final String CDATA="CDATA";
  
  /**
   * Write an item to a XML file.
   * @param outFile Output file.
   * @param item Quest to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, Item item, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      write(hd,item);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }
  
  private void write(TransformerHandler hd, Item item) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    // ID
    String id=item.getIdentifier();
    if (id!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ID_ATTR,CDATA,id);
    }
    // Name
    String name=item.getName();
    if (name!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_NAME_ATTR,CDATA,name);
    }
    // Icon URL
    String iconURL=item.getIconURL();
    if (iconURL!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_ICON_URL_ATTR,CDATA,iconURL);
    }
    // Category
    ItemCategory category=item.getCategory();
    if (category!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_CATEGORY_ATTR,CDATA,category.name());
    }
    // Sub-category
    String subCategory=item.getSubCategory();
    if (subCategory!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_SUBCATEGORY_ATTR,CDATA,subCategory);
    }
    // Binding
    ItemBinding binding=item.getBinding();
    if (binding!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_BINDING_ATTR,CDATA,binding.name());
    }
    // Unique
    boolean unique=item.isUnique();
    itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_UNIQUE_ATTR,CDATA,String.valueOf(unique));
    // Durability
    Integer durability=item.getDurability();
    if (durability!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DURABILITY_ATTR,CDATA,String.valueOf(durability.intValue()));
    }
    // Sturdiness
    ItemSturdiness sturdiness=item.getSturdiness();
    if (sturdiness!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STURDINESS_ATTR,CDATA,sturdiness.name());
    }
    // Minimum level
    Integer minLevel=item.getMinLevel();
    if (minLevel!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_MINLEVEL_ATTR,CDATA,String.valueOf(minLevel.intValue()));
    }
    // Required class
    String requiredClass=item.getRequiredClass();
    if (requiredClass!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_REQUIRED_CLASS_ATTR,CDATA,requiredClass);
    }
    // Description
    String description=item.getDescription();
    if (description!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_DESCRIPTION_ATTR,CDATA,description);
    }
    // Stack max
    Integer stackMax=item.getStackMax();
    if (stackMax!=null)
    {
      itemAttrs.addAttribute("","",ItemXMLConstants.ITEM_STACK_MAX_ATTR,CDATA,String.valueOf(stackMax.intValue()));
    }
    hd.startElement("","",ItemXMLConstants.ITEM_TAG,itemAttrs);

    // Money
    Money value=item.getValue();
    MoneyXMLWriter.writeMoney(hd,value);
    // Bonuses
    List<String> bonuses=item.getBonus();
    if (bonuses!=null)
    {
      for(String bonus : bonuses)
      {
        AttributesImpl attrs=new AttributesImpl();
        attrs.addAttribute("","",ItemXMLConstants.BONUS_VALUE_ATTR,CDATA,bonus);
        hd.startElement("","",ItemXMLConstants.BONUS_TAG,attrs);
        hd.endElement("","",ItemXMLConstants.BONUS_TAG);
      }
    }
    hd.endElement("","",ItemXMLConstants.ITEM_TAG);
  }
}
