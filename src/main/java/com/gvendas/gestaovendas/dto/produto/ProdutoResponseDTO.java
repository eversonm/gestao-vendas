package com.gvendas.gestaovendas.dto.produto;

import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;
import com.gvendas.gestaovendas.entidades.Produto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ApiModel("Produto retorno DTO")
public class ProdutoResponseDTO {

    @ApiModelProperty(value = "Código")
    private Long codigo;

    @ApiModelProperty(value = "Descrição")
    private String descricao;

    @ApiModelProperty(value = "Quantidade")
    private Integer quantidade;

    @ApiModelProperty(value = "Preço custo")
    private BigDecimal precoCusto;

    @ApiModelProperty(value = "Preço venda")
    private BigDecimal precoVenda;

    @ApiModelProperty(value = "Observação")
    private String observacao;

    @ApiModelProperty(value = "Categoria")
    private CategoriaResponseDTO categoriaDto;

    public static ProdutoResponseDTO converterParaProdutoDTO(Produto produto){
        return new ProdutoResponseDTO(
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getQuantidade(),
                produto.getPrecoCusto(),
                produto.getPrecoVenda(),
                produto.getObservacao(),
                CategoriaResponseDTO.converterParaCategoriaDTO(produto.getCategoria()));
    }
}
