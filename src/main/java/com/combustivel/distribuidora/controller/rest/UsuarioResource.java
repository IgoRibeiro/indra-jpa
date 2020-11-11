package com.combustivel.distribuidora.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.combustivel.distribuidora.entity.Usuario;
import com.combustivel.distribuidora.service.UsuarioService;
import com.combustivel.distribuidora.util.Paths;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@ResponseBody
@AllArgsConstructor
@RequestMapping(value = Paths.USUARIO, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna a lista de Usuarios Paginada e/ou por sigla da Regiao"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping
	private Page<Usuario> listarPaginado(
				@RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size
			){
		
		Page<Usuario> result = usuarioService.listarPaginado(new Usuario(), page, size);
		
		return new PageImpl<>(result.getContent(), PageRequest.of(page, size), result.getTotalElements());
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna Usuario por ID"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@GetMapping("/{id}")
	private ResponseEntity<Usuario> procurarPorId(@PathVariable long id) {
		return usuarioService.procurarPorId(id).map(h-> ResponseEntity.ok().body(h)).orElse(ResponseEntity.notFound().build());
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Cadastra um novo Usuario"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@PostMapping
	private ResponseEntity<Usuario> novo(@Validated @RequestBody Usuario usuario) {
		return ResponseEntity.of(Optional.of(usuarioService.salvar(usuario)));
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Altera o Usuario"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@PutMapping
	private ResponseEntity<Usuario> alterar(@Validated @RequestBody Usuario usuario) {
		return ResponseEntity.of(Optional.of(usuarioService.salvar(usuario)));
	}

	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Remove o Usuario"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@DeleteMapping("/{id}")
	private void excluir(@PathVariable long id) {
		usuarioService.excluir(id);
	}
	
}
