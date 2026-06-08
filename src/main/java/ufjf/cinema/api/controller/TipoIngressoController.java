package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.TipoIngressoDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoIngresso;
import ufjf.cinema.services.TipoIngressoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoingressos")
@RequiredArgsConstructor
@CrossOrigin
public class TipoIngressoController {

    private final TipoIngressoService service;

    @GetMapping
    public ResponseEntity get() {
        List<TipoIngresso> tipos = service.findAll();
        return ResponseEntity.ok(tipos.stream().map(TipoIngressoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoIngresso> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipo.map(TipoIngressoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoIngressoDTO dto) {
        try {
            TipoIngresso tipo = converter(dto);
            tipo = service.salvar(tipo);
            return new ResponseEntity(tipo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoIngressoDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Tipo de ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoIngresso tipo = converter(dto);
            tipo.setId(id);
            service.salvar(tipo);
            return ResponseEntity.ok(tipo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoIngresso> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipo.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoIngresso converter(TipoIngressoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoIngresso.class);
    }
}
