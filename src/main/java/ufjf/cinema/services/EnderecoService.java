package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;
import ufjf.cinema.model.entity.Endereco;
import ufjf.cinema.model.repository.EnderecoRepository;

@Service
public class EnderecoService extends CrudServiceBase<Endereco, Long>{
    private EnderecoRepository enderecoRepository;

    public EnderecoService(JpaRepository<Endereco, Long> repository) {
        super(repository);
        this.enderecoRepository = (EnderecoRepository) repository;
    }

    public void validar(Endereco endereco) {
        validarCampo(endereco.getBairro(), "bairro");
        validarCampo(endereco.getCidade(), "cidade");
        validarCampo(endereco.getLogradouro(), "logradouro");
        if (endereco.getNumero() == null || endereco.getNumero() == 0) {
            throw new RegraNegocioException("Número inválido");
        }
    }
}
