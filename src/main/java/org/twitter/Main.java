package org.twitter;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.twitter.entity.Account;
import org.twitter.util.ApplicationContext;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        //ApplicationContext.getAccountService().createAccount("ssssss","1234","12344321");
        Account account = new Account("sss","1234","ssssw");
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
        ApplicationContext.getAccountService().save(account);
        System.out.println(constraintViolations);
    }
}