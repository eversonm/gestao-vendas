package com.gvendas.gestaovendas.servico;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.repositorio.ProdutoRepositorio;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServico {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @Autowired
    private CategoriaServico categoriaServico;

    public List<Produto> listarTodos(Long codigoCategoria){
        return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
    }

    public Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria){
        return produtoRepositorio.buscarPorCodigo(codigo, codigoCategoria);
    }

    public Produto salvar(Long codigoCategoria, Produto produto){
        validarCategoriaDoProduto(codigoCategoria);
        validarProdutoDuplicado(produto);
        return produtoRepositorio.save(produto);
    }

    public Produto atualizar(Long codigoCategoria, Long codigo, Produto produto){
        Produto produtoSalvar = validarProdutoExistente(codigo, codigoCategoria);
        validarCategoriaDoProduto(codigoCategoria);
        validarProdutoDuplicado(produto);
        BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
        return produtoRepositorio.save(produtoSalvar);
    }

    public void apagar(Long codigoCategoria, Long codigoProduto){
        Produto produtoApagar = validarProdutoExistente(codigoProduto, codigoCategoria);
        produtoRepositorio.delete(produtoApagar);
    }

    private Produto validarProdutoExistente(Long codigo, Long codigoCategoria) {
        Optional<Produto> produto = buscarPorCodigo(codigo, codigoCategoria);
        if(produto.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }
        return produto.get();
    }

    private void validarProdutoDuplicado(Produto produto){
        Optional<Produto> produtoPorDescricao = produtoRepositorio.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
        if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
            throw new RegraNegocioException(
                    String.format(
                        "O produto %s já está cadastrado.",
                        produto.getDescricao()));
        }
    }

    private void validarCategoriaDoProduto(Long codigoCategoria){
        if(codigoCategoria == null){
            throw new RegraNegocioException("A categoria não pode ser nula.");
        }
        if (categoriaServico.buscarPorCodigo(codigoCategoria).isEmpty()){
            throw new RegraNegocioException(
                String.format(
                    "A categoria de código %s informada não existe no cadastro.",
                    codigoCategoria));
        }
    }
}
