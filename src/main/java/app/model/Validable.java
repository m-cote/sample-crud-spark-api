package app.model;

public interface Validable {

    default boolean isValid(){
        return true;
    }
}
