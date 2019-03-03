package spell;

public class WordNode implements ITrie.INode {

    public WordNode[] childrenArray;
    public int frequency;

    public WordNode()
    {
        childrenArray = new WordNode[26];
        frequency = 0;
    }

    /**
     * Returns the frequency count for the word represented by the node
     *
     * @return The frequency count for the word represented by the node
     */
    public int getValue()
    {
        return frequency;
    }


}
