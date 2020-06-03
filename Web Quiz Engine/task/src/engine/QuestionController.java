package engine;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
//import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jfilter.EnableJsonFilter;
import com.jfilter.filter.FieldFilterSetting;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

//    public String filterOutAnswer(Question question) throws JsonProcessingException {
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("questionFilter",
//                SimpleBeanPropertyFilter.serializeAllExcept("answer"));
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setFilterProvider(filterProvider);
//
//        String jsonOutput = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(question);
//
//        return jsonOutput;
//    }
//
//    public String filterOutAnswer(Collection<Question> questionList) throws JsonProcessingException {
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("questionFilter",
//                SimpleBeanPropertyFilter.serializeAllExcept("answer"));
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setFilterProvider(filterProvider);
//
//        String jsonOutput = mapper.writerWithDefaultPrettyPrinter()
//                .writeValueAsString(questionList);
//
//        return jsonOutput;
//    }



    @PostMapping(path = "/api/quiz")
    public ResponseEntity<QuizResult> submitAnswer(@RequestParam(value = "answer") int answer) {
        if (answer == 2) {
            return new ResponseEntity<QuizResult>(new QuizResult(true, "correct"), HttpStatus.OK);
        }
        return new ResponseEntity<QuizResult>( new QuizResult(false, "wrong"), HttpStatus.OK);
    }

    @FieldFilterSetting(className = Question.class, fields = {"answer"})
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public ResponseEntity addQuestion(@RequestBody Question newQuestion) throws JsonProcessingException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
        if (newQuestion.getId() == 0) {
            newQuestion.setId(currentId);
            currentId += 1;
        }
        questions.put(newQuestion.getId(), newQuestion);
        return new ResponseEntity(questions.get(newQuestion.getId()), responseHeaders, HttpStatus.OK);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve")
    public ResponseEntity submitAnswerById(@PathVariable int id,
                                           @RequestParam int answer) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");

        if (!questions.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (answer == questions.get(id).getAnswer()) {
            return new ResponseEntity<QuizResult>(new QuizResult(true, "correct"), HttpStatus.OK);
        }
        System.out.println(String.format("%s = %s", answer, questions.get(id).getAnswer()));

        return new ResponseEntity<QuizResult>( new QuizResult(false, "wrong"), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quiz")
    public ResponseEntity<Question> getQuestion() {
        Question question = testQuestion;
        return new ResponseEntity<Question>(question, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public ResponseEntity getQuestionById(@PathVariable int id) throws JsonProcessingException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
        if (!questions.containsKey(id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(questions.get(id), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(path = "/api/quizzes")
    public ResponseEntity getQuestionById() throws JsonProcessingException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json");
//        String[] output = questions.values().stream().map(q -> {
//            try {
//                return filterOutAnswer(q);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return "";
//        }).toArray(String[]::new);

        return new ResponseEntity(questions.values(), responseHeaders, HttpStatus.OK);
    }

//    private String filterOutAnswer(Map<Integer, Question> q) {
//        return filterOutAnswer((Question) q);
//    }
}
