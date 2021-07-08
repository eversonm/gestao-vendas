package com.gvendas.gestaovendas.dto;

import com.gvendas.gestaovendas.entidades.Categoria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("Categoria Requisicao DTO")
public class CategoriaRequestDTO {

    @ApiModelProperty(value = "Nome")
    @NotBlank(message = "Nome")
    @Length(min = 3, max = 50, message = "Nome")
    private String nome;

    public Categoria converterParaEntidade(){
        return new Categoria(nome);
    }

    public Categoria converterParaEntidade(Long codigo){
        return new Categoria(codigo, nome);
    }
}
