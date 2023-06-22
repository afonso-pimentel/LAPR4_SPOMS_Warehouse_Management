package models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
/**
 * Order task assignment response that extends error response
 */
public class OrderTaskAssignmentResponse extends ErrorResponse{

    /**
     * Initializes a new instance of OrderTaskAssignment
     */
    public OrderTaskAssignmentResponse(){ //For jackson
        super(false, "");
    }

    /**
     * Initializes a new instance of OrderTaskAssignmentResponse
     * @param error Error
     * @param message Message
     */
    public OrderTaskAssignmentResponse(boolean error, String message) {
        super(error, message);
    }
}
