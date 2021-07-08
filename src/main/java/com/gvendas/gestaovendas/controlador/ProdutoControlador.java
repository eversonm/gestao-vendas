package com.gvendas.gestaovendas.controlador;

import com.gvendas.gestaovendas.dto.produto.ProdutoRequestDTO;
import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.ProdutoServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar todos os produtos", nickname = "listarTodosProdutos")
    @GetMapping
    public List<ProdutoResponseDTO> listarTodos(Long codigoCategoria) {
        return produtoServico.listarTodos(codigoCategoria)
                .stream()
                .map(produto -> ProdutoResponseDTO.converterParaProdutoDTO(produto))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Listar produto por código", nickname = "buscarProdutoPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorCodigo(@PathVariable Long codigoCategoria, @PathVariable Long codigo){
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo, codigoCategoria);
        return produto.isPresent() ?
                ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produto.get())) :
                ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Adicionar um produto", nickname = "salvarProduto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody ProdutoRequestDTO produtoDto){
        Produto produtoSalvar = produtoServico.salvar(codigoCategoria, produtoDto.converterParaEntidade(codigoCategoria));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProdutoResponseDTO.converterParaProdutoDTO(produtoSalvar));
    }

    @ApiOperation(value = "Atualizar um produto", nickname = "atualizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto, @Valid @RequestBody ProdutoRequestDTO produtoDto){
        Produto produtoAtualizar = produtoServico
                .atualizar(codigoCategoria, codigoProduto, produtoDto.converterParaEntidade(codigoCategoria, codigoProduto));
        return ResponseEntity.ok(ProdutoResponseDTO.converterParaProdutoDTO(produtoAtualizar));
    }

    @ApiOperation(value = "Apagar um produto", nickname = "apagarProduto")
    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto){
        produtoServico.apagar(codigoCategoria, codigoProduto);
    }
}
