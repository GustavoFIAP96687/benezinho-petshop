package br.com.fiap.petshop.domain.repository;

import br.com.fiap.petshop.domain.entity.Telefone;
import br.com.fiap.petshop.domain.entity.animal.Animal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class TelefoneRepository implements Repository<Telefone, Long> {

    private static final AtomicReference<TelefoneRepository> instance = new AtomicReference<>();
    private EntityManager manager;

    private TelefoneRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static TelefoneRepository build(EntityManager manager) {
        TelefoneRepository result = instance.get();
        if (Objects.isNull( result )) {
            TelefoneRepository repo = new TelefoneRepository( manager );
            if (instance.compareAndSet( null, repo )) {
                result = repo;
            } else {
                result = instance.get();
            }
        }
        return result;
    }


    @Override
    public List<Telefone> findAll() {
        List<Telefone> list = manager.createQuery( "FROM Telefone" ).getResultList();
        return list;
    }

    @Override
    public Telefone findById(Long id) {
        Telefone telefone = manager.find(Telefone.class, id );
        return telefone;
    }

    @Override
    public List<Telefone> findByTexto(String texto) {
        String jpql = "SELECT a FROM Telefone a  where lower(a.name) LIKE CONCAT('%',lower(:name),'%')";
        Query query = manager.createQuery( jpql );
        query.setParameter( "name", texto );
        List<Telefone> list = query.getResultList();
        return list;
    }

    @Override
    public Telefone persist(Telefone telefone) {
        manager.getTransaction().begin();
        manager.persist( telefone );
        manager.getTransaction().commit();
        return telefone;
    }

    @Override
    public Telefone update(Telefone telefone) {
        return null;
    }

    @Override
    public boolean delete(Telefone telefone) {
        return false;
    }
}
