package br.com.elektron.pedido;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.elektron.pedido.model.PedidoVO;
import br.com.elektron.pedido.model.Status;
import br.com.elektron.pedido.repository.PedidoRepository;
import br.com.elektron.pedido.repository.StatusRepository;
import br.com.elektron.pedido.util.Util;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class PedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
		
		System.out.println("*************************************************************************");
		System.out.println("                      DESAFIO CADMUS/VR");
		System.out.println("*************************************************************************");
		
		System.out.println(Util.getDate(new Date()));
		System.out.println(Util.getHour(new Date()));
		System.out.println(Util.getDateHour(new Date()));
	}
	
    @Bean
    CommandLineRunner initialize(PedidoRepository pedidoRepository, StatusRepository statusRepository) {
        return args -> {
            Stream.of("AB123", "XY987", "CD456").forEach(numero -> {
                PedidoVO pedido = new PedidoVO(numero, new Date());
                pedidoRepository.save(pedido);
                statusRepository.save(new Status("NOVO", new Date(), pedido));
            });
            pedidoRepository.findAll().forEach(System.out::println);
        };
    }

}
