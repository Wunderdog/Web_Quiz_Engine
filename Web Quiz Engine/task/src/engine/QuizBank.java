package engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QuizBank {
    private Map<Long, Question> questions = new HashMap<>();
    private long currentId = 1;

    public long addQuestion(Question newQuestion) {
        if (newQuestion.getId() == 0) {
            newQuestion.setId(currentId);
            currentId += 1;
        }
        questions.put(newQuestion.getId(), newQuestion);
        return newQuestion.getId();
    }

    public Question getQuestion(long id) {
        return questions.get(id);
    }

    public boolean containsId(long id) {
        return questions.containsKey(id);
    }

    public boolean checkAnswer(Answer answer, long id) {
        return answer.getAnswer().equals(questions.get(id).getAnswer());
    }

    public Question get(long id) {
        return questions.get(id);
    }

    public Collection<Question> questions() {
        return questions.values();
    }
}
