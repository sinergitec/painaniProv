package com.sienrgitec.painaniprov.model;

public class ctCategoriaProv {

    private Integer iProveedor;
    private Integer iCategoria;
    private String cCategoria;
    private Boolean lActivo;
    private String dtCreado;
    private Object dtModificado;
    private String cUsuCrea;
    private String cUsuModifica;
    private Object id;

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public Integer getiCategoria() {
        return iCategoria;
    }

    public void setiCategoria(Integer iCategoria) {
        this.iCategoria = iCategoria;
    }

    public String getcCategoria() {
        return cCategoria;
    }

    public void setcCategoria(String cCategoria) {
        this.cCategoria = cCategoria;
    }

    public Boolean getlActivo() {
        return lActivo;
    }

    public void setlActivo(Boolean lActivo) {
        this.lActivo = lActivo;
    }

    public String getDtCreado() {
        return dtCreado;
    }

    public void setDtCreado(String dtCreado) {
        this.dtCreado = dtCreado;
    }

    public Object getDtModificado() {
        return dtModificado;
    }

    public void setDtModificado(Object dtModificado) {
        this.dtModificado = dtModificado;
    }

    public String getcUsuCrea() {
        return cUsuCrea;
    }

    public void setcUsuCrea(String cUsuCrea) {
        this.cUsuCrea = cUsuCrea;
    }

    public String getcUsuModifica() {
        return cUsuModifica;
    }

    public void setcUsuModifica(String cUsuModifica) {
        this.cUsuModifica = cUsuModifica;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return this.cCategoria;
    }
}
