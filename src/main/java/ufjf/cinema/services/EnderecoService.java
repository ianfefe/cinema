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
        if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
            throw new RegraNegocioException("Bairro inválido");
        }
        if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
            throw new RegraNegocioException("Cidade inválida");
        }
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().isEmpty()) {
            throw new RegraNegocioException("Logradouro inválido");
        }
        if (endereco.getNumero() == null || endereco.getNumero() == 0) {
            throw new RegraNegocioException("Número inválido");
        }
    }
}
