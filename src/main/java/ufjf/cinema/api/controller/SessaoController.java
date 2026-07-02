package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.SessaoDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Filme;
import ufjf.cinema.model.entity.Sala;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.entity.TipoAudio;
import ufjf.cinema.model.entity.TipoImagem;
import ufjf.cinema.services.FilmeService;
import ufjf.cinema.services.SalaService;
import ufjf.cinema.services.SessaoService;
import ufjf.cinema.services.TipoAudioService;
import ufjf.cinema.services.TipoImagemService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sessoes")
@RequiredArgsConstructor
@CrossOrigin
public class SessaoController {

    private final SessaoService service;
    private final FilmeService filmeService;
    private final SalaService salaService;
    private final TipoImagemService tipoImagemService;
    private final TipoAudioService tipoAudioService;

    @GetMapping
    public ResponseEntity get() {
        List<Sessao> sessoes = service.findAll();
        return ResponseEntity.ok(sessoes.stream().map(SessaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.findById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sessao.map(SessaoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody SessaoDTO dto) {
        try {
            Sessao sessao = converter(dto);
            sessao = service.salvar(sessao);
            return new ResponseEntity(sessao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SessaoDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sessao sessao = converter(dto);
            sessao.setId(id);
            service.salvar(sessao);
            return ResponseEntity.ok(sessao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sessao> sessao = service.findById(id);
        if (!sessao.isPresent()) {
            return new ResponseEntity("Sessão não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sessao.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sessao converter(SessaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sessao sessao = modelMapper.map(dto, Sessao.class);
        if (dto.getIdFilme() != null) {
            Optional<Filme> filme = filmeService.findById(dto.getIdFilme());
            sessao.setFilme(filme.orElse(null));
        }
        if (dto.getIdSala() != null) {
            Optional<Sala> sala = salaService.findById(dto.getIdSala());
            sessao.setSala(sala.orElse(null));
        }
        if (dto.getIdTipoImagem() != null) {
            Optional<TipoImagem> tipoImagem = tipoImagemService.findById(dto.getIdTipoImagem());
            sessao.setTipoImagem(tipoImagem.orElse(null));
        }
        if (dto.getIdTipoAudio() != null) {
            Optional<TipoAudio> tipoAudio = tipoAudioService.findById(dto.getIdTipoAudio());
            sessao.setTipoAudio(tipoAudio.orElse(null));
        }
        return sessao;
    }
}
