package edu.comillas.icai.gitt.pat.spring.mvc.repositorio;

import edu.comillas.icai.gitt.pat.spring.mvc.entity.Carrito;
import org.springframework.data.repository.CrudRepository;

public interface RepoCarrito extends CrudRepository<Carrito, Long>{
    Carrito findByIdCarrito (Long IdCarrito);
}
