package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.IngressoDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Assento;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Ingresso;
import ufjf.cinema.model.entity.Sessao;
import ufjf.cinema.model.entity.TipoIngresso;
import ufjf.cinema.services.AssentoService;
import ufjf.cinema.services.CompraService;
import ufjf.cinema.services.IngressoService;
import ufjf.cinema.services.SessaoService;
import ufjf.cinema.services.TipoIngressoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ingressos")
@RequiredArgsConstructor
@CrossOrigin
public class IngressoController {

    private final IngressoService service;
    private final TipoIngressoService tipoIngressoService;
    private final CompraService compraService;
    private final AssentoService assentoService;
    private final SessaoService sessaoService;

    @GetMapping
    public ResponseEntity get() {
        List<Ingresso> ingressos = service.findAll();
        return ResponseEntity.ok(ingressos.stream().map(IngressoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Ingresso> ingresso = service.findById(id);
        if (!ingresso.isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ingresso.map(IngressoDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody IngressoDTO dto) {
        try {
            Ingresso ingresso = converter(dto);
            ingresso = service.salvar(ingresso);
            return new ResponseEntity(ingresso, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody IngressoDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Ingresso ingresso = converter(dto);
            ingresso.setId(id);
            service.salvar(ingresso);
            return ResponseEntity.ok(ingresso);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Ingresso> ingresso = service.findById(id);
        if (!ingresso.isPresent()) {
            return new ResponseEntity("Ingresso não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ingresso.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Ingresso converter(IngressoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Ingresso ingresso = modelMapper.map(dto, Ingresso.class);
        if (dto.getIdTipoIngresso() != null) {
            Optional<TipoIngresso> tipoIngresso = tipoIngressoService.findById(dto.getIdTipoIngresso());
            ingresso.setTipoIngresso(tipoIngresso.orElse(null));
        }
        if (dto.getIdCompra() != null) {
            Optional<Compra> compra = compraService.findById(dto.getIdCompra());
            ingresso.setCompra(compra.orElse(null));
        }
        if (dto.getIdAssento() != null) {
            Optional<Assento> assento = assentoService.findById(dto.getIdAssento());
            ingresso.setAssento(assento.orElse(null));
        }
        if (dto.getIdSessao() != null) {
            Optional<Sessao> sessao = sessaoService.findById(dto.getIdSessao());
            ingresso.setSessao(sessao.orElse(null));
        }
        return ingresso;
    }
}
