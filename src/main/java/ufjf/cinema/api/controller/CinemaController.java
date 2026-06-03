package ufjf.cinema.api.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufjf.cinema.api.dto.CinemaDTO;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Cinema;
import ufjf.cinema.model.entity.Endereco;
import ufjf.cinema.services.CinemaService;
import ufjf.cinema.services.EnderecoService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
@CrossOrigin
public class CinemaController {

    private final EnderecoService enderecoService;
    private final CinemaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Cinema> cinemas = service.findAll();
        return ResponseEntity.ok(cinemas.stream().map(CinemaDTO::create).collect(Collectors.toList()));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody CinemaDTO dto) {
        try {
            Cinema cinema = converter(dto);
            cinema = service.salvar(cinema);
            return new ResponseEntity(cinema, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Cinema converter(CinemaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cinema cinema = modelMapper.map(dto, Cinema.class);
        if (dto.getEnderecoId() != 0) {
            Optional<Endereco> enderecoOptional = enderecoService.findById(dto.getEnderecoId());
            if (!enderecoOptional.isPresent()) {
                cinema.setEndereco(null);
            } else {
                cinema.setEndereco(enderecoOptional.get());
            }
        } else {
            cinema.setEndereco(null);
        }
        return cinema;
    }
}
