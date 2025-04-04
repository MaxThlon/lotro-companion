package delta.games.lotro.gui.utils;

import delta.common.ui.swing.navigator.PageIdentifier;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.gui.common.navigation.ReferenceConstants;
import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Factory for some commonly used gadgets controllers.
 * @author DAM
 */
public class GadgetsControllersFactory
{
  /**
   * Build a gadgets controller for a skill.
   * @param parent Parent window.
   * @param skill Skill to use.
   * @return the new gadgets controller.
   */
  public static IconLinkLabelGadgetsController build(WindowController parent, SkillDescription skill)
  {
    IconController icon=IconControllerFactory.buildSkillIcon(parent,skill);
    PageIdentifier pageId=ReferenceConstants.getSkillReference(skill.getIdentifier());
    String text=skill.getName();
    return new IconLinkLabelGadgetsController(parent,icon,text,pageId);
  }

  /**
   * Build a gadgets controller for a trait.
   * @param parent Parent window.
   * @param trait Trait to use.
   * @return the new gadgets controller.
   */
  public static IconLinkLabelGadgetsController build(WindowController parent, TraitDescription trait)
  {
    IconController icon=IconControllerFactory.buildTraitIcon(parent,trait);
    PageIdentifier pageId=ReferenceConstants.getTraitReference(trait.getIdentifier());
    String text=trait.getName();
    return new IconLinkLabelGadgetsController(parent,icon,text,pageId);
  }

  /**
   * Build a gadgets controller for an emote.
   * @param parent Parent window.
   * @param emote Emote to use.
   * @return the new gadgets controller.
   */
  public static IconLinkLabelGadgetsController build(WindowController parent, EmoteDescription emote)
  {
    IconController icon=IconControllerFactory.buildEmoteIcon(parent,emote);
    PageIdentifier pageId=ReferenceConstants.getEmoteReference(emote.getIdentifier());
    String text=emote.getName();
    return new IconLinkLabelGadgetsController(parent,icon,text,pageId);
  }
}
