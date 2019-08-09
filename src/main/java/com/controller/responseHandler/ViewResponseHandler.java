package com.controller.responseHandler;

import com.controller.PalindromeController;
import com.exception.BadRequestException;
import com.exception.InternalServerError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = {PalindromeController.class})
public class ViewResponseHandler {

    @ExceptionHandler(value = {BadRequestException.class, NumberFormatException.class})
    public ModelAndView badRequestExceptionHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("badRequestException");
        modelAndView.addObject("exception", e);
        return modelAndView;
    }

    @ExceptionHandler(value = InternalServerError.class)
    public ModelAndView internalServerErrorHandler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("internalServerError");
        modelAndView.addObject("error", e);
        return modelAndView;
    }
}
