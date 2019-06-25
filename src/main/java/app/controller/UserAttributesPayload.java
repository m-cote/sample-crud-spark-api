package app.controller;

import app.model.Validable;

import java.util.HashMap;


public class UserAttributesPayload extends HashMap<String, String> implements Validable {
    @Override
    public boolean isValid() {
        return !isEmpty();
    }
}
