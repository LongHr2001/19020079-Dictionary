import javax.speech.AudioException;
import javax.speech.EngineException;
import javax.speech.synthesis.JSMLException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Render extends JFrame {
    private final String FRAME_TITLE = "19020079 - Dictionary";

    private final int FRAME_WIDTH = 1080;
    private final int FRAME_HEIGHT = 640;

    private JPanel panel;
    private SearchLabel search;
    private SearchField searchField;
    private DictionaryEntries dictionaryEntries;
    private MeaningWord meaningWord;
    private Meaning meaning;
    private Speaker speaker;

    private static Menu menu;

    private void prepareFrame() {
        setTitle(FRAME_TITLE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void prepareSearch() {
        search = new SearchLabel();
    }

    private void prepareSearchField() {
        searchField = new SearchField();
        DocumentListener searchFieldListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                dictionaryEntries.updateFromSearchField(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                dictionaryEntries.updateFromSearchField(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
        searchField.getDocument().addDocumentListener(searchFieldListener);
    }

    private void prepareDictionaryEntries() {
        dictionaryEntries = new DictionaryEntries();

        dictionaryEntries.getDictionaryEntries().addListSelectionListener(e -> {
            if(!dictionaryEntries.getDictionaryEntries().isSelectionEmpty()) {
                setMeaningSection(dictionaryEntries.getDictionaryEntries().getSelectedIndex());
            }
        });
    }

    private void prepareMeaningWord() {
        meaningWord = new MeaningWord();
    }

    private void prepareMeaning() {
        meaning = new Meaning();
    }

    private void createLayout() {
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);

        int searchWidth = search.getWidth();
        int searchHeight = search.getHeight();
        int searchFieldWidth = searchField.getWidth();
        int searchFieldHeight = searchField.getHeight();
        int dictionaryEntriesWidth = dictionaryEntries.getWidth();
        int dictionaryEntriesHeight = dictionaryEntries.getHeight();
        int meaningWordWidth = meaningWord.getWidth();
        int meaningWordHeight = meaningWord.getHeight();
        int meaningWidth = meaning.getWidth();
        int meaningHeight = meaning.getHeight();

        JScrollPane listScroll = dictionaryEntries.getListScroll();
        JScrollPane meaningScroll = meaning.getMeaningScroll();

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(search, searchWidth, searchWidth, searchWidth)
                                .addComponent(searchField, searchFieldWidth, searchFieldWidth, searchFieldWidth)
                        )
                        .addComponent(listScroll, dictionaryEntriesWidth, dictionaryEntriesWidth, dictionaryEntriesWidth)
                ).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(meaningWord, meaningWordWidth, meaningWordWidth, meaningWordWidth)
                                .addComponent(speaker))
                        .addComponent(meaningScroll, meaningWidth, meaningWidth, meaningWidth)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(search, searchHeight, searchHeight, searchHeight)
                        .addComponent(searchField, searchFieldHeight, searchFieldHeight, searchFieldHeight)
                        .addComponent(meaningWord, meaningWordHeight, meaningWordHeight, meaningWordHeight)
                        .addComponent(speaker)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listScroll, dictionaryEntriesHeight, dictionaryEntriesHeight, dictionaryEntriesHeight)
                        .addComponent(meaningScroll, meaningHeight, meaningHeight, meaningHeight)
                )
        );
    }

    private void createMenu() {
        menu = new Menu();
        menu.getDictionaryMenu().getAdd().addActionListener(e -> addWord());
        menu.getDictionaryMenu().getDelete().addActionListener(e -> deleteWord());
        menu.getDictionaryMenu().getEdit().addActionListener(e -> editWord());
    }

    private void createSpeaker() throws EngineException {
        speaker = new Speaker();

        speaker.addActionListener(e -> {
            try {
                speaker.speak(dictionaryEntries.getCurrentListModel().getElementAt(dictionaryEntries.getDictionaryEntries().getSelectedIndex()).getWordTarget());
            } catch (JSMLException | InterruptedException | EngineException | AudioException jsmlException) {
                jsmlException.printStackTrace();
            }
        });
    }

    public Render() throws EngineException {
        prepareFrame();

        panel = new JPanel();

        prepareSearch();
        panel.add(search);

        prepareSearchField();
        panel.add(searchField);

        prepareDictionaryEntries();
        panel.add(dictionaryEntries.getListScroll());

        prepareMeaningWord();
        panel.add(meaningWord);

        prepareMeaning();
        panel.add(meaning.getMeaningScroll());

        createSpeaker();
        panel.add(speaker);

        createLayout();

        add(panel);

        createMenu();
        add(menu, BorderLayout.PAGE_START);

        setVisible(true);
    }

    private void setMeaningSection (int index) {
        if(dictionaryEntries.getCurrentListModel() != null && !dictionaryEntries.getCurrentListModel().isEmpty()) {
            String wordEnglish = dictionaryEntries.getCurrentListModel().getElementAt(index).getWordTarget();
            String wordExplain = dictionaryEntries.getCurrentListModel().getElementAt(index).getWordExplain();

            meaningWord.setText(" " + wordEnglish);
            meaning.getMeaning().setText(wordExplain);
        } else {
            meaningWord.setText("");
            meaning.getMeaning().setText("");
        }
    }

    private void addWord() {
        if (menu.getDictionaryMenu().getAdd().addWord()) {
            dictionaryEntries.getDictionaryManagement().addToDictionary(menu.getDictionaryMenu().getAdd().getWordAdded());
            dictionaryEntries.updateEntriesList();
            dictionaryEntries.updateFromSearchField(searchField.getText());
        }
    }

    private void deleteWord() {
        if (menu.getDictionaryMenu().getDelete().deleteWordConfirmation()) {
            dictionaryEntries.getDictionaryManagement().dictionary.deleteWord(dictionaryEntries.getCurrentListModel().getElementAt(dictionaryEntries.getDictionaryEntries().getSelectedIndex()).getWordTarget());

            dictionaryEntries.updateEntriesList();
            dictionaryEntries.updateFromSearchField(searchField.getText());

            menu.getDictionaryMenu().getDelete().deleteSuccess();
        }
    }

    private void editWord() {
        if (menu.getDictionaryMenu().getEdit().editWord()) {
            dictionaryEntries.getDictionaryManagement().editWord(dictionaryEntries.getDictionaryManagement()
                            .dictionaryLookup(dictionaryEntries.getCurrentListModel().getElementAt(dictionaryEntries.getDictionaryEntries().getSelectedIndex())
                                    .getWordTarget()) + 1
                    , menu.getDictionaryMenu().getEdit().getNewWord(), menu.getDictionaryMenu().getEdit().getNewMeaning());
            dictionaryEntries.updateEntriesList();
            dictionaryEntries.updateFromSearchField(searchField.getText());
        }
    }
}
