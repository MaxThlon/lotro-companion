package delta.games.lotro.gui.character.status.currencies;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ComboBoxItem;
import delta.common.ui.swing.windows.WindowController;
import delta.games.lotro.character.storage.currencies.Currency;

/**
 * Controller for a currency choice panel.
 * @author DAM
 */
public class CurrencyChoicePanelController
{
  // Data
  private List<Currency> _currencies;
  private List<Currency> _selectedCurrencies;
  // Controllers
  private WindowController _parent;
  private ComboBoxController<Currency> _currencySelector;
  // UI
  private JPanel _panel;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param currencies Currencies to use.
   * @param selectedCurrencies Selected currencies.
   */
  public CurrencyChoicePanelController(WindowController parent, List<Currency> currencies, List<Currency> selectedCurrencies)
  {
    _parent=parent;
    _currencies=new ArrayList<Currency>(currencies);
    _selectedCurrencies=new ArrayList<Currency>(selectedCurrencies);
    _currencySelector=buildCurrencyCombo(selectedCurrencies);
    _panel=buildPanel();
  }

  private ComboBoxController<Currency> buildCurrencyCombo(List<Currency> currencies)
  {
    ComboBoxController<Currency> ctrl=new ComboBoxController<Currency>();
    updateCurrencyCombo(ctrl,currencies);
    if (currencies.size()>0)
    {
      ctrl.selectItem(currencies.get(0));
    }
    return ctrl;
  }

  private void updateCurrencyCombo(ComboBoxController<Currency> ctrl, List<Currency> currencies)
  {
    List<ComboBoxItem<Currency>> items=new ArrayList<ComboBoxItem<Currency>>();
    for(Currency currency : currencies)
    {
      ComboBoxItem<Currency> item=new ComboBoxItem<Currency>(currency,currency.getName());
      items.add(item);
    }
    ctrl.updateItems(items);
  }

  private JButton buildCurrenciesChooserButton()
  {
    JButton ret=GuiFactory.buildButton("Choose currencies...");
    ActionListener al=new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        List<Currency> selectedCurrencies=CurrenciesChooserController.selectCurrencies(_parent,_currencies,_selectedCurrencies);
        if (selectedCurrencies!=null)
        {
          _selectedCurrencies.clear();
          _selectedCurrencies.addAll(selectedCurrencies);
          updateCurrencyCombo(_currencySelector,_selectedCurrencies);
        }
      }
    };
    ret.addActionListener(al);
    return ret;
  }

  private JPanel buildPanel()
  {
    JPanel ret=GuiFactory.buildPanel(new FlowLayout());
    ret.add(GuiFactory.buildLabel("Currency: "));
    ret.add(_currencySelector.getComboBox());
    JButton chooser=buildCurrenciesChooserButton();
    ret.add(chooser);
    return ret;
  }

  /**
   * Get the currency selector.
   * @return the currency selector.
   */
  public ComboBoxController<Currency> getCurrencySelector()
  {
    return _currencySelector;
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_currencySelector!=null)
    {
      _currencySelector.dispose();
      _currencySelector=null;
    }
  }
}
