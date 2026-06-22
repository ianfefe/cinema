package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.FIlmesDiretorDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Diretor;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.FilmesDiretor;
import ufjf.cinema.services.DiretorService;
import ufjf.cinema.services.FilmeService;
import ufjf.cinema.services.FilmesDiretorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/filmes-diretor")
@RequiredArgsConstructor
@CrossOrigin
public class FilmesDiretorController {

    private final FilmesDiretorService service;
    private final FilmeService filmeService;
    private final DiretorService diretorService;

    @GetMapping
    public ResponseEntity get() {
        List<FilmesDiretor> filmesDiretor = service.findAll();
        return ResponseEntity.ok(filmesDiretor.stream().map(FIlmesDiretorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<FilmesDiretor> filmesDiretor = service.findById(id);
        if (!filmesDiretor.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filmesDiretor.map(FIlmesDiretorDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody FIlmesDiretorDTO dto) {
        try {
            FilmesDiretor filmesDiretor = converter(dto);
            filmesDiretor = service.salvar(filmesDiretor);
            return new ResponseEntity(filmesDiretor, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FIlmesDiretorDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            FilmesDiretor filmesDiretor = converter(dto);
            filmesDiretor.setId(id);
            service.salvar(filmesDiretor);
            return ResponseEntity.ok(filmesDiretor);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<FilmesDiretor> filmesDiretor = service.findById(id);
        if (!filmesDiretor.isPresent()) {
            return new ResponseEntity("Registro não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(filmesDiretor.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public FilmesDiretor converter(FIlmesDiretorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        FilmesDiretor filmesDiretor = modelMapper.map(dto, FilmesDiretor.class);
        if (dto.getFilmeId() != null) {
            Optional<Filme> filme = filmeService.findById(dto.getFilmeId());
            filmesDiretor.setFilme(filme.orElse(null));
        }
        if (dto.getDiretorId() != null) {
            Optional<Diretor> diretor = diretorService.findById(dto.getDiretorId());
            filmesDiretor.setDiretor(diretor.orElse(null));
        }
        return filmesDiretor;
    }
}
