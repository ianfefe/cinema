package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.FilmeDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.services.FilmeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
@CrossOrigin
public class FilmeController {

    private final FilmeService service;

    @GetMapping
    public ResponseEntity get() {
        List<Filme> filmes = service.findAll();
        return ResponseEntity.ok(filmes.stream().map(FilmeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.findById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filme.map(FilmeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody FilmeDTO dto) {
        try {
            Filme filme = converter(dto);
            filme = service.salvar(filme);
            return new ResponseEntity(filme, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Filme filme = converter(dto);
            filme.setId(id);
            service.salvar(filme);
            return ResponseEntity.ok(filme);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Filme> filme = service.findById(id);
        if (!filme.isPresent()) {
            return new ResponseEntity("Filme não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filme.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Filme converter(FilmeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Filme.class);
    }
}
