package com.example.ermolaenkoalex.nytimes.api;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class EnumValueConverter extends Converter.Factory {

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        if (type instanceof Class && ((Class<?>) type).isEnum()) {
            return (Converter<Enum, String>) value -> {
                try {
                    return value.getClass().getField(value.name()).getAnnotation(SerializedName.class).value();
                } catch (Exception exception) {
                    return value.toString();
                }
            };
        }

        return null;
    }
}
