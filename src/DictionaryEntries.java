import javax.swing.*;
import java.awt.*;

public class DictionaryEntries{
    private final int ENTRIES_WIDTH = 385;
    private final int ENTRIES_HEIGHT = 490;

    private JList dictionaryEntries;

    DefaultListModel<Word> entriesList = new DefaultListModel();
    private DictionaryManagement dictionaryManagement;

    private DefaultListModel<Word> currentListModel = entriesList;

    private JScrollPane listScroll;

    public DictionaryEntries() {
        dictionaryManagement = new DictionaryManagement();

        dictionaryEntries = new JList();

        updateEntriesList();
        dictionaryEntries.setModel(entriesList);
        dictionaryEntries.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        dictionaryEntries.setVisibleRowCount(19);
        dictionaryEntries.setFixedCellHeight(21);
        dictionaryEntries.setFixedCellWidth(ENTRIES_WIDTH);
        dictionaryEntries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listScroll = new JScrollPane(dictionaryEntries);
    }

    public int getHeight() {
        return ENTRIES_HEIGHT;
    }

    public int getWidth() {
        return ENTRIES_WIDTH;
    }

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public JList getDictionaryEntries() {
        return dictionaryEntries;
    }

    public JScrollPane getListScroll() {
        return listScroll;
    }

    public DefaultListModel<Word> getCurrentListModel() {
        return currentListModel;
    }

    public void updateFromSearchField(String text) {
        if (!"".equals(text)) {
            DefaultListModel<Word> resultList = dictionaryManagement.dictionarySearcher(text);
            dictionaryEntries.setModel(resultList);
            currentListModel = resultList;
        } else {
            dictionaryEntries.setModel(entriesList);
            currentListModel = entriesList;
        }
    }

    public void updateEntriesList() {
        int dictSize = dictionaryManagement.dictionary.getCurrentSize();

        entriesList.clear();

        for (int i = 0; i < dictSize; i++) {
            entriesList.addElement(dictionaryManagement.dictionary.getDictAtElement(i));
        }
    }
}
