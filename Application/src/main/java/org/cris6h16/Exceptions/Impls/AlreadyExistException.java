package org.cris6h16.Exceptions.Impls;

import org.cris6h16.Exceptions.AbstractApplicationException;

public class AlreadyExistException extends AbstractApplicationException {
    public AlreadyExistException(String message) {
        super(message);
    }
}
