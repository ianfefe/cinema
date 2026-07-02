package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.GeneroDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Genero;
import ufjf.cinema.services.GeneroService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
@CrossOrigin
public class GeneroController {

    private final GeneroService service;

    @GetMapping
    public ResponseEntity get() {
        List<Genero> generos = service.findAll();
        return ResponseEntity.ok(generos.stream().map(GeneroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.findById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(genero.map(GeneroDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody GeneroDTO dto) {
        try {
            Genero genero = converter(dto);
            genero = service.salvar(genero);
            return new ResponseEntity(genero, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GeneroDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Genero genero = converter(dto);
            genero.setId(id);
            service.salvar(genero);
            return ResponseEntity.ok(genero);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Genero> genero = service.findById(id);
        if (!genero.isPresent()) {
            return new ResponseEntity("Gênero não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(genero.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Genero converter(GeneroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Genero.class);
    }
}
