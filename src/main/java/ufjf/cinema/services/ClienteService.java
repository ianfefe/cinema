package ufjf.cinema.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.model.entity.Cliente;
import ufjf.cinema.model.repository.ClienteRepository;

@Service
public class ClienteService extends CrudServiceBase<Cliente, Long>{
    private ClienteRepository clienteRepository;

    public ClienteService(JpaRepository<Cliente, Long> repository) {
        super(repository);
        this.clienteRepository = (ClienteRepository) repository;
    }

    @Override
    public void validar(Cliente cliente){
        validarCampo(cliente.getEmail(),  "email");
        validarCampo(cliente.getNome(), "nome");
        validarCampo(cliente.getSenha(),  "senha");
        validarCampo(cliente.getTelefone(),  "telefone");
        validarEntidade(cliente.getEndereco(), "endereco");
    }
}
