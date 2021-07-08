package com.gvendas.gestaovendas.controlador;

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

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoControlador {

    @Autowired
    private ProdutoServico produtoServico;

    @ApiOperation(value = "Listar todos os produtos", nickname = "listarTodosProdutos")
    @GetMapping
    public List<Produto> listarTodos(Long codigoCategoria) {
        return produtoServico.listarTodos(codigoCategoria);
    }

    @ApiOperation(value = "Listar produto por código", nickname = "buscarProdutoPorCodigo")
    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Produto>> buscarPorCodigo(@PathVariable Long codigoCategoria, @PathVariable Long codigo){
        Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo, codigoCategoria);
        return produto.isPresent() ?
                ResponseEntity.ok(produto) :
                ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Adicionar um produto", nickname = "salvarProduto")
    @PostMapping("/{codigoCategoria}")
    public ResponseEntity<Produto> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody Produto produto){
        Produto produtoSalvar = produtoServico.salvar(codigoCategoria, produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvar);
    }

    @ApiOperation(value = "Atualizar um produto", nickname = "atualizarProduto")
    @PutMapping("/{codigoProduto}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto, @Valid @RequestBody Produto produto){
        Produto produtoAtualizar = produtoServico.atualizar(codigoCategoria, codigoProduto, produto);
        return ResponseEntity.ok(produtoAtualizar);
    }

    @ApiOperation(value = "Apagar um produto", nickname = "apagarProduto")
    @DeleteMapping("/{codigoProduto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long codigoCategoria, @PathVariable Long codigoProduto){
        produtoServico.apagar(codigoCategoria, codigoProduto);
    }
}
