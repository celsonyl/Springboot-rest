package com.celso.springrest;

import com.celso.springrest.exceptions.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MathController {

    @RequestMapping(method = RequestMethod.GET, value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subTracao/{numberOne}/{numberTwo}")
    public Double subTracao(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }

        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/multiplicacao/{numberOne}/{numberTwo}")
    public Double multiplicacao(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        }

        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/divisao/{numberOne}/{numberTwo}")
    public Double divisao(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new UnsupportedMathOperationException("Defina um valor numerico!");
        } else if (convertToDouble(numberTwo) == 0) {
            throw new UnsupportedMathOperationException("Não é possivel dividir por 0");
        }

        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    private Double convertToDouble(String number) {
        return Double.parseDouble(number);
    }

    private boolean isNumeric(String number) {
        if (number == null) {
            return false;
        }
        String n = number.replaceAll(",", ".");
        return n.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
