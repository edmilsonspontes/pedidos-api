package br.com.elektron.pedido.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import br.com.elektron.pedido.model.Status;

@Repository
public interface StatusRepository extends PagingAndSortingRepository<Status, Long>, QueryByExampleExecutor<Status> {

}
