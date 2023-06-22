package models;

public abstract class ErrorResponse {
    boolean error;
    String message;

    protected ErrorResponse(boolean error, String message){
        this.error = error;
        this.message = message;
    }

    public String ErrorMessage() {
        return message;
    }

    public boolean HasErrors(){
        return error;
    }
}
