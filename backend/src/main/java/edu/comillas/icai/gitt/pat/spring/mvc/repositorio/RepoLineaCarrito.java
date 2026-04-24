package edu.comillas.icai.gitt.pat.spring.mvc.repositorio;

import edu.comillas.icai.gitt.pat.spring.mvc.entity.Carrito;
import edu.comillas.icai.gitt.pat.spring.mvc.entity.LineaCarrito;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoLineaCarrito extends CrudRepository<LineaCarrito, Long> {

    List<LineaCarrito> findByCarrito_IdCarrito(Long idCarrito);
}