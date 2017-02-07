package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	private final TipoRepository tipoRepository;

	public DemoApplication(TipoRepository tipoRepository) {
		this.tipoRepository = tipoRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Arrays.asList("juan","jhon","luis","roger","miguel","beto")
				.stream()
				.map(name->new Tipo(name))
				.forEach(tipoRepository::save);
//		tipoRepository.findAll().forEach(System.out::println);
	}
}

@RestController
class MyRestController {
	private final TipoRepository tipoRepository;

	MyRestController(TipoRepository tipoRepository) {
		this.tipoRepository = tipoRepository;
	}

	@GetMapping(value = "/data")
	public List<Tipo> getList() {
		return tipoRepository.findAll();
	}

	@GetMapping(value = "/data-server")
	public DataTablesOutput getListServer(HttpServletRequest request) {
		int draw = Integer.parseInt(request.getParameter("draw"));
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		int pageNumber = start / length;
		Pageable pageable = new QPageRequest(pageNumber, length);
		Page<Tipo> page = tipoRepository.findAll(pageable);
		return new DataTablesOutput(draw,page);
	}
}
@Controller
class MyController {
	private final TipoRepository tipoRepository;

	MyController(TipoRepository tipoRepository) {
		this.tipoRepository = tipoRepository;
	}

	@RequestMapping("/hola")
	public String home() {
		return "index";
	}
}
class DataTablesOutput<T>{
	private int draw;
	private long recordsTotal = 0L;
	private long recordsFiltered = 0L;
	private List<T> data = Collections.emptyList();
	private String error;

	public DataTablesOutput() {
	}

	public DataTablesOutput(int draw) {
		this.draw = draw;
	}

	public DataTablesOutput(int draw, Page page) {
		this.draw = draw;
		this.data = page.getContent();
		this.recordsFiltered = page.getTotalElements();
		this.recordsTotal = page.getTotalElements();
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
@Entity
class Tipo {
	@Id
	@GeneratedValue
	private long id;
	private String name;

	public Tipo() {
	}

	public Tipo(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Tipo{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}

interface TipoRepository extends PagingAndSortingRepository<Tipo, Long> {
	List<Tipo> findAll();
}