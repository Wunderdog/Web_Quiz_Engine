package engine;

public class QuizResult {
    boolean success;
    String feedback;

    final String CORRECT_MESSAGE = "Congratulations, you're right!";
    final String WRONG_MESSAGE = "Wrong answer! Please, try again.";

    QuizResult() {}

    QuizResult(boolean success, String feedback) {
        if(feedback.equals("correct")) {
            this.success = true;
            this.feedback = CORRECT_MESSAGE;
        } else
        if(feedback.equals("wrong")){
            this.success = false;
            this.feedback = WRONG_MESSAGE;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
