package com.sienrgitec.painaniprov.model;

public class ctComisiones {

    private Integer iUnidad;
    private Integer iTipoPersona;
    private Integer iComision;
    private String cComision;
    private Double deValor;
    private Boolean lActivo;
    private String dtCreado;
    private String dtModificado;
    private String cUsuCrea;
    private String cUsuModifica;
    private Object id;

    public Integer getiUnidad() {
        return iUnidad;
    }

    public void setiUnidad(Integer iUnidad) {
        this.iUnidad = iUnidad;
    }

    public Integer getiTipoPersona() {
        return iTipoPersona;
    }

    public void setiTipoPersona(Integer iTipoPersona) {
        this.iTipoPersona = iTipoPersona;
    }

    public Integer getiComision() {
        return iComision;
    }

    public void setiComision(Integer iComision) {
        this.iComision = iComision;
    }

    public String getcComision() {
        return cComision;
    }

    public void setcComision(String cComision) {
        this.cComision = cComision;
    }

    public Double getDeValor() {
        return deValor;
    }

    public void setDeValor(Double deValor) {
        this.deValor = deValor;
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

    public String getDtModificado() {
        return dtModificado;
    }

    public void setDtModificado(String dtModificado) {
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
}
