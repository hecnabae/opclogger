package com.anymetrik.opclogger.utils;

import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;

import java.time.Instant;

public class DateTimeConverter {
    public static Instant convertToInstant(DateTime dateTime) {
        // Obtener el tiempo en milisegundos desde la época UNIX
        long timeInMillis = dateTime.getJavaTime();

        // Crear y retornar un objeto Instant usando el tiempo en milisegundos
        return Instant.ofEpochMilli(timeInMillis);
    }
}
