package com.sienrgitec.painaniprov.model;

public class ctSubCategoriaProv {

    private Integer iProveedor;
    private Integer iCategoria;
    private Integer iSubCategoria;
    private String cSubCategoria;
    private Boolean lActivo;
    private Object dtCreado;
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

    public Integer getiSubCategoria() {
        return iSubCategoria;
    }

    public void setiSubCategoria(Integer iSubCategoria) {
        this.iSubCategoria = iSubCategoria;
    }

    public String getcSubCategoria() {
        return cSubCategoria;
    }

    public void setcSubCategoria(String cSubCategoria) {
        this.cSubCategoria = cSubCategoria;
    }

    public Boolean getlActivo() {
        return lActivo;
    }

    public void setlActivo(Boolean lActivo) {
        this.lActivo = lActivo;
    }

    public Object getDtCreado() {
        return dtCreado;
    }

    public void setDtCreado(Object dtCreado) {
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


    @Override
    public String toString() {
        return this.cSubCategoria;
    }
}
