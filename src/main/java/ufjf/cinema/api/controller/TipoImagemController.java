package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.TipoImagemDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoImagem;
import ufjf.cinema.services.TipoImagemService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoimagens")
@RequiredArgsConstructor
@CrossOrigin
public class TipoImagemController {

    private final TipoImagemService service;

    @GetMapping
    public ResponseEntity get() {
        List<TipoImagem> tipos = service.findAll();
        return ResponseEntity.ok(tipos.stream().map(TipoImagemDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoImagem> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de imagem não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipo.map(TipoImagemDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoImagemDTO dto) {
        try {
            TipoImagem tipo = converter(dto);
            tipo = service.salvar(tipo);
            return new ResponseEntity(tipo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoImagemDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Tipo de imagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoImagem tipo = converter(dto);
            tipo.setId(id);
            service.salvar(tipo);
            return ResponseEntity.ok(tipo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoImagem> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de imagem não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipo.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoImagem converter(TipoImagemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoImagem.class);
    }
}
