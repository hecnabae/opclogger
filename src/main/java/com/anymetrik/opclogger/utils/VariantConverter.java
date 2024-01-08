package com.anymetrik.opclogger.utils;

import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

public class VariantConverter {
    public static Double convertToDouble(Variant variant) {
        // Comprobar si el Variant contiene un valor que puede ser convertido a Double
        if (variant.getValue() instanceof Number) {
            return ((Number) variant.getValue()).doubleValue();
        } else {
            throw new IllegalArgumentException("El Variant no contiene un tipo de dato que pueda ser convertido a Double.");
        }
    }
}
