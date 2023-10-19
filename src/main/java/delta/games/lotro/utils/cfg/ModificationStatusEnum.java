package delta.games.lotro.utils.cfg;

/**
 * @author MaxThlon
 */
public enum ModificationStatusEnum {

  /**
   * Modifications status none
   */
  MODIFICATION_STATUS_NONE(0),
  /**
   * Modifications status data path
   */
  MODIFICATION_STATUS_DATA_PATH(1),
  /**
   * Modifications status gui pattern factory
   */
  MODIFICATION_STATUS_GUI_PATTERN_FACTORY(2),
  /**
   * Modifications status gui pattern theme
   */
  MODIFICATION_STATUS_GUI_PATTERN_THEME(3),
  /**
   * Modifications status skin
   */
  MODIFICATION_STATUS_SKIN(4);
  
  private int _val;
  ModificationStatusEnum(int val)
  {
    _val = val;
  }
  
  /**
   * @return enum int value
   */
  public int getValue()
  {
    return _val;
  }
}
