package com.ecommerce.api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return new ResponseEntity<>("Error 404: Recurso no encontrado", HttpStatus.NOT_FOUND);
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new ResponseEntity<>("Error 500: Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    public String getErrorPath() {
        return "/error";
    }
}
