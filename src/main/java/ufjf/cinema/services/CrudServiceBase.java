package ufjf.cinema.services;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import ufjf.cinema.exception.RegraNegocioException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class CrudServiceBase<T, ID> {

    protected JpaRepository<T, ID> repository;

    public CrudServiceBase(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Transactional
    public T salvar(T entidade) {
        validar(entidade);
        return repository.save(entidade);
    }

    @Transactional
    public void excluir(T entidade, ID id) {
        Objects.requireNonNull(id);
        repository.delete(entidade);
    }

    public abstract void validar(T entidade);

    public void validarCampo(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RegraNegocioException(nomeCampo + " inválido");
        }
    }
}