package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.SessoesCinemaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.entity.SessoesCinema;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.SessaoService;
import ufjf.cinema.services.SessoesCinemaService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sessoes-cinema")
@RequiredArgsConstructor
@CrossOrigin
public class SessoesCinemaController {

    private final SessoesCinemaService service;
    private final CinemaService cinemaService;
    private final SessaoService sessaoService;

    @GetMapping
    public ResponseEntity get() {
        List<SessoesCinema> sessoesCinema = service.findAll();
        return ResponseEntity.ok(sessoesCinema.stream().map(SessoesCinemaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<SessoesCinema> sessoesCinema = service.findById(id);
        if (!sessoesCinema.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessoesCinema.map(SessoesCinemaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SessoesCinemaDTO dto) {
        try {
            SessoesCinema sessoesCinema = converter(dto);
            sessoesCinema = service.salvar(sessoesCinema);
            return new ResponseEntity(sessoesCinema, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessoesCinemaDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            SessoesCinema sessoesCinema = converter(dto);
            sessoesCinema.setId(id);
            service.salvar(sessoesCinema);
            return ResponseEntity.ok(sessoesCinema);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<SessoesCinema> sessoesCinema = service.findById(id);
        if (!sessoesCinema.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessoesCinema.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public SessoesCinema converter(SessoesCinemaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        SessoesCinema sessoesCinema = modelMapper.map(dto, SessoesCinema.class);
        if (dto.getCinemaId() != null) {
            Optional<Cinema> cinema = cinemaService.findById(dto.getCinemaId());
            sessoesCinema.setCinema(cinema.orElse(null));
        }
        if (dto.getSessaoId() != null) {
            Optional<Sessao> sessao = sessaoService.findById(dto.getSessaoId());
            sessoesCinema.setSessao(sessao.orElse(null));
        }
        return sessoesCinema;
    }
}
