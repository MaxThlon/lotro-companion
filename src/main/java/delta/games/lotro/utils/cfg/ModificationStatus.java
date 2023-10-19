package delta.games.lotro.utils.cfg;

import java.util.BitSet;

/**
 * @author MaxThlon
 */
public class ModificationStatus extends BitSet {

  /**
   * 
   */
  public ModificationStatus() {
    super(ModificationStatusEnum.values().length-1);
  }

  /**
   * @param modification {@code ModificationStatusEnum}
   */
  public void set(ModificationStatusEnum modification) {
    this.set(modification.getValue());
  }

  /**
   * @param modification {@code ModificationStatusEnum}
   * @return true if modification is inside modificationStatus
   */
  public boolean contains(ModificationStatusEnum modification) {
    return this.get(modification.getValue());
  }
  
  /**
   * @param modifications {@code ModificationStatusEnum}
   * @return true if modification is inside modificationStatus
   */
  public boolean contains(ModificationStatusEnum... modifications) {
    for(ModificationStatusEnum modification:modifications) {
      if (this.get(modification.getValue())) return true;
    }
    return false;
  }
  
  /**
   * @return true if has modifications.
   */
  public boolean hasModifications() {
    return !this.isEmpty();
  }
}
