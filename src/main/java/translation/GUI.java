package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LanguageCodeConverter c1 = new LanguageCodeConverter();
            CountryCodeConverter c2 = new CountryCodeConverter();
            Translator translator = new JSONTranslator();

            JPanel countryPanel = new JPanel();
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String country : translator.getCountryCodes()){
                items[i++] = c2.fromCountryCode(country);
            }
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"), 0);

            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(c1.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);

            JPanel translatePanel = new JPanel();

            JLabel resultLabelText = new JLabel("Translation:");
            translatePanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            translatePanel.add(resultLabel);

            // add listener for when an item is selected.
            languageComboBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String country = c2.fromCountry(list.getSelectedValue().toString());
                        String language = c1.fromLanguage(languageComboBox.getSelectedItem().toString());

                        // for now, just using our simple translator, but
                        // we'll need to use the real JSON version later.

                        String result = translator.translate(country, language);
                        if (result == null) {
                            result = "no translation found!";
                        }
                        resultLabel.setText(result);
                    }
                }
            });
            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {

                    String country = c2.fromCountry(list.getSelectedValue().toString());
                    String language = c1.fromLanguage(languageComboBox.getSelectedItem().toString());

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translatePanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setSize(400, 200);
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
