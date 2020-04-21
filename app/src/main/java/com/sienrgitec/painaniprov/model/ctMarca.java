package com.sienrgitec.painaniprov.model;

public class ctMarca {

    private Integer iProveedor;
    private Integer iMarca;
    private String cMarca;
    private Boolean lActivo;
    private Object dtCreado;
    private Object dtModifcado;
    private String cUsuCrea;
    private String cUsuModifica;
    private Object id;

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public Integer getiMarca() {
        return iMarca;
    }

    public void setiMarca(Integer iMarca) {
        this.iMarca = iMarca;
    }

    public String getcMarca() {
        return cMarca;
    }

    public void setcMarca(String cMarca) {
        this.cMarca = cMarca;
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

    public Object getDtModifcado() {
        return dtModifcado;
    }

    public void setDtModifcado(Object dtModifcado) {
        this.dtModifcado = dtModifcado;
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
}
