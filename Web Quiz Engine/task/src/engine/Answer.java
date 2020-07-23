package engine;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Answer {

    @Id
    @NotNull
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QUESTION_ID")
    private long id;

//    @NotNull
    @ElementCollection
    private Set<Integer> answer = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    @MapsId
    private Question question;

    public Answer(){}

    public Answer(Question question) { this.question = question; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public void setAnswer(Set<Integer> answer) {
//        this.answer = answer;
//    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<Integer> getAnswer() {
        return this.answer;
    }

    public void setAnswer(int[] answer) {

        this.answer = Arrays.stream(answer).boxed().collect(Collectors.toSet());
        System.out.println(this.answer);
    }
}
