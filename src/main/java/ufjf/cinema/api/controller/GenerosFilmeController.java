package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.GenerosFilmeDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Genero;
import ufjf.cinema.model.entity.GenerosFilme;
import ufjf.cinema.services.FilmeService;
import ufjf.cinema.services.GeneroService;
import ufjf.cinema.services.GenerosFilmeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/generos-filme")
@RequiredArgsConstructor
@CrossOrigin
public class GenerosFilmeController {

    private final GenerosFilmeService service;
    private final FilmeService filmeService;
    private final GeneroService generoService;

    @GetMapping
    public ResponseEntity get() {
        List<GenerosFilme> generosFilme = service.findAll();
        return ResponseEntity.ok(generosFilme.stream().map(GenerosFilmeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<GenerosFilme> generosFilme = service.findById(id);
        if (!generosFilme.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(generosFilme.map(GenerosFilmeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody GenerosFilmeDTO dto) {
        try {
            GenerosFilme generosFilme = converter(dto);
            generosFilme = service.salvar(generosFilme);
            return new ResponseEntity(generosFilme, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GenerosFilmeDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            GenerosFilme generosFilme = converter(dto);
            generosFilme.setId(id);
            service.salvar(generosFilme);
            return ResponseEntity.ok(generosFilme);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<GenerosFilme> generosFilme = service.findById(id);
        if (!generosFilme.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(generosFilme.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public GenerosFilme converter(GenerosFilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        GenerosFilme generosFilme = modelMapper.map(dto, GenerosFilme.class);
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.findById(dto.getIdFilme());
            generosFilme.setFilme(filme.orElse(null));
        }
        if (dto.getIdGenero() != null) {
            Optional<Genero> genero = generoService.findById(dto.getIdGenero());
            generosFilme.setGenero(genero.orElse(null));
        }
        return generosFilme;
    }
}
