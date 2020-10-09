import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Vector;

public class Render extends JFrame {
    private final String FRAME_TITLE = "19020079 - Dictionary";

    private final int FRAME_WIDTH = 810;
    private final int FRAME_HEIGHT = 540;

    private final String LABEL_SEARCH = "Search: ";
    private final int SEARCH_X = 10;
    private final int SEARCH_Y = 10;
    private final int SEARCH_WIDTH = 70;
    private final int SEARCH_HEIGHT = 20;

    private JTextArea meaningWord;
    private final int MEANINGWORD_X = 405;
    private final int MEANINGWORD_Y = 10;
    private final int MEANINGWORD_WIDTH = 385;
    private final int MEANINGWORD_HEIGHT = 45;

    private JTextArea meaning;
    private final int MEANING_X = 405;
    private final int MEANING_Y = 55;
    private final int MEANING_WIDTH = 385;
    private final int MEANING_HEIGHT = 475;

    private JTextArea searchField;
    private final int SEARCHFIELD_X = 70;
    private final int SEARCHFIELD_Y = 10;
    private final int SEARCHFIELD_WIDTH = 325;
    private final int SEARCHFIELD_HEIGHT = 20;

    private JList dictionaryEntries;
    private final int ENTRIES_X = 10;
    private final int ENTRIES_Y = 40;
    private final int ENTRIES_WIDTH = 385;
    private final int ENTRIES_HEIGHT = 490;


    DefaultListModel<Word> entriesList = new DefaultListModel();
    DictionaryManagement dictionaryManagement;
    Vector<Word> indexList = new Vector<>();

    DefaultListModel<Word> currentListModel = entriesList;


    public Render() {
        setTitle(FRAME_TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel search = new JLabel(LABEL_SEARCH);
        search.setBounds(SEARCH_X, SEARCH_Y, SEARCH_WIDTH, SEARCH_HEIGHT);
        search.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        add(search);

        meaningWord = new JTextArea();
        meaningWord.setBounds(MEANINGWORD_X, MEANINGWORD_Y, MEANINGWORD_WIDTH, MEANINGWORD_HEIGHT);
        meaningWord.setFont(new Font("helveticaneuebold", Font.PLAIN, 30));
        meaningWord.setForeground(Color.RED);
        meaningWord.setEditable(false);
        add(meaningWord);

        meaning = new JTextArea();
        meaning.setBounds(MEANING_X, MEANING_Y, MEANING_WIDTH, MEANING_HEIGHT);
        meaning.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        meaning.setEditable(false);
        add(meaning);

        searchField = new JTextArea();
        searchField.setBounds(SEARCHFIELD_X, SEARCHFIELD_Y, SEARCHFIELD_WIDTH, SEARCHFIELD_HEIGHT);
        searchField.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        DocumentListener searchFieldListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFromSearchField(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFromSearchField(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        searchField.getDocument().addDocumentListener(searchFieldListener);
        add(searchField);

        dictionaryManagement = new DictionaryManagement();

        int dictSize = dictionaryManagement.dictionary.getCurrentSize();

        for (int i = 0; i < dictSize; i++) {
            entriesList.addElement(dictionaryManagement.dictionary.getDictAtElement(i));
        }

        dictionaryEntries = new JList();
        dictionaryEntries.setBounds(ENTRIES_X, ENTRIES_Y, ENTRIES_WIDTH, ENTRIES_HEIGHT);
        dictionaryEntries.setModel(entriesList);
        JScrollPane listScroll = new JScrollPane(dictionaryEntries);
        dictionaryEntries.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        dictionaryEntries.setVisibleRowCount(19);
        dictionaryEntries.setFixedCellHeight(21);
        dictionaryEntries.setFixedCellWidth(ENTRIES_WIDTH);
        dictionaryEntries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        dictionaryEntries.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!dictionaryEntries.isSelectionEmpty()) {
                    setMeaningSection(dictionaryEntries.getSelectedIndex());
                }
            }
        });

        add(dictionaryEntries);
        add(listScroll);

        setVisible(true);
    }

    public void setMeaningSection (int index) {
        if(currentListModel != null && !currentListModel.isEmpty()) {
            String wordEnglish = currentListModel.getElementAt(index).getWordTarget();
            String wordExplain = currentListModel.getElementAt(index).getWordExplain();

            meaningWord.setText(" " + wordEnglish);
            meaning.setText("  " + wordExplain);
        } else {
            meaningWord.setText("");
            meaning.setText("");
        }
    }

    public JTextArea getMeaning() {
        return meaning;
    }

    public JList getList() {return dictionaryEntries;}

    public void addToEntriesList(Word word) {
        entriesList.addElement(word);
    }

    public void updateFromSearchField(String text) {
        if(!"".equals(text)) {
            indexList.clear();

            int dictSize = dictionaryManagement.dictionary.getCurrentSize();
            int textStringLength = text.length();

            for (int i = 0; i < dictSize; i++) {
                String word = dictionaryManagement.dictionary.getDictAtElement(i).getWordTarget();
                if (word.length() >= textStringLength) {
                    String wordSubString = word.substring(0, textStringLength);

                    if (wordSubString.equals(text)) {
                        indexList.add(dictionaryManagement.dictionary.getDictAtElement(i));
                    }
                }
            }
            if(!indexList.isEmpty()) {
                DefaultListModel<Word> tempList = new DefaultListModel<>();

                for(int i = 0; i < indexList.size(); i++) {
                    tempList.addElement(indexList.elementAt(i));
                }

                dictionaryEntries.setModel(tempList);
                currentListModel = tempList;
            } else {
                DefaultListModel tempList = new DefaultListModel();
                dictionaryEntries.setModel(tempList);
                currentListModel = tempList;
            }
        } else {
            dictionaryEntries.setModel(entriesList);
            currentListModel = entriesList;
        }
    }
}
