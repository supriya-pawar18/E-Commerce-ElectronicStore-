package com.electronicstore.electronicStoreApp.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {

    private Logger logger= (Logger) LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        logger.info("Message from isValid :{}");

        if(value.isBlank()){
            return false;
        }else {
            return true;
        }
    }
}
