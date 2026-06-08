package ufjf.cinema.services;

import jakarta.persistence.MappedSuperclass;
import jakarta.transaction.Transactional;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.SuperCall;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ufjf.cinema.exception.RegraNegocioException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    public void validarEntidade(Object entidade, String nomeCampo) {
        if (entidade == null)
            throw new RegraNegocioException(nomeCampo + " inválido");

        try {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(entidade);
            Object idValue = wrapper.getPropertyValue("id");

            if (idValue == null || (Long) idValue == 0) {
                throw new RegraNegocioException(nomeCampo + " inválido");
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("O objeto passado não possui uma propriedade 'id' válida.", e);
        }
    }
}