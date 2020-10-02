import java.util.Objects;

public class Dictionary {
    private Word[] dict = new Word[1000000];
    private int currentSize = 0;

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setDictAtElement(int index, Word word) { this.dict[index] = word; }

    public Word getDictAtElement(int index) {
        return dict[index];
    }

    public void addWord(Word word) {
        this.dict[currentSize] = word;
        currentSize++;
    }

    public boolean deleteWord(String wordTarget) {
        boolean delete = false;

        for(int i = 0; i < currentSize; i++) {
            if(Objects.equals(dict[i].getWordTarget(), wordTarget)) {
                delete = true;

                if (currentSize + 1 - i + 1 >= 0)
                    System.arraycopy(dict, i + 1, dict, i + 1 - 1, currentSize + 1 - i + 1);

                currentSize--;
                break;
            }
        }

        return delete;
    }
}
