package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.FilmeArtistaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Artista;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesArtista;
import ufjf.cinema.services.ArtistaService;
import ufjf.cinema.services.FilmeService;
import ufjf.cinema.services.FilmesArtistaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes-artista")
@RequiredArgsConstructor
@CrossOrigin
public class FilmesArtistaController {

    private final FilmesArtistaService service;
    private final FilmeService filmeService;
    private final ArtistaService artistaService;

    @GetMapping
    public ResponseEntity get() {
        List<FilmesArtista> filmesArtista = service.findAll();
        return ResponseEntity.ok(filmesArtista.stream().map(FilmeArtistaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmesArtista> filmesArtista = service.findById(id);
        if (!filmesArtista.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmesArtista.map(FilmeArtistaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody FilmeArtistaDTO dto) {
        try {
            FilmesArtista filmesArtista = converter(dto);
            filmesArtista = service.salvar(filmesArtista);
            return new ResponseEntity(filmesArtista, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmeArtistaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FilmesArtista filmesArtista = converter(dto);
            filmesArtista.setId(id);
            service.salvar(filmesArtista);
            return ResponseEntity.ok(filmesArtista);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FilmesArtista> filmesArtista = service.findById(id);
        if (!filmesArtista.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filmesArtista.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FilmesArtista converter(FilmeArtistaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmesArtista filmesArtista = modelMapper.map(dto, FilmesArtista.class);
        if (dto.getFilmeId() != null) {
            Optional<Filme> filme = filmeService.findById(dto.getFilmeId());
            filmesArtista.setFilme(filme.orElse(null));
        }
        if (dto.getArtistaId() != null) {
            Optional<Artista> artista = artistaService.findById(dto.getArtistaId());
            filmesArtista.setArtista(artista.orElse(null));
        }
        return filmesArtista;
    }
}
