/*
 * Copyright (c) 2025 Lahiru Kasun
 * Licensed under the MIT License – see the LICENSE file for details.
 */

package validations;

public class CustomValidationException extends RuntimeException{
    public CustomValidationException(String message) {
        super(message);
    }
}

