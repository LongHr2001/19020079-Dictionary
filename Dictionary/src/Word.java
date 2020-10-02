public class Word {
    private String wordTarget;
    private String wordExplain;

    /**
     *Constructor with 2 parameters
     * for both word and its explanation.
     */
    Word (String wordTarget, String wordExplain) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    /**
     *Constructor with 1 parameter
     * for the word only.
     */
    Word (String wordTarget) {
        this.wordTarget = wordTarget;
        this.wordExplain = "Meaning unknown";
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }
}
