package br.com.elektron.pedido.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import br.com.elektron.pedido.model.PedidoVO;

@Repository
public interface PedidoRepository extends PagingAndSortingRepository<PedidoVO, Long>, QueryByExampleExecutor<PedidoVO> {

}
