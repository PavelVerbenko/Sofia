package com.example.Sofia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

@ControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseError(DataAccessException ex, Model model) {
        log.error("Database error: {}", ex.getMessage());
        model.addAttribute("error", "Ошибка работы с базой данных");
        model.addAttribute("message", ex.getLocalizedMessage());
        return "error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, RedirectAttributes redirectAttributes) {
        log.warn("Entity not found: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", "Запрошенные данные не найдены");
        return "redirect:/posts";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointer(NullPointerException ex, Model model) {
        log.error("Null pointer: {}", ex.getMessage());
        model.addAttribute("error", "Ошибка в обработке данных");
        model.addAttribute("message", "Попытка обращения к несуществующему объекту");
        return "error";
    }
}