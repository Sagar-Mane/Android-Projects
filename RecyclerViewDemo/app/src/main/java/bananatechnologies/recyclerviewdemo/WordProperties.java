package bananatechnologies.recyclerviewdemo;

/**
 * Created by Sagar Mane on 05-06-2017.
 */

public class WordProperties {

    private String text;
    private String lexical_category;
    private String definitions;

    //Parameterized Constructor
    public WordProperties(String text, String lexical_category, String definitions){
        this.text=text;
        this.lexical_category=lexical_category;
        this.definitions=definitions;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLexical_category() {
        return lexical_category;
    }

    public void setLexical_category(String lexical_category) {
        this.lexical_category = lexical_category;
    }

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }
}
