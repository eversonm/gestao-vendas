package com.gvendas.gestaovendas.dto.produto;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("Produto requisição DTO")
@Getter
@Setter
public class ProdutoRequestDTO {

    @ApiModelProperty(value = "Descrição")
    @NotBlank(message = "Descrição")
    @Length(min = 3, max = 100, message = "Descrição")
    private String descricao;

    @ApiModelProperty(value = "Quantidade")
    @NotNull(message = "Quantidade")
    private Integer quantidade;

    @ApiModelProperty(value = "Preço custo")
    @NotNull(message = "Preço custo")
    private BigDecimal precoCusto;

    @ApiModelProperty(value = "Preço venda")
    @NotNull(message = "Preço venda")
    private BigDecimal precoVenda;

    @ApiModelProperty(value = "Observação")
    @Length(max = 500, message = "Observação")
    private String observacao;

    public Produto converterParaEntidade(Long codigoCategoria){
        return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }

    public Produto converterParaEntidade(Long codigoCategoria, Long codigoProduto){
        return new Produto(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
    }
}
