import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.synthesis.JSMLException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Render extends JFrame {
    private final String FRAME_TITLE = "19020079 - Dictionary";

    private final int FRAME_WIDTH = 810;
    private final int FRAME_HEIGHT = 540;

    private JPanel panel;

    private JLabel search;
    private final String LABEL_SEARCH = "Search: ";
    private final int SEARCH_WIDTH = 70;
    private final int SEARCH_HEIGHT = 20;

    private JTextArea meaningWord;
    private final int MEANINGWORD_WIDTH = 325;
    private final int MEANINGWORD_HEIGHT = 45;

    private JTextArea meaning;
    private final int MEANING_WIDTH = 385;
    private final int MEANING_HEIGHT = 475;

    private JTextArea searchField;
    private final int SEARCHFIELD_WIDTH = 315;
    private final int SEARCHFIELD_HEIGHT = 20;

    private JList dictionaryEntries;
    private final int ENTRIES_WIDTH = 385;
    private final int ENTRIES_HEIGHT = 490;

    private static JMenuBar menu;

    private JMenu dictionaryMenu;
    private final String DICTIONARY_LABEL = "Dictionary";

    private JMenuItem add;
    private final String ADD_LABEL = "Add a Word...";
    private final String ADDWORD_PROMPT = "Enter word needed to be added: ";
    private final String ADDWORD_ERROR = "Word is not valid. Please try again.";
    private final String ADDWORD_ERROR_TITLE = "Word Adding Error";
    private final String ADDMEANING_PROMPT = "Enter meaning of the word: ";
    private final String ADDMEANING_ERROR = "Meaning is not valid. Please try again.";
    private final String ADDMEANING_ERROR_TITLE = "Meaning Adding Error";

    private JMenuItem delete;
    private final String DELETE_LABEL = "Delete this Word...";
    private final String DELETE_PROMPT = "Delete this Word? ";
    private final String DELETE_PROMPT_TITLE = "Delete Confirmation";
    private final String DELETE_SUCCESS = "Word deleted!";
    private final String DELETE_SUCCESS_TITLE = "Word Deleting Success";

    private JMenuItem edit;
    private final String EDITWORD_LABEL = "Edit this Word";
    private final String EDITWORD_PROMPT = "Enter the new word: ";
    private final String EDITWORD_ERROR = "Word is not valid. Please try again.";
    private final String EDITWORD_ERROR_TITLE = "Word Edit Error";
    private final String EDITMEANING_PROMPT = "Enter the new word's new meaning: ";
    private final String EDITMEANING_ERROR = "Word is not valid. Please try again.";
    private final String EDITMEANING_ERROR_TITLE = "Word Edit Error";

    private JButton speak;


    DefaultListModel<Word> entriesList = new DefaultListModel();
    DictionaryManagement dictionaryManagement;

    DefaultListModel<Word> currentListModel = entriesList;

    private void prepareFrame() {
        setTitle(FRAME_TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void prepareSearch() {
        search = new JLabel(LABEL_SEARCH);
        search.setFont(new Font("helveticaneue", Font.PLAIN, 15));
    }

    private void prepareSearchField() {
        searchField = new JTextArea();
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
            public void changedUpdate(DocumentEvent e) {}
        };
        searchField.getDocument().addDocumentListener(searchFieldListener);
    }

    private void prepareDictionaryEntries() {
        updateEntriesList();

        dictionaryEntries = new JList();
        dictionaryEntries.setModel(entriesList);
        dictionaryEntries.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        dictionaryEntries.setVisibleRowCount(19);
        dictionaryEntries.setFixedCellHeight(21);
        dictionaryEntries.setFixedCellWidth(ENTRIES_WIDTH);
        dictionaryEntries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        dictionaryEntries.addListSelectionListener(e -> {
            if(!dictionaryEntries.isSelectionEmpty()) {
                setMeaningSection(dictionaryEntries.getSelectedIndex());
            }
        });
    }

    private void prepareMeaningWord() {
        meaningWord = new JTextArea();
        meaningWord.setFont(new Font("helveticaneuebold", Font.PLAIN, 30));
        meaningWord.setForeground(Color.RED);
        meaningWord.setEditable(false);
    }

    private void prepareMeaning() {
        meaning = new JTextArea();
        meaning.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        meaning.setEditable(false);
    }

    private void createLayout() {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(search, SEARCH_WIDTH, SEARCH_WIDTH, SEARCH_WIDTH)
                                .addComponent(searchField, SEARCHFIELD_WIDTH, SEARCHFIELD_WIDTH, SEARCHFIELD_WIDTH)
                        )
                        .addComponent(dictionaryEntries, ENTRIES_WIDTH, ENTRIES_WIDTH, ENTRIES_WIDTH)
                ).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(meaningWord, MEANINGWORD_WIDTH, MEANINGWORD_WIDTH, MEANINGWORD_WIDTH)
                                .addComponent(speak))
                        .addComponent(meaning, MEANING_WIDTH, MEANING_WIDTH, MEANING_WIDTH)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(search, SEARCH_HEIGHT, SEARCH_HEIGHT, SEARCH_HEIGHT)
                        .addComponent(searchField, SEARCHFIELD_HEIGHT, SEARCHFIELD_HEIGHT, SEARCHFIELD_HEIGHT)
                        .addComponent(meaningWord, MEANINGWORD_HEIGHT, MEANINGWORD_HEIGHT, MEANINGWORD_HEIGHT)
                        .addComponent(speak)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(dictionaryEntries, ENTRIES_HEIGHT, ENTRIES_HEIGHT, ENTRIES_HEIGHT)
                        .addComponent(meaning, MEANING_HEIGHT, MEANING_HEIGHT, MEANING_HEIGHT)
                )
        );
    }

    private void createMenu() {
        menu = new JMenuBar();
        dictionaryMenu = new JMenu(DICTIONARY_LABEL);
        add = new JMenuItem(ADD_LABEL);
        delete = new JMenuItem(DELETE_LABEL);
        edit = new JMenuItem(EDITWORD_LABEL);

        add.addActionListener(e -> addWord());

        delete.addActionListener(e -> deleteWord());

        edit.addActionListener(e -> editWord());

        dictionaryMenu.setFont(new Font("helveticaneue", Font.PLAIN, 12));
        add.setFont(new Font("helveticaneue", Font.PLAIN, 12));
        delete.setFont(new Font("helveticaneue", Font.PLAIN, 12));
        edit.setFont(new Font("helveticaneue", Font.PLAIN, 12));

        dictionaryMenu.add(add);
        dictionaryMenu.add(delete);
        dictionaryMenu.add(edit);
        menu.add(dictionaryMenu);
    }

    private void createSpeaker() {
        Icon speakIcon = new ImageIcon("speakerIcon.png");
        speak = new JButton();
        speak.setOpaque(false);
        speak.setContentAreaFilled(false);
        speak.setBorderPainted(false);
        speak.setIcon(speakIcon);
        speak.setPreferredSize(new Dimension(30, 30));

        speak.addActionListener(e -> pronounceWord(currentListModel.getElementAt(dictionaryEntries.getSelectedIndex()).getWordTarget()));
    }

    public Render() {
        dictionaryManagement = new DictionaryManagement();

        prepareFrame();

        panel = new JPanel();

        prepareSearch();
        panel.add(search);

        prepareSearchField();
        panel.add(searchField);

        prepareDictionaryEntries();
        panel.add(dictionaryEntries);
        JScrollPane listScroll = new JScrollPane(dictionaryEntries);
        panel.add(listScroll);

        prepareMeaningWord();
        panel.add(meaningWord);

        prepareMeaning();
        panel.add(meaning);

        createSpeaker();
        panel.add(speak);

        createLayout();

        add(panel);

        createMenu();
        add(menu, BorderLayout.PAGE_START);

        setVisible(true);
    }

    private void setMeaningSection (int index) {
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

    private void updateFromSearchField(String text) {
        if (!"".equals(text)) {
            DefaultListModel<Word> resultList = dictionaryManagement.dictionarySearcher(text);
            dictionaryEntries.setModel(resultList);
            currentListModel = resultList;
        } else {
            dictionaryEntries.setModel(entriesList);
            currentListModel = entriesList;
        }
    }

    private void updateEntriesList() {
        int dictSize = dictionaryManagement.dictionary.getCurrentSize();

        entriesList.clear();

        for (int i = 0; i < dictSize; i++) {
            entriesList.addElement(dictionaryManagement.dictionary.getDictAtElement(i));
        }
    }

    private void addWord() {
        JOptionPane addWord = new JOptionPane();
        JLabel addWordPrompt = new JLabel(ADDWORD_PROMPT);
        addWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        String wordToAdd = addWord.showInputDialog(addWordPrompt);

        if(wordToAdd != null) {
            while (wordToAdd.length() == 0) {
                JFrame frame = new JFrame();
                JOptionPane addWordError = new JOptionPane();
                JLabel addWordErrorPrompt = new JLabel(ADDWORD_ERROR);
                addWordErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                addWordError.showMessageDialog(frame, addWordErrorPrompt, ADDWORD_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                wordToAdd = addWord.showInputDialog(addWordPrompt);
                if (wordToAdd == null) {
                    break;
                }
            }

            if(wordToAdd != null) {
                JOptionPane addMeaning = new JOptionPane();
                JLabel addMeaningPrompt = new JLabel(ADDMEANING_PROMPT);
                addMeaningPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

                String meaningToAdd = addMeaning.showInputDialog(addMeaningPrompt);

                if (meaningToAdd != null) {
                    while (meaningToAdd.length() == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane addMeaningError = new JOptionPane();
                        JLabel addMeaningErrorPrompt = new JLabel(ADDMEANING_ERROR);
                        addMeaningErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                        addMeaningError.showMessageDialog(frame, addMeaningErrorPrompt, ADDMEANING_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                        meaningToAdd = addMeaning.showInputDialog(addMeaningPrompt);
                        if (meaningToAdd == null) {
                            break;
                        }
                    }
                }
                if (meaningToAdd != null) {
                    dictionaryManagement.addToDictionary(wordToAdd, meaningToAdd);
                    updateEntriesList();
                    updateFromSearchField(searchField.getText());
                }
            }
        }
    }

    private void deleteWord() {
        JFrame frame = new JFrame();
        JOptionPane deleteWord = new JOptionPane();
        JLabel deleteWordPrompt = new JLabel(DELETE_PROMPT);
        deleteWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        int value = deleteWord.showConfirmDialog(frame, deleteWordPrompt, DELETE_PROMPT_TITLE, JOptionPane.YES_NO_OPTION);

        if (value == JOptionPane.YES_OPTION) {
            dictionaryManagement.dictionary.deleteWord(currentListModel.getElementAt(dictionaryEntries.getSelectedIndex()).getWordTarget());

            updateEntriesList();
            updateFromSearchField(searchField.getText());

            JOptionPane deleteSuccess = new JOptionPane();
            JLabel deleteSuccessPrompt = new JLabel(DELETE_SUCCESS);
            deleteSuccessPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
            deleteSuccess.showMessageDialog(frame, deleteSuccessPrompt, DELETE_SUCCESS_TITLE, JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void editWord() {
        JOptionPane editWord = new JOptionPane();
        JLabel editWordPrompt = new JLabel(EDITWORD_PROMPT);
        editWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        String wordEdited = editWord.showInputDialog(editWordPrompt);

        if(wordEdited != null) {
            while (wordEdited.length() == 0) {
                JFrame frame = new JFrame();
                JOptionPane editWordError = new JOptionPane();
                JLabel editWordErrorPrompt = new JLabel(EDITWORD_ERROR);
                editWordErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                editWordError.showMessageDialog(frame, editWordErrorPrompt, EDITWORD_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                wordEdited = editWord.showInputDialog(editWordPrompt);
                if (wordEdited == null) {
                    break;
                }
            }

            if(wordEdited != null) {
                JOptionPane editMeaning = new JOptionPane();
                JLabel editMeaningPrompt = new JLabel(EDITMEANING_PROMPT);
                editMeaningPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

                String meaningEdited = editMeaning.showInputDialog(editMeaningPrompt);

                if (meaningEdited != null) {
                    while (meaningEdited.length() == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane editMeaningError = new JOptionPane();
                        JLabel editMeaningErrorPrompt = new JLabel(EDITMEANING_ERROR);
                        editMeaningErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                        editMeaningError.showMessageDialog(frame, editMeaningErrorPrompt, EDITMEANING_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                        meaningEdited = editMeaning.showInputDialog(editMeaningPrompt);
                        if (meaningEdited == null) {
                            break;
                        }
                    }
                }
                if (meaningEdited != null) {
                    dictionaryManagement.editWord(dictionaryManagement.dictionaryLookup(currentListModel
                                    .getElementAt(dictionaryEntries.getSelectedIndex()).getWordTarget()) + 1
                            , wordEdited, meaningEdited);
                    updateEntriesList();
                    updateFromSearchField(searchField.getText());
                }
            }
        }
    }

    private void pronounceWord(String word) {
        try {
            Speaker.speak(word);
        } catch (JSMLException | InterruptedException | EngineException | AudioException e) {
            e.printStackTrace();
        }
    }
}
