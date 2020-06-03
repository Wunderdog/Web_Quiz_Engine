package engine;

public class QuizResult {
    boolean success;
    String feedback;

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

    String correct = "Congratulations, you're right!";
    String wrong = "Wrong answer! Please, try again.";

    QuizResult() {}

    QuizResult(boolean success, String feedback) {
        if(feedback == "correct") {
            this.success = true;
            this.feedback = correct;
        } else
        if(feedback == "wrong" ){
            this.success = false;
            this.feedback = wrong;
        }
    }

}
