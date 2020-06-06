package engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfilter.EnableJsonFilter;
import com.jfilter.filter.FieldFilterSetting;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ComponentScan({"com.jfilter.components"})
@EnableJsonFilter
@RestController
public class QuestionController {

    private QuizBank quizBank = new QuizBank();
    private final HttpHeaders JSON_RESPONSE_HEADERS = new HttpHeaders() {{
        add("Content-Type", "application/json");
    }};

    public QuestionController() {}

    Question testQuestion = new Question(
        "The Java Logo",
        "What is depicted on the Java logo?",
        new String[]{"Robot","Tea leaf","Cup of coffee","Bug"}
    );

    @PostMapping(path = "/api/quiz")
    public ResponseEntity<QuizResult> submitAnswer(@RequestParam(value = "answer") int answer) {
        if (answer == 2) {
            return new ResponseEntity<QuizResult>(new QuizResult(true, "correct"), HttpStatus.OK);
        }
        return new ResponseEntity<QuizResult>( new QuizResult(false, "wrong"), HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public ResponseEntity addQuestion(@RequestBody @Valid Question newQuestion) throws JsonProcessingException {
        int id = quizBank.addQuestion(newQuestion);
        return new ResponseEntity(quizBank.getQuestion(id), JSON_RESPONSE_HEADERS, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve", consumes = "application/json")
    public ResponseEntity submitAnswerById(@PathVariable int id,
                                           @Valid @RequestBody Answer answer) throws ParseException {

        if (!quizBank.containsId(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (quizBank.checkAnswer(answer, id)) {
            return new ResponseEntity(new QuizResult(true, "correct"),
                    JSON_RESPONSE_HEADERS, HttpStatus.OK);
        }
        return new ResponseEntity( new QuizResult(false, "wrong"),
                JSON_RESPONSE_HEADERS, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quiz")
    public ResponseEntity<Question> getQuestion() {
        Question question = testQuestion;
        return new ResponseEntity<Question>(question, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity getQuestionById(@PathVariable int id) throws JsonProcessingException {

        if (!quizBank.containsId(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(quizBank.get(id), JSON_RESPONSE_HEADERS, HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @GetMapping(path = "/api/quizzes")
    public ResponseEntity getQuestionById() throws JsonProcessingException {

        return new ResponseEntity(quizBank.questions(), JSON_RESPONSE_HEADERS, HttpStatus.OK);
    }
}
