package engine;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import validator.QuizOptionsConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@QuizAnswerConstraint
@Entity
public class Question implements Serializable {

    @Id
    @Column(name = "QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Title must not be null")
    @NotBlank(message = "Title must not be blank")
    private String title;
    @NotNull(message = "Title must not be null")
    @NotBlank(message = "Text must not be blank")
    private String text;

    @NotNull(message = "options can not be null")
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "question"
    )
    @Fetch(FetchMode.JOIN)
//    @QuizOptionsConstraint
    @Size(min = 2, message = "There must be at least 2 options")
//    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    private List<Option> options = new ArrayList<>();

    @OneToOne(
            cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER
//            orphanRemoval = true
            mappedBy = "question"
    )
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    private Answer answer = new Answer(this);

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

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Integer> getAnswer() {
        return answer.getAnswer();
    }

    public void setAnswer(int[] answer) {
        this.answer.setAnswer(answer);
    }

//    public void setAnswer(Answer answer) { this.answer = answer; }

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
        return options.stream().map(op -> op.getOption()).toArray(String[]::new);
    }

    public void setOptions(String[] options) {
        for (String option : options) {

            this.options.add(new Option(option));
        }
    }

//    public void setOptions(String[] options) {
//        this.options = Stream.of(options).map(op -> (new Option(){{
//                            setOption(op);
//        }})).collect(Collectors.toList());
//    }
}
