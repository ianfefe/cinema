package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.DiretorDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Diretor;
import ufjf.cinema.services.DiretorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/diretores")
@RequiredArgsConstructor
@CrossOrigin
public class DiretorController {

    private final DiretorService service;

    @GetMapping
    public ResponseEntity get() {
        List<Diretor> diretores = service.findAll();
        return ResponseEntity.ok(diretores.stream().map(DiretorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Diretor> diretor = service.findById(id);
        if (!diretor.isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(diretor.map(DiretorDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody DiretorDTO dto) {
        try {
            Diretor diretor = converter(dto);
            diretor = service.salvar(diretor);
            return new ResponseEntity(diretor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DiretorDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Diretor diretor = converter(dto);
            diretor.setId(id);
            service.salvar(diretor);
            return ResponseEntity.ok(diretor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Diretor> diretor = service.findById(id);
        if (!diretor.isPresent()) {
            return new ResponseEntity("Diretor não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(diretor.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Diretor converter(DiretorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Diretor.class);
    }
}
