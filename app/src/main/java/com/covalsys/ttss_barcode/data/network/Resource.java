package com.covalsys.ttss_barcode.data.network;

import static com.covalsys.ttss_barcode.data.network.Status.ERROR;
import static com.covalsys.ttss_barcode.data.network.Status.LOADING;
import static com.covalsys.ttss_barcode.data.network.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Resource<T> {
    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable private final String message;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Nullable
    public String getMessage() {
        return message;
    }
}