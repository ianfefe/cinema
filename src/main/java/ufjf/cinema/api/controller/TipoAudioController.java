package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.TipoAudioDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.TipoAudio;
import ufjf.cinema.services.TipoAudioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tipoaudios")
@RequiredArgsConstructor
@CrossOrigin
public class TipoAudioController {

    private final TipoAudioService service;

    @GetMapping
    public ResponseEntity get() {
        List<TipoAudio> tipos = service.findAll();
        return ResponseEntity.ok(tipos.stream().map(TipoAudioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<TipoAudio> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de áudio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tipo.map(TipoAudioDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody TipoAudioDTO dto) {
        try {
            TipoAudio tipo = converter(dto);
            tipo = service.salvar(tipo);
            return new ResponseEntity(tipo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TipoAudioDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Tipo de áudio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            TipoAudio tipo = converter(dto);
            tipo.setId(id);
            service.salvar(tipo);
            return ResponseEntity.ok(tipo);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<TipoAudio> tipo = service.findById(id);
        if (!tipo.isPresent()) {
            return new ResponseEntity("Tipo de áudio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tipo.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public TipoAudio converter(TipoAudioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, TipoAudio.class);
    }
}
