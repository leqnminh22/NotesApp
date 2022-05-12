package com.mle.notesapp.domain;

public interface Callback<T> {

    void onSuccess(T data);

    void onError(Throwable exception);
}
