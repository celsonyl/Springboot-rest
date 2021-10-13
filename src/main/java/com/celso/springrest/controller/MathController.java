package com.celso.springrest.controller;

import com.celso.springrest.services.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/math")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping(value = "/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) {
        return mathService.soma(numberOne, numberTwo);
    }

    @GetMapping(value = "/subTracao/{numberOne}/{numberTwo}")
    public Double subTracao(@PathVariable String numberOne, @PathVariable String numberTwo) {
        return mathService.subtracao(numberOne, numberTwo);
    }

    @GetMapping(value = "/multiplicacao/{numberOne}/{numberTwo}")
    public Double multiplicacao(@PathVariable String numberOne, @PathVariable String numberTwo) {
        return mathService.multiplicacao(numberOne, numberTwo);
    }

    @GetMapping(value = "/raizQuadrada/{numberOne}")
    public Double raizQuadrada(@PathVariable String numberOne) {
        return mathService.raizQuadrada(numberOne);
    }

    @GetMapping(value = "/media/{numberOne}/{numberTwo}")
    public Double media(@PathVariable String numberOne, @PathVariable String numberTwo) {
        return mathService.media(numberOne, numberTwo);
    }

    @GetMapping(value = "/divisao/{numberOne}/{numberTwo}")
    public Double divisao(@PathVariable String numberOne, @PathVariable String numberTwo) {
        return mathService.divisao(numberOne, numberTwo);
    }
}