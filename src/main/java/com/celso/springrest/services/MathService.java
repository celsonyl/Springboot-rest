package com.celso.springrest.services;

import com.celso.springrest.exceptions.UnsupportedMathOperationException;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double soma(String n1, String n2) {
        if (!isNumeric(n1) || !isNumeric(n2)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }
        return Double.parseDouble(n1) + Double.parseDouble(n2);
    }

    public Double subtracao(String n1, String n2) {
        if (!isNumeric(n1) || !isNumeric(n2)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }
        return Double.parseDouble(n1) - Double.parseDouble(n2);
    }

    public Double multiplicacao(String n1, String n2) {
        if (!isNumeric(n1) || !isNumeric(n2)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }
        return Double.parseDouble(n1) * Double.parseDouble(n2);
    }

    public Double raizQuadrada(String n1) {
        if (!isNumeric(n1)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }
        return Math.sqrt(Double.parseDouble(n1));
    }

    public Double media(String n1, String n2) {
        if (!isNumeric(n1) || !isNumeric(n2)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }
        return (Double.parseDouble(n1) + Double.parseDouble(n2)) / 2;
    }

    public Double divisao(String n1, String n2) {
        if (!isNumeric(n1) || !isNumeric(n2)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        } else if (Double.parseDouble(n2) == 0) {
            throw new UnsupportedMathOperationException("Não é possivel dividir por 0");
        }
        return Double.parseDouble(n1) / Double.parseDouble(n2);
    }

    private boolean isNumeric(String number) {
        if (number == null) {
            return false;
        }
        String n = number.replaceAll(",", ".");
        return n.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}