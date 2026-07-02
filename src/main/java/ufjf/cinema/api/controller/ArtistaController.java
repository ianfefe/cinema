package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.ArtistaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Artista;
import ufjf.cinema.services.ArtistaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/artistas")
@RequiredArgsConstructor
@CrossOrigin
public class ArtistaController {

    private final ArtistaService service;

    @GetMapping
    public ResponseEntity get() {
        List<Artista> artistas = service.findAll();
        return ResponseEntity.ok(artistas.stream().map(ArtistaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Artista> artista = service.findById(id);
        if (!artista.isPresent()) {
            return new ResponseEntity("Artista não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(artista.map(ArtistaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ArtistaDTO dto) {
        try {
            Artista artista = converter(dto);
            artista = service.salvar(artista);
            return new ResponseEntity(artista, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ArtistaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Artista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Artista artista = converter(dto);
            artista.setId(id);
            service.salvar(artista);
            return ResponseEntity.ok(artista);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Artista> artista = service.findById(id);
        if (!artista.isPresent()) {
            return new ResponseEntity("Artista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(artista.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Artista converter(ArtistaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Artista.class);
    }
}
