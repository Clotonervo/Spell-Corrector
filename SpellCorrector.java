package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector  {

    public TrieTree trie;
    public Set<String> misspelledWords;


    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @param dictionaryFileName File containing the words to be used
     * @throws IOException If the file cannot be read
     */
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        Scanner input = new Scanner(new File(dictionaryFileName));
        trie = new TrieTree();
        while (input.hasNext()){
            String nextInput = input.nextLine();
            trie.add(nextInput.toLowerCase());
        }
        input.close();
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion or null if there is no similar word in the dictionary
     */
    @Override
    public String suggestSimilarWord(String inputWord)
    {
        misspelledWords = new TreeSet<String>();
        String suggestedWord = null;
        inputWord = inputWord.toLowerCase();
        if (trie.find(inputWord) != null){
            return inputWord;
        }
        else {
            makeWordList(inputWord);
        }

        suggestedWord = findSuggestedWord();
        if (suggestedWord == null){
            Set<String> misspelledCopy = new TreeSet<String>(misspelledWords);
            Iterator<String> wordItr = misspelledCopy.iterator();
            while (wordItr.hasNext()){
                String nextString = wordItr.next();
                makeWordList(nextString);
            }
            suggestedWord = findSuggestedWord();
            return suggestedWord;
        }
        else {
            return suggestedWord;
        }
    }

//-------------------------- SpellCorrector Functions ---------------------------------------------------

    private String findSuggestedWord()
    {
        Iterator<String> wordItr = misspelledWords.iterator();
        String suggestedWord = null;
        int suggestedNodeFrequency = 0;

        while(wordItr.hasNext()){
            String currWord = wordItr.next();
            WordNode currNode = (WordNode)(trie.find(currWord));
            boolean isEmpty = currNode == null;

            if (!isEmpty){
                if (currNode.frequency > suggestedNodeFrequency && currNode.frequency > 0) {
                    suggestedWord = currWord;
                    suggestedNodeFrequency = currNode.frequency;
                }
                else if (currNode.frequency == suggestedNodeFrequency){
                    int compare = suggestedWord.compareTo(currWord);
                    if(compare > 0) {
                        suggestedWord = currWord;
                        suggestedNodeFrequency = currNode.frequency;
                    }
                }
            }
        }

        if (suggestedWord == null){
            return null;
        }
        else {
            return suggestedWord;
        }
    }

//-------------------------- Making a word list ---------------------------------------------------
    private void makeWordList(String inputWord)
    {
        if (inputWord.length() == 1){
            deletion(inputWord);
            alteration(inputWord);
            insertion(inputWord);
        }
        else {
            deletion(inputWord);
            alteration(inputWord);
            transposition(inputWord);
            insertion(inputWord);
        }
    }

    private void deletion(String word) // Done
    {
        if (word.length() == 1){
            return;
        }
        for (int i = 0; i < word.length(); ++i){
            String wordCopy = word;
            wordCopy = wordCopy.substring(0, i) + wordCopy.substring(i + 1);

            misspelledWords.add(wordCopy);

        }

    }

    private void transposition(String word)// Done
    {
        for (int i = 0; i < word.length() - 1; ++i){
            String wordCopy = word;
            char[] currString = wordCopy.toCharArray();

            char tempChar = currString[i];
            currString[i] = currString[i + 1];
            currString[i + 1] = tempChar;

            wordCopy = new String(currString);

            misspelledWords.add(wordCopy);
        }
    }

    private void alteration(String word) // Done
    {
        for (int i = 0; i < word.length(); ++i){
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++){
                String wordCopy = word;
                char[] currString = wordCopy.toCharArray();
                currString[i] = alphabet;
                wordCopy = new String(currString);
                misspelledWords.add(wordCopy);
            }
        }

    }

    private void insertion(String word) // Done
    {
        for (int i = 0; i < word.length(); ++i){
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++){
                String wordCopy = word;
                wordCopy = wordCopy.substring(0, i) + alphabet + wordCopy.substring(i);
                misspelledWords.add(wordCopy);
            }
        }

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++){
            String wordCopy = word;
            wordCopy += alphabet;
            misspelledWords.add(wordCopy);
        }

    }
}
