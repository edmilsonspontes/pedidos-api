package br.com.elektron.pedido.model;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.elektron.pedido.util.Util;

@Entity
public class Status {
	
	@Id 
	@GeneratedValue 
	private 
	Long id;
	
	@Transient
	private Long idPedido;

	@NonNull
	private String nome;

	@NonNull
	private Date data;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false)
	private PedidoVO pedido;
	
	public Status() {
		super();
	}

	public Status(String nome, Date data, PedidoVO pedido) {
		this.nome = nome;
		this.data = data;
		this.pedido = pedido;
		this.idPedido = pedido != null ? pedido.getId() : null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public PedidoVO getPedido() {
		return pedido;
	}

	public void setPedido(PedidoVO pedido) {
		this.pedido = pedido;
	}
	
	public Long getIdPedido() {
		return this.idPedido = pedido != null ? pedido.getId() : null;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	@Override
	public String toString() {
		return this.nome.concat(" - ")
				.concat(Util.getDateHour(data));
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(((Status)obj).getId().equals(this.id));
	}

}
