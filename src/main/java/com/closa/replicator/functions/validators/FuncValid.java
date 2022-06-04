package com.closa.replicator.functions.validators;


import com.closa.replicator.throwables.exceptions.ValidationJsonException;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FuncValid {
    private FuncValid() {

    }

    public static <I> void validate(I pInput) throws ValidationJsonException {
        List<String> listOfExceptions = new ArrayList<>();
        Set<ConstraintViolation<I>> constraintViolations = Validators.getValidator().validate(pInput, new Class[0]);
        /*if (!constraintViolations.isEmpty()) {
            ConstraintViolation<I> constraint = (ConstraintViolation) constraintViolations.iterator().next();
            throw new
                    ValidationJsonException(constraint.getPropertyPath().toString(), "", constraint.getMessage(), Severity.ERROR);
        }
        */
        for (ConstraintViolation one : constraintViolations) {
            listOfExceptions.add("[ " + one.getPropertyPath().toString() + " ] : " + one.getMessage());
        }
        throw new ValidationJsonException(listOfExceptions);
    }

    public static void validatateInputLists(List<?>... lists) throws ValidationJsonException {
        int emptyCount = 0;
        List[] var2 = lists;
        int var3 = lists.length;
        for (int var4 = 0; var4 < var3; ++var4) {
            List<?> list = var2[var4];
            if (list == null || list.isEmpty()) {
                ++emptyCount;
            }
        }
    }

}
