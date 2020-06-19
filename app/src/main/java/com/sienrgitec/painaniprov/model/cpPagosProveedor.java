package com.sienrgitec.painaniprov.model;

import java.io.Serializable;

public class cpPagosProveedor implements Serializable {
    private Object id;
    private Integer iProveedor;
    private Integer iPago;
    private String dtFecha;
    private Double dePorcAporta;
    private Double dePorcProp;
    private Integer iPedAtendidos;
    private Double dePenalizaciones;
    private Integer iCancelados;
    private Double deCancelaciones;
    private Double deIngresoBruto;
    private Double deAportacioCP;
    private Double dePropinas;
    private Double deIngresoNeto;
    private String dtCreado;
    private String dtModificado;
    private String cUsuCrea;
    private String cUsuModifica;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public Integer getiPago() {
        return iPago;
    }

    public void setiPago(Integer iPago) {
        this.iPago = iPago;
    }

    public String getDtFecha() {
        return dtFecha;
    }

    public void setDtFecha(String dtFecha) {
        this.dtFecha = dtFecha;
    }

    public Double getDePorcAporta() {
        return dePorcAporta;
    }

    public void setDePorcAporta(Double dePorcAporta) {
        this.dePorcAporta = dePorcAporta;
    }

    public Double getDePorcProp() {
        return dePorcProp;
    }

    public void setDePorcProp(Double dePorcProp) {
        this.dePorcProp = dePorcProp;
    }

    public Integer getiPedAtendidos() {
        return iPedAtendidos;
    }

    public void setiPedAtendidos(Integer iPedAtendidos) {
        this.iPedAtendidos = iPedAtendidos;
    }

    public Double getDePenalizaciones() {
        return dePenalizaciones;
    }

    public void setDePenalizaciones(Double dePenalizaciones) {
        this.dePenalizaciones = dePenalizaciones;
    }

    public Integer getiCancelados() {
        return iCancelados;
    }

    public void setiCancelados(Integer iCancelados) {
        this.iCancelados = iCancelados;
    }

    public Double getDeCancelaciones() {
        return deCancelaciones;
    }

    public void setDeCancelaciones(Double deCancelaciones) {
        this.deCancelaciones = deCancelaciones;
    }

    public Double getDeIngresoBruto() {
        return deIngresoBruto;
    }

    public void setDeIngresoBruto(Double deIngresoBruto) {
        this.deIngresoBruto = deIngresoBruto;
    }

    public Double getDeAportacioCP() {
        return deAportacioCP;
    }

    public void setDeAportacioCP(Double deAportacioCP) {
        this.deAportacioCP = deAportacioCP;
    }

    public Double getDePropinas() {
        return dePropinas;
    }

    public void setDePropinas(Double dePropinas) {
        this.dePropinas = dePropinas;
    }

    public Double getDeIngresoNeto() {
        return deIngresoNeto;
    }

    public void setDeIngresoNeto(Double deIngresoNeto) {
        this.deIngresoNeto = deIngresoNeto;
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
}
