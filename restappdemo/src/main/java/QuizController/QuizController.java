package QuizController;

import Entity.Quiz;
import Entity.Response;
import Repository.QuizRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
class QuizController {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;

    public QuizController() {

    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable(value = "id") Long quizId)
            throws ResourceNotFoundException {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found for this id :: " + quizId));
        return ResponseEntity.ok().body(quiz);
    }

    @PostMapping(path = "/api/quizzes", consumes = "application/json", produces = "application/json")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @PostMapping(path = "/api/quizzes/{id}/solve{answer}" , produces = "application/json")
    public Response getResponse(@PathVariable long id, @RequestBody int[] answer) throws ResourceNotFoundException {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            if (quiz.getAnswer() == null) {
                return new Response(true);
            } else {
                return new Response(Arrays.equals(answer, quiz.getAnswer()));
            }
        } else {
            throw new ResourceNotFoundException("Invalid id: " + id);
        }
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
