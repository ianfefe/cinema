package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.FilmesCinemaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesCinema;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.FilmeService;
import ufjf.cinema.services.FilmesCinemaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes-cinema")
@RequiredArgsConstructor
@CrossOrigin
public class FilmesCinemaController {

    private final FilmesCinemaService service;
    private final FilmeService filmeService;
    private final CinemaService cinemaService;

    @GetMapping
    public ResponseEntity get() {
        List<FilmesCinema> filmesCinema = service.findAll();
        return ResponseEntity.ok(filmesCinema.stream().map(FilmesCinemaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmesCinema> filmesCinema = service.findById(id);
        if (!filmesCinema.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmesCinema.map(FilmesCinemaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody FilmesCinemaDTO dto) {
        try {
            FilmesCinema filmesCinema = converter(dto);
            filmesCinema = service.salvar(filmesCinema);
            return new ResponseEntity(filmesCinema, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FilmesCinemaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FilmesCinema filmesCinema = converter(dto);
            filmesCinema.setId(id);
            service.salvar(filmesCinema);
            return ResponseEntity.ok(filmesCinema);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FilmesCinema> filmesCinema = service.findById(id);
        if (!filmesCinema.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filmesCinema.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FilmesCinema converter(FilmesCinemaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmesCinema filmesCinema = modelMapper.map(dto, FilmesCinema.class);
        if (dto.getFilmeId() != null) {
            Optional<Filme> filme = filmeService.findById(dto.getFilmeId());
            filmesCinema.setFilme(filme.orElse(null));
        }
        if (dto.getCinemaId() != null) {
            Optional<Cinema> cinema = cinemaService.findById(dto.getCinemaId());
            filmesCinema.setCinema(cinema.orElse(null));
        }
        return filmesCinema;
    }
}
