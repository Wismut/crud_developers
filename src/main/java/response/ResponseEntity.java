package response;

public class ResponseEntity<T> {
    private String type;
    private String message;
    private T data;

    public ResponseEntity(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public ResponseEntity(T data) {
        this.data = data;
    }
}
