package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.CompraDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Cliente;
import ufjf.cinema.model.entity.Compra;
import ufjf.cinema.model.entity.Funcionario;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.ClienteService;
import ufjf.cinema.services.CompraService;
import ufjf.cinema.services.FuncionarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compras")
@RequiredArgsConstructor
@CrossOrigin
public class CompraController {

    private final CompraService service;
    private final CinemaService cinemaService;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity get() {
        List<Compra> compras = service.findAll();
        return ResponseEntity.ok(compras.stream().map(CompraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.findById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(compra.map(CompraDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody CompraDTO dto) {
        try {
            Compra compra = converter(dto);
            compra = service.salvar(compra);
            return new ResponseEntity(compra, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CompraDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Compra compra = converter(dto);
            compra.setId(id);
            service.salvar(compra);
            return ResponseEntity.ok(compra);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Compra> compra = service.findById(id);
        if (!compra.isPresent()) {
            return new ResponseEntity("Compra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(compra.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Compra converter(CompraDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Compra compra = modelMapper.map(dto, Compra.class);
        if (dto.getCinemaId() != null) {
            Optional<Cinema> cinema = cinemaService.findById(dto.getCinemaId());
            compra.setCinema(cinema.orElse(null));
        }
        if (dto.getClienteId() != null) {
            Optional<Cliente> cliente = clienteService.findById(dto.getClienteId());
            compra.setCliente(cliente.orElse(null));
        }
        if (dto.getUsuarioId() != null) {
            Optional<Funcionario> funcionario = funcionarioService.findById(dto.getUsuarioId());
            compra.setFuncionario(funcionario.orElse(null));
        }
        return compra;
    }
}
