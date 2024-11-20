package com.udyata.linentrack.linentrack.utils;
import com.udyata.linentrack.linentrack.exception.LinenTrackApiException;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;

public class NullAwareBeanUtils {

    public static <T> void copyNonNullProperties(T source, T target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                throw new LinenTrackApiException(HttpStatus.CONFLICT, e.getLocalizedMessage());
            }
        }
    }
}
