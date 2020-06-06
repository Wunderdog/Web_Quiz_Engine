package engine;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

public class Answer {

    @NotNull
    private Set<Integer> answer = new HashSet<>();

    public Set<Integer> getAnswer() {
        return this.answer;
    }

    public void setAnswer(int[] answer) {

        this.answer = Arrays.stream(answer).boxed().collect(Collectors.toSet());
        System.out.println(this.answer);
    }
}
