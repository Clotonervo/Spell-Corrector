package spell;

public class TrieTree implements ITrie {

    private WordNode root;
    int nodeNum;
    int wordNum;

    public TrieTree()
    {
        root = new WordNode();
        nodeNum = 1;
        wordNum = 0;
    }

//-------------------------------- Add a Word to Dictionary -------------------------------------------------------
    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count
     *
     * @param word The word being added to the trie
     */
    public void add(String word)
    {
        if (word.isEmpty()){
            return;
        }
        else {
            addWord(word, root);
        }
    }


    private void addWord(String word, WordNode currNode)
    {
        char currChar = word.charAt(0);
        boolean isNodeEmpty = currNode.childrenArray[currChar -'a'] == null;
        boolean isLastCharacter = word.length() == 1;

        if (isNodeEmpty && isLastCharacter){
            currNode.childrenArray[currChar -'a'] = new WordNode();
            currNode.childrenArray[currChar -'a'].frequency++;
            nodeNum++;
            wordNum++;
        }
        else if (isNodeEmpty) {
            currNode.childrenArray[currChar -'a'] = new WordNode();
            word = word.substring(1, word.length());
            addWord(word, currNode.childrenArray[currChar -'a']);
            nodeNum++;
        }
        else if (!isNodeEmpty && isLastCharacter){
            if (currNode.childrenArray[currChar -'a'].frequency == 0){
                wordNum++;
            }
            currNode.childrenArray[currChar -'a'].frequency++;
        }
        else {
            word = word.substring(1, word.length());
            addWord(word, currNode.childrenArray[currChar -'a']);
        }
    }

//-------------------------------- Find Function and other little functions -------------------------------------------------------
    public INode find(String word)
    {
        return findRecursive(word, root);
    }

    private INode findRecursive(String word, WordNode currNode)
    {
        if(word.isEmpty()){
            return null;
        }

        char currChar = word.charAt(0);
        boolean isNodeEmpty = currNode.childrenArray[currChar - 'a'] == null;
        boolean isLastCharacter = word.length() == 1;

        if (isNodeEmpty && isLastCharacter){
            return null;
        }
        else if (isNodeEmpty) {
            return null;
        }
        else if (!isNodeEmpty && isLastCharacter){
            if (currNode.childrenArray[currChar - 'a'].frequency == 0){
                return null;
            }
            else {
                return currNode.childrenArray[currChar - 'a'];
            }
        }
        else {
            word = word.substring(1, word.length());
            return findRecursive(word, currNode.childrenArray[currChar - 'a']);
        }
    }

    /**
     * Returns the number of unique words in the trie
     *
     * @return The number of unique words in the trie
     */
    public int getWordCount()
    {
        return wordNum;
    }

    /**
     * Returns the number of nodes in the trie
     *
     * @return The number of nodes in the trie
     */
    public int getNodeCount()
    {
        return nodeNum;
    }


//-------------------------- Override toString -------------------------------------------------------------
    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     */
    @Override
    public String toString()
    {
        return toStringRecursion(root, "");
    }


    private String toStringRecursion(WordNode currNode, String currString)
    {
        StringBuilder tempStringBuilder = new StringBuilder();

        for (int i = 0; i < currNode.childrenArray.length; ++i){
            if (currNode.childrenArray[i] != null){
                if (currNode.childrenArray[i].frequency > 0){
                    int charVal = i + 'a';
                    char currentChar = (char) charVal;
                    currString += currentChar;
                    tempStringBuilder.append(currString + "\n");
                    tempStringBuilder.append(toStringRecursion(currNode.childrenArray[i], currString));
                    currString = currString.substring(0,currString.length() - 1);
                }
                else {
                    int charVal = i + 'a';
                    char currentChar = (char) charVal;
                    currString += currentChar;
                    tempStringBuilder.append(toStringRecursion(currNode.childrenArray[i], currString));
                    currString = currString.substring(0,currString.length() - 1);
                }

            }
        }


        return tempStringBuilder.toString();
    }

//-------------------------- Override hashCode() -------------------------------------------------------------

    @Override
    public int hashCode()
    {
        return (wordNum * nodeNum);
    }

//-------------------------- Override equals() -------------------------------------------------------------

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof TrieTree){
            if (((TrieTree) o).nodeNum != this.nodeNum){
                return false;
            }
            else if (((TrieTree) o).wordNum != this.wordNum){
                return false;
            }
            else if (o.hashCode() != this.hashCode()){
                return false;
            }
            else {
                return equalsRecursive(root, ((TrieTree) o).root);
            }
        }
        else {
            return false;
        }
    }

    private boolean equalsRecursive(WordNode currNode, WordNode currObjectNode)
    {
        for (int i = 0; i < currNode.childrenArray.length; ++i){
            if (currNode.childrenArray[i] != null) {
                if (currObjectNode.childrenArray[i] == null) {
                    return false;
                }
                else {
                    if (currNode.childrenArray[i].frequency == currObjectNode.childrenArray[i].frequency) {
                        if(equalsRecursive(currNode.childrenArray[i], currObjectNode.childrenArray[i])){
                            continue;
                        }
                        else{
                            return false;
                        }
                    }
                    else {
                        return false;
                    }
                }
            }
            else if (currObjectNode.childrenArray[i] != null){
                return false;
                }
            }
        return true;
    }









}
