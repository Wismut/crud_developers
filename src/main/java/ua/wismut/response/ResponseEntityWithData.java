package ua.wismut.response;

public class ResponseEntityWithData<T> {
    private T data;

    public ResponseEntityWithData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}