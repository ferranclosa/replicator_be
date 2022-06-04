package com.closa.replicator.functions.validators;

import com.closa.replicator.throwables.exceptions.ValidationJsonException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Validators {
    private static Validator validator;

    public Validators() {
    }

    public static Validator getValidator(){
        if(validator == null ){
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
        return validator;
    }
    public static <I> void validate(I pInput) throws ValidationJsonException {
        List<String> listOfExceptions = new ArrayList<>();
        Set<ConstraintViolation<I>> constraintViolations = Validators.getValidator().validate(pInput, new Class[0]);
        if (!constraintViolations.isEmpty()) {

            for (ConstraintViolation one : constraintViolations) {
                listOfExceptions.add("[ " + one.getPropertyPath().toString() + " ] : " + one.getMessage());
            }
            throw new ValidationJsonException(listOfExceptions);
        }
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
