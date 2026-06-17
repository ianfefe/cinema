package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.AssentoDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.TipoAssento;
import ufjf.cinema.services.AssentoService;
import ufjf.cinema.services.SalaService;
import ufjf.cinema.services.TipoAssentoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assentos")
@RequiredArgsConstructor
@CrossOrigin
public class AssentoController {

    private final AssentoService service;
    private final TipoAssentoService tipoAssentoService;
    private final SalaService salaService;

    @GetMapping
    public ResponseEntity get() {
        List<Assento> assentos = service.findAll();
        return ResponseEntity.ok(assentos.stream().map(AssentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.findById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assento.map(AssentoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody AssentoDTO dto) {
        try {
            Assento assento = converter(dto);
            assento = service.salvar(assento);
            return new ResponseEntity(assento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AssentoDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Assento assento = converter(dto);
            assento.setId(id);
            service.salvar(assento);
            return ResponseEntity.ok(assento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Assento> assento = service.findById(id);
        if (!assento.isPresent()) {
            return new ResponseEntity("Assento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(assento.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Assento converter(AssentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Assento assento = modelMapper.map(dto, Assento.class);
        if (dto.getTipoAssentoId() != null) {
            Optional<TipoAssento> tipoAssento = tipoAssentoService.findById(dto.getTipoAssentoId());
            assento.setTipoAssento(tipoAssento.orElse(null));
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.findById(dto.getIdSala());
            assento.setSala(sala.orElse(null));
        }
        return assento;
    }
}
