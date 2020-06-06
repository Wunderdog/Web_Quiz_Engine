package engine;

import validator.QuizOptionsConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

//@QuizAnswerConstraint
public class Question {

    private int id;
    @NotNull(message = "Title must not be null")
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotNull(message = "Title must not be null")
    @NotBlank(message = "Text must not be blank")
    private String text;
    @NotNull(message = "options can not be null")
    @QuizOptionsConstraint
    private String[] options;
    private Answer answer = new Answer();


    public Question() {
        System.out.println("CONSTRUCTOR");

    }

    public Question(String title, String text, String[] options) {
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
        this.setAnswer(new int[0]);
    }

    public Question(String title, String text, String[] options, int[] answer) {
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
        this.setAnswer(answer);
    }

    public Question(int id, String title, String text, String[] options, int[] answer) {
        this.setId(id);
        this.setTitle(title);
        this.setText(text);
        this.setOptions(options);
        this.setAnswer(answer);
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getAnswer() {
        return answer.getAnswer();
    }

    public void setAnswer(int[] answer) {
        this.answer.setAnswer(answer);
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
