package engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QuizBank {
    private Map<Integer, Question> questions = new HashMap<>();
    private int currentId = 1;

    public int addQuestion(Question newQuestion) {
        if (newQuestion.getId() == 0) {
            newQuestion.setId(currentId);
            currentId += 1;
        }
        questions.put(newQuestion.getId(), newQuestion);
        return newQuestion.getId();
    }

    public Question getQuestion(int id) {
        return questions.get(id);
    }

    public boolean containsId(int id) {
        return questions.containsKey(id);
    }

    public boolean checkAnswer(Answer answer, int id) {
        return answer.getAnswer().equals(questions.get(id).getAnswer());
    }

    public Question get(int id) {
        return questions.get(id);
    }

    public Collection<Question> questions() {
        return questions.values();
    }
}
