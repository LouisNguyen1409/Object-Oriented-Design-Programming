package dungeonmania.response.models;

/**
 * DO NOT CHANGE THIS FILE
 */
public final class GenericResponseWrapper<T> {
    private final T result;
    private final String errorTitle;
    private final String errorMessage;
    private final boolean isError;

    private GenericResponseWrapper(T result) {
        this.result = result;
        this.errorTitle = this.errorMessage = null;
        this.isError = false;
    }

    private GenericResponseWrapper(String title, String msg) {
        this.result = null;
        this.errorTitle = title;
        this.errorMessage = msg;
        this.isError = true;
    }

    public static <T> GenericResponseWrapper<T> ok(T result) {
        return new GenericResponseWrapper<T>(result);
    }

    public static <T> GenericResponseWrapper<T> err(Exception e) {
        return new GenericResponseWrapper<T>(e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

    public boolean isError() {
        return isError;
    }

    public T getResult() {
        return result;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
