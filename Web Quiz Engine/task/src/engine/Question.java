package engine;

public class Question {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String title;
    private String text;
    private String[] options;
    private int answer;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }



    public Question() {
    }

    public Question(String title, String text, String[] options) {
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
    }

    public Question(String title, String text, String[] options, int answer) {
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
        this.setId(answer);
    }

    public Question(int id, String title, String text, String[] options, int answer) {
        this.setId(id);
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
        this.setAnswer(answer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
