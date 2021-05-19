package Entity;

import lombok.Data;

@Data
public class Response {

    private boolean success;
    private String feedback;

    public Response(boolean answer) {
        this.success = answer;
        this.feedback = answer ? "Congratulations, you're right!"
                : "Wrong answer! Please, try again";
    }
}
