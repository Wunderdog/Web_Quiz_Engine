package engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfilter.EnableJsonFilter;
import com.jfilter.filter.FieldFilterSetting;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


@ComponentScan({"com.jfilter.components"})
@EnableJsonFilter
@RestController
public class QuestionController {
    private Map<Integer, Question> questions = new HashMap<>();
    private int currentId = 1;

    public QuestionController() {

    }

    Question testQuestion = new Question(
        "The Java Logo",
        "What is depicted on the Java logo?",
        new String[]{"Robot","Tea leaf","Cup of coffee","Bug"}
    );

    @PostMapping(path = "/api/quiz")
    public ResponseEntity<QuizResult> submitAnswer(@RequestParam(value = "answer") int answer) {
        if (answer == 2) {
            return new ResponseEntity<>(new QuizResult(true, "correct"), HttpStatus.OK);
        }
        return new ResponseEntity<>( new QuizResult(false, "wrong"), HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public ResponseEntity<Question> addQuestion(@RequestBody Question newQuestion) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
        if (newQuestion.getId() == 0) {
            newQuestion.setId(currentId);
            currentId += 1;
        }
        questions.put(newQuestion.getId(), newQuestion);
        return new ResponseEntity<>(questions.get(newQuestion.getId()), responseHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity<QuizResult> submitAnswerById(@PathVariable int id,
                                           @RequestParam int answer) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");

        if (!questions.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (answer == questions.get(id).getAnswer()) {
            return new ResponseEntity<>(new QuizResult(true, "correct"), responseHeaders, HttpStatus.OK);
        }
        System.out.println(String.format("%s = %s", answer, questions.get(id).getAnswer()));

        return new ResponseEntity<>( new QuizResult(false, "wrong"), responseHeaders, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quiz")
    public ResponseEntity<Question> getQuestion() {
        Question question = testQuestion;
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable int id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
        if (!questions.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions.get(id), responseHeaders, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quizzes")
    public ResponseEntity<Collection<Question>> getQuestionById() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");

        return new ResponseEntity<>(questions.values(), responseHeaders, HttpStatus.OK);
    }
}
