package response;

public class ResponseEntity<T> {
    private String statusMessage;
    private Integer statusCode;
    private T data;

    public ResponseEntity(String statusMessage, Integer statusCode, T data) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
