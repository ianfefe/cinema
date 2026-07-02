package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.FuncionarioDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Endereco;
import ufjf.cinema.model.entity.Funcionario;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.EnderecoService;
import ufjf.cinema.services.FuncionarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/funcionarios")
@RequiredArgsConstructor
@CrossOrigin
public class FuncionarioController {

    private final FuncionarioService service;
    private final EnderecoService enderecoService;
    private final CinemaService cinemaService;

    @GetMapping
    public ResponseEntity get() {
        List<Funcionario> funcionarios = service.findAll();
        return ResponseEntity.ok(funcionarios.stream().map(FuncionarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.findById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funcionario.map(FuncionarioDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody FuncionarioDTO dto) {
        try {
            Funcionario funcionario = converter(dto);
            funcionario = service.salvar(funcionario);
            return new ResponseEntity(funcionario, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody FuncionarioDTO dto) {
        if (!service.findById(id).isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Funcionario funcionario = converter(dto);
            funcionario.setId(id);
            service.salvar(funcionario);
            return ResponseEntity.ok(funcionario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = service.findById(id);
        if (!funcionario.isPresent()) {
            return new ResponseEntity("Funcionário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funcionario.get(), id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funcionario converter(FuncionarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
        if (dto.getEndereco() != null) {
            Optional<Endereco> endereco = enderecoService.findById(dto.getEndereco());
            funcionario.setEndereco(endereco.orElse(null));
        }
        if (dto.getCinemaId() != null) {
            Optional<Cinema> cinema = cinemaService.findById(dto.getCinemaId());
            funcionario.setCinema(cinema.orElse(null));
        }
        return funcionario;
    }
}
