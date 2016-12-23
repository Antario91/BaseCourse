package domain;

/**
 * Created by olgo on 23-Dec-16.
 */
public class ParamIsNullException extends Exception {
    private String paramName;

    public ParamIsNullException(String paramName) {
        super("Parameter \"" + paramName + "\" is NULL");
    }

    public String getParamName() {
        return paramName;
    }
}
