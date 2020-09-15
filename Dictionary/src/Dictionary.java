public class Dictionary {
    private Word[] dict = new Word[1000000];
    private int currentSize = 0;

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setDictAtElement(int index, Word word) {
        this.dict[index] = word;
    }

    public Word getDictAtElement(int index) {
        return dict[index];
    }
}
