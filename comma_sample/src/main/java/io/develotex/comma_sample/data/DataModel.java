package io.develotex.comma_sample.data;

public class DataModel<T> {

    public enum Status { LOADING, SUCCESS, EMPTY, ERROR }

    public Status status;

    public T data;

    public int errorCode;

    public String errorDescription;

    private DataModel(Status status, T data, int errorCode, String errorDescription) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public static DataModel loading() {
        return new DataModel(Status.LOADING, null, 0, null);
    }

    public static <T> DataModel success(T data) {
        return new DataModel(Status.SUCCESS, data, 0, null);
    }

    public static DataModel error(int errorCode, String errorDescription) {
        return new DataModel(Status.ERROR, null, errorCode, errorDescription);
    }

    public static DataModel empty() {
        return new DataModel(Status.EMPTY, null, 0, null);
    }

}