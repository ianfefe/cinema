package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.SalaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.TipoSala;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.SalaService;
import ufjf.cinema.services.TipoSalaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/salas")
@RequiredArgsConstructor
@CrossOrigin
public class SalaController {

    private final SalaService service;
    private final CinemaService cinemaService;
    private final TipoSalaService tipoSalaService;

    @GetMapping
    public ResponseEntity get() {
        List<Sala> salas = service.findAll();
        return ResponseEntity.ok(salas.stream().map(SalaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.findById(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sala.map(SalaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SalaDTO dto) {
        try {
            Sala sala = converter(dto);
            sala = service.salvar(sala);
            return new ResponseEntity(sala, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SalaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sala sala = converter(dto);
            sala.setId(id);
            service.salvar(sala);
            return ResponseEntity.ok(sala);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.findById(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sala.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sala converter(SalaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sala sala = modelMapper.map(dto, Sala.class);
        if (dto.getIdCinema() != null) {
            Optional<Cinema> cinema = cinemaService.findById(dto.getIdCinema());
            sala.setCinema(cinema.orElse(null));
        }
        if (dto.getIdTipoSala() != null) {
            Optional<TipoSala> tipoSala = tipoSalaService.findById(dto.getIdTipoSala());
            sala.setTipoSala(tipoSala.orElse(null));
        }
        return sala;
    }
}
